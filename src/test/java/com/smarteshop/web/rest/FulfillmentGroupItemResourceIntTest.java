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
import com.smarteshop.domain.FulfillmentGroupItem;
import com.smarteshop.repository.FulfillmentGroupItemRepository;
import com.smarteshop.repository.search.FulfillmentGroupItemSearchRepository;
import com.smarteshop.service.FulfillmentGroupItemService;

/**
 * Test class for the FulfillmentGroupItemResource REST controller.
 *
 * @see FulfillmentGroupItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class FulfillmentGroupItemResourceIntTest {

    @Inject
    private FulfillmentGroupItemRepository fulfillmentGroupItemRepository;

    @Inject
    private FulfillmentGroupItemService fulfillmentGroupItemService;

    @Inject
    private FulfillmentGroupItemSearchRepository fulfillmentGroupItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFulfillmentGroupItemMockMvc;

    private FulfillmentGroupItem fulfillmentGroupItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FulfillmentGroupItemResource fulfillmentGroupItemResource = new FulfillmentGroupItemResource();
        ReflectionTestUtils.setField(fulfillmentGroupItemResource, "fulfillmentGroupItemService", fulfillmentGroupItemService);
        this.restFulfillmentGroupItemMockMvc = MockMvcBuilders.standaloneSetup(fulfillmentGroupItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FulfillmentGroupItem createEntity(EntityManager em) {
        FulfillmentGroupItem fulfillmentGroupItem = new FulfillmentGroupItem();
        return fulfillmentGroupItem;
    }

    @Before
    public void initTest() {
        fulfillmentGroupItemSearchRepository.deleteAll();
        fulfillmentGroupItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createFulfillmentGroupItem() throws Exception {
        int databaseSizeBeforeCreate = fulfillmentGroupItemRepository.findAll().size();

        // Create the FulfillmentGroupItem

        restFulfillmentGroupItemMockMvc.perform(post("/api/fulfillment-group-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fulfillmentGroupItem)))
            .andExpect(status().isCreated());

        // Validate the FulfillmentGroupItem in the database
        List<FulfillmentGroupItem> fulfillmentGroupItems = fulfillmentGroupItemRepository.findAll();
        assertThat(fulfillmentGroupItems).hasSize(databaseSizeBeforeCreate + 1);
        FulfillmentGroupItem testFulfillmentGroupItem = fulfillmentGroupItems.get(fulfillmentGroupItems.size() - 1);

        // Validate the FulfillmentGroupItem in ElasticSearch
        FulfillmentGroupItem fulfillmentGroupItemEs = fulfillmentGroupItemSearchRepository.findOne(testFulfillmentGroupItem.getId());
        assertThat(fulfillmentGroupItemEs).isEqualToComparingFieldByField(testFulfillmentGroupItem);
    }

    @Test
    @Transactional
    public void getAllFulfillmentGroupItems() throws Exception {
        // Initialize the database
        fulfillmentGroupItemRepository.saveAndFlush(fulfillmentGroupItem);

        // Get all the fulfillmentGroupItems
        restFulfillmentGroupItemMockMvc.perform(get("/api/fulfillment-group-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fulfillmentGroupItem.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFulfillmentGroupItem() throws Exception {
        // Initialize the database
        fulfillmentGroupItemRepository.saveAndFlush(fulfillmentGroupItem);

        // Get the fulfillmentGroupItem
        restFulfillmentGroupItemMockMvc.perform(get("/api/fulfillment-group-items/{id}", fulfillmentGroupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fulfillmentGroupItem.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFulfillmentGroupItem() throws Exception {
        // Get the fulfillmentGroupItem
        restFulfillmentGroupItemMockMvc.perform(get("/api/fulfillment-group-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFulfillmentGroupItem() throws Exception {
        // Initialize the database
        fulfillmentGroupItemService.save(fulfillmentGroupItem);

        int databaseSizeBeforeUpdate = fulfillmentGroupItemRepository.findAll().size();

        // Update the fulfillmentGroupItem
        FulfillmentGroupItem updatedFulfillmentGroupItem = fulfillmentGroupItemRepository.findOne(fulfillmentGroupItem.getId());

        restFulfillmentGroupItemMockMvc.perform(put("/api/fulfillment-group-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFulfillmentGroupItem)))
            .andExpect(status().isOk());

        // Validate the FulfillmentGroupItem in the database
        List<FulfillmentGroupItem> fulfillmentGroupItems = fulfillmentGroupItemRepository.findAll();
        assertThat(fulfillmentGroupItems).hasSize(databaseSizeBeforeUpdate);
        FulfillmentGroupItem testFulfillmentGroupItem = fulfillmentGroupItems.get(fulfillmentGroupItems.size() - 1);

        // Validate the FulfillmentGroupItem in ElasticSearch
        FulfillmentGroupItem fulfillmentGroupItemEs = fulfillmentGroupItemSearchRepository.findOne(testFulfillmentGroupItem.getId());
        assertThat(fulfillmentGroupItemEs).isEqualToComparingFieldByField(testFulfillmentGroupItem);
    }

    @Test
    @Transactional
    public void deleteFulfillmentGroupItem() throws Exception {
        // Initialize the database
        fulfillmentGroupItemService.save(fulfillmentGroupItem);

        int databaseSizeBeforeDelete = fulfillmentGroupItemRepository.findAll().size();

        // Get the fulfillmentGroupItem
        restFulfillmentGroupItemMockMvc.perform(delete("/api/fulfillment-group-items/{id}", fulfillmentGroupItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean fulfillmentGroupItemExistsInEs = fulfillmentGroupItemSearchRepository.exists(fulfillmentGroupItem.getId());
        assertThat(fulfillmentGroupItemExistsInEs).isFalse();

        // Validate the database is empty
        List<FulfillmentGroupItem> fulfillmentGroupItems = fulfillmentGroupItemRepository.findAll();
        assertThat(fulfillmentGroupItems).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFulfillmentGroupItem() throws Exception {
        // Initialize the database
        fulfillmentGroupItemService.save(fulfillmentGroupItem);

        // Search the fulfillmentGroupItem
        restFulfillmentGroupItemMockMvc.perform(get("/api/_search/fulfillment-group-items?query=id:" + fulfillmentGroupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fulfillmentGroupItem.getId().intValue())));
    }
}
