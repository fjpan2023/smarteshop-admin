package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApplication;

import com.smarteshop.domain.MerchantStore;
import com.smarteshop.repository.StoreRepository;
import com.smarteshop.service.StoreService;
import com.smarteshop.repository.search.StoreSearchRepository;

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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarteshop.domain.enumeration.StatusEnum;
/**
 * Test class for the StoreResource REST controller.
 *
 * @see StoreController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class StoreResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.ACTIVITY;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.UNACTIVITY;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreService storeService;

    @Inject
    private StoreSearchRepository storeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStoreMockMvc;

    private MerchantStore store;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoreController storeResource = new StoreController();
        ReflectionTestUtils.setField(storeResource, "storeService", storeService);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MerchantStore createEntity(EntityManager em) {
        MerchantStore store = new MerchantStore()
                .name(DEFAULT_NAME)
                .url(DEFAULT_URL)
                .status(DEFAULT_STATUS);
        return store;
    }

    @Before
    public void initTest() {
        storeSearchRepository.deleteAll();
        store = createEntity(em);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(store)))
            .andExpect(status().isCreated());

        // Validate the Store in the database
        List<MerchantStore> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeCreate + 1);
        MerchantStore testStore = stores.get(stores.size() - 1);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testStore.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Store in ElasticSearch
        MerchantStore storeEs = storeSearchRepository.findOne(testStore.getId());
        assertThat(storeEs).isEqualToComparingFieldByField(testStore);
    }

    @Test
    @Transactional
    public void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the stores
        restStoreMockMvc.perform(get("/api/stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeService.save(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        MerchantStore updatedStore = storeRepository.findOne(store.getId());
        updatedStore
                .name(UPDATED_NAME)
                .url(UPDATED_URL)
                .status(UPDATED_STATUS);

        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStore)))
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<MerchantStore> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeUpdate);
        MerchantStore testStore = stores.get(stores.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testStore.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Store in ElasticSearch
        MerchantStore storeEs = storeSearchRepository.findOne(testStore.getId());
        assertThat(storeEs).isEqualToComparingFieldByField(testStore);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeService.save(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Get the store
        restStoreMockMvc.perform(delete("/api/stores/{id}", store.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean storeExistsInEs = storeSearchRepository.exists(store.getId());
        assertThat(storeExistsInEs).isFalse();

        // Validate the database is empty
        List<MerchantStore> stores = storeRepository.findAll();
        assertThat(stores).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStore() throws Exception {
        // Initialize the database
        storeService.save(store);

        // Search the store
        restStoreMockMvc.perform(get("/api/_search/stores?query=id:" + store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
