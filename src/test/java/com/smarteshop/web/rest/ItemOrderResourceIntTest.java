package com.smarteshop.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.smarteshop.SmarteshopApplication;
import com.smarteshop.domain.OrderItem;
import com.smarteshop.repository.ItemOrderRepository;
import com.smarteshop.repository.search.ItemOrderSearchRepository;
import com.smarteshop.service.ItemOrderService;

/**
 * Test class for the ItemOrderResource REST controller.
 *
 * @see ItemOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class ItemOrderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private ItemOrderRepository itemOrderRepository;

    @Inject
    private ItemOrderService itemOrderService;

    @Inject
    private ItemOrderSearchRepository itemOrderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restItemOrderMockMvc;

    private OrderItem itemOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemOrderResource itemOrderResource = new ItemOrderResource();
        ReflectionTestUtils.setField(itemOrderResource, "itemOrderService", itemOrderService);
        this.restItemOrderMockMvc = MockMvcBuilders.standaloneSetup(itemOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createEntity(EntityManager em) {
        OrderItem itemOrder = new OrderItem()
                .name(DEFAULT_NAME);
        return itemOrder;
    }

    @Before
    public void initTest() {
        itemOrderSearchRepository.deleteAll();
        itemOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemOrder() throws Exception {
        int databaseSizeBeforeCreate = itemOrderRepository.findAll().size();

        // Create the ItemOrder

        restItemOrderMockMvc.perform(post("/api/item-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemOrder)))
            .andExpect(status().isCreated());

        // Validate the ItemOrder in the database
        List<OrderItem> itemOrders = itemOrderRepository.findAll();
        assertThat(itemOrders).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testItemOrder = itemOrders.get(itemOrders.size() - 1);
        assertThat(testItemOrder.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ItemOrder in ElasticSearch
        OrderItem itemOrderEs = itemOrderSearchRepository.findOne(testItemOrder.getId());
        assertThat(itemOrderEs).isEqualToComparingFieldByField(testItemOrder);
    }

    @Test
    @Transactional
    public void getAllItemOrders() throws Exception {
        // Initialize the database
        itemOrderRepository.saveAndFlush(itemOrder);

        // Get all the itemOrders
        restItemOrderMockMvc.perform(get("/api/item-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getItemOrder() throws Exception {
        // Initialize the database
        itemOrderRepository.saveAndFlush(itemOrder);

        // Get the itemOrder
        restItemOrderMockMvc.perform(get("/api/item-orders/{id}", itemOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemOrder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemOrder() throws Exception {
        // Get the itemOrder
        restItemOrderMockMvc.perform(get("/api/item-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemOrder() throws Exception {
        // Initialize the database
        itemOrderService.save(itemOrder);

        int databaseSizeBeforeUpdate = itemOrderRepository.findAll().size();

        // Update the itemOrder
        OrderItem updatedItemOrder = itemOrderRepository.findOne(itemOrder.getId());
        updatedItemOrder
                .name(UPDATED_NAME);

        restItemOrderMockMvc.perform(put("/api/item-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemOrder)))
            .andExpect(status().isOk());

        // Validate the ItemOrder in the database
        List<OrderItem> itemOrders = itemOrderRepository.findAll();
        assertThat(itemOrders).hasSize(databaseSizeBeforeUpdate);
        OrderItem testItemOrder = itemOrders.get(itemOrders.size() - 1);
        assertThat(testItemOrder.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ItemOrder in ElasticSearch
        OrderItem itemOrderEs = itemOrderSearchRepository.findOne(testItemOrder.getId());
        assertThat(itemOrderEs).isEqualToComparingFieldByField(testItemOrder);
    }

    @Test
    @Transactional
    public void deleteItemOrder() throws Exception {
        // Initialize the database
        itemOrderService.save(itemOrder);

        int databaseSizeBeforeDelete = itemOrderRepository.findAll().size();

        // Get the itemOrder
        restItemOrderMockMvc.perform(delete("/api/item-orders/{id}", itemOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean itemOrderExistsInEs = itemOrderSearchRepository.exists(itemOrder.getId());
        assertThat(itemOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderItem> itemOrders = itemOrderRepository.findAll();
        assertThat(itemOrders).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchItemOrder() throws Exception {
        // Initialize the database
        itemOrderService.save(itemOrder);

        // Search the itemOrder
        restItemOrderMockMvc.perform(get("/api/_search/item-orders?query=id:" + itemOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
