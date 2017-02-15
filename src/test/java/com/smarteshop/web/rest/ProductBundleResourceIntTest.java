package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApp;

import com.smarteshop.domain.ProductBundle;
import com.smarteshop.repository.ProductBundleRepository;
import com.smarteshop.service.ProductBundleService;
import com.smarteshop.repository.search.ProductBundleSearchRepository;

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

/**
 * Test class for the ProductBundleResource REST controller.
 *
 * @see ProductBundleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApp.class)
public class ProductBundleResourceIntTest {

    private static final String DEFAULT_PRICE_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_MODEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_BUNDLE = false;
    private static final Boolean UPDATED_AUTO_BUNDLE = true;

    private static final Boolean DEFAULT_BUNDLE_PROMOTABLE = false;
    private static final Boolean UPDATED_BUNDLE_PROMOTABLE = true;

    @Inject
    private ProductBundleRepository productBundleRepository;

    @Inject
    private ProductBundleService productBundleService;

    @Inject
    private ProductBundleSearchRepository productBundleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductBundleMockMvc;

    private ProductBundle productBundle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductBundleResource productBundleResource = new ProductBundleResource();
        ReflectionTestUtils.setField(productBundleResource, "productBundleService", productBundleService);
        this.restProductBundleMockMvc = MockMvcBuilders.standaloneSetup(productBundleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBundle createEntity(EntityManager em) {
        ProductBundle productBundle = new ProductBundle()
                .priceModel(DEFAULT_PRICE_MODEL)
                .autoBundle(DEFAULT_AUTO_BUNDLE)
                .bundlePromotable(DEFAULT_BUNDLE_PROMOTABLE);
        return productBundle;
    }

    @Before
    public void initTest() {
        productBundleSearchRepository.deleteAll();
        productBundle = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductBundle() throws Exception {
        int databaseSizeBeforeCreate = productBundleRepository.findAll().size();

        // Create the ProductBundle

        restProductBundleMockMvc.perform(post("/api/product-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBundle)))
            .andExpect(status().isCreated());

        // Validate the ProductBundle in the database
        List<ProductBundle> productBundles = productBundleRepository.findAll();
        assertThat(productBundles).hasSize(databaseSizeBeforeCreate + 1);
        ProductBundle testProductBundle = productBundles.get(productBundles.size() - 1);
        assertThat(testProductBundle.getPriceModel()).isEqualTo(DEFAULT_PRICE_MODEL);
        assertThat(testProductBundle.isAutoBundle()).isEqualTo(DEFAULT_AUTO_BUNDLE);
        assertThat(testProductBundle.isBundlePromotable()).isEqualTo(DEFAULT_BUNDLE_PROMOTABLE);

        // Validate the ProductBundle in ElasticSearch
        ProductBundle productBundleEs = productBundleSearchRepository.findOne(testProductBundle.getId());
        assertThat(productBundleEs).isEqualToComparingFieldByField(testProductBundle);
    }

    @Test
    @Transactional
    public void getAllProductBundles() throws Exception {
        // Initialize the database
        productBundleRepository.saveAndFlush(productBundle);

        // Get all the productBundles
        restProductBundleMockMvc.perform(get("/api/product-bundles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBundle.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceModel").value(hasItem(DEFAULT_PRICE_MODEL.toString())))
            .andExpect(jsonPath("$.[*].autoBundle").value(hasItem(DEFAULT_AUTO_BUNDLE.booleanValue())))
            .andExpect(jsonPath("$.[*].bundlePromotable").value(hasItem(DEFAULT_BUNDLE_PROMOTABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getProductBundle() throws Exception {
        // Initialize the database
        productBundleRepository.saveAndFlush(productBundle);

        // Get the productBundle
        restProductBundleMockMvc.perform(get("/api/product-bundles/{id}", productBundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productBundle.getId().intValue()))
            .andExpect(jsonPath("$.priceModel").value(DEFAULT_PRICE_MODEL.toString()))
            .andExpect(jsonPath("$.autoBundle").value(DEFAULT_AUTO_BUNDLE.booleanValue()))
            .andExpect(jsonPath("$.bundlePromotable").value(DEFAULT_BUNDLE_PROMOTABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductBundle() throws Exception {
        // Get the productBundle
        restProductBundleMockMvc.perform(get("/api/product-bundles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBundle() throws Exception {
        // Initialize the database
        productBundleService.save(productBundle);

        int databaseSizeBeforeUpdate = productBundleRepository.findAll().size();

        // Update the productBundle
        ProductBundle updatedProductBundle = productBundleRepository.findOne(productBundle.getId());
        updatedProductBundle
                .priceModel(UPDATED_PRICE_MODEL)
                .autoBundle(UPDATED_AUTO_BUNDLE)
                .bundlePromotable(UPDATED_BUNDLE_PROMOTABLE);

        restProductBundleMockMvc.perform(put("/api/product-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductBundle)))
            .andExpect(status().isOk());

        // Validate the ProductBundle in the database
        List<ProductBundle> productBundles = productBundleRepository.findAll();
        assertThat(productBundles).hasSize(databaseSizeBeforeUpdate);
        ProductBundle testProductBundle = productBundles.get(productBundles.size() - 1);
        assertThat(testProductBundle.getPriceModel()).isEqualTo(UPDATED_PRICE_MODEL);
        assertThat(testProductBundle.isAutoBundle()).isEqualTo(UPDATED_AUTO_BUNDLE);
        assertThat(testProductBundle.isBundlePromotable()).isEqualTo(UPDATED_BUNDLE_PROMOTABLE);

        // Validate the ProductBundle in ElasticSearch
        ProductBundle productBundleEs = productBundleSearchRepository.findOne(testProductBundle.getId());
        assertThat(productBundleEs).isEqualToComparingFieldByField(testProductBundle);
    }

    @Test
    @Transactional
    public void deleteProductBundle() throws Exception {
        // Initialize the database
        productBundleService.save(productBundle);

        int databaseSizeBeforeDelete = productBundleRepository.findAll().size();

        // Get the productBundle
        restProductBundleMockMvc.perform(delete("/api/product-bundles/{id}", productBundle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean productBundleExistsInEs = productBundleSearchRepository.exists(productBundle.getId());
        assertThat(productBundleExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductBundle> productBundles = productBundleRepository.findAll();
        assertThat(productBundles).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductBundle() throws Exception {
        // Initialize the database
        productBundleService.save(productBundle);

        // Search the productBundle
        restProductBundleMockMvc.perform(get("/api/_search/product-bundles?query=id:" + productBundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBundle.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceModel").value(hasItem(DEFAULT_PRICE_MODEL.toString())))
            .andExpect(jsonPath("$.[*].autoBundle").value(hasItem(DEFAULT_AUTO_BUNDLE.booleanValue())))
            .andExpect(jsonPath("$.[*].bundlePromotable").value(hasItem(DEFAULT_BUNDLE_PROMOTABLE.booleanValue())));
    }
}
