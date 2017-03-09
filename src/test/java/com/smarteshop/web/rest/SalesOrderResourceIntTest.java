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
import com.smarteshop.domain.SalesOrder;
import com.smarteshop.repository.SalesOrderRepository;
import com.smarteshop.repository.search.SalesOrderSearchRepository;
import com.smarteshop.service.SalesOrderService;

/**
 * Test class for the SalesOrderResource REST controller.
 *
 * @see SalesOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class SalesOrderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderService salesOrderService;

    @Inject
    private SalesOrderSearchRepository salesOrderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesOrderResource salesOrderResource = new SalesOrderResource();
        ReflectionTestUtils.setField(salesOrderResource, "salesOrderService", salesOrderService);
        this.restSalesOrderMockMvc = MockMvcBuilders.standaloneSetup(salesOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
                .name(DEFAULT_NAME);
        return salesOrder;
    }

    @Before
    public void initTest() {
        salesOrderSearchRepository.deleteAll();
        salesOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder

        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrders.get(salesOrders.size() - 1);
        assertThat(testSalesOrder.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the SalesOrder in ElasticSearch
        SalesOrder salesOrderEs = salesOrderSearchRepository.findOne(testSalesOrder.getId());
        assertThat(salesOrderEs).isEqualToComparingFieldByField(testSalesOrder);
    }

    @Test
    @Transactional
    public void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrders
        restSalesOrderMockMvc.perform(get("/api/sales-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        SalesOrder updatedSalesOrder = salesOrderRepository.findOne(salesOrder.getId());
        updatedSalesOrder
                .name(UPDATED_NAME);

        restSalesOrderMockMvc.perform(put("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesOrder)))
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrders.get(salesOrders.size() - 1);
        assertThat(testSalesOrder.getName()).isEqualTo(UPDATED_NAME);

        // Validate the SalesOrder in ElasticSearch
        SalesOrder salesOrderEs = salesOrderSearchRepository.findOne(testSalesOrder.getId());
        assertThat(salesOrderEs).isEqualToComparingFieldByField(testSalesOrder);
    }

    @Test
    @Transactional
    public void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Get the salesOrder
        restSalesOrderMockMvc.perform(delete("/api/sales-orders/{id}", salesOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean salesOrderExistsInEs = salesOrderSearchRepository.exists(salesOrder.getId());
        assertThat(salesOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        // Search the salesOrder
        restSalesOrderMockMvc.perform(get("/api/_search/sales-orders?query=id:" + salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
