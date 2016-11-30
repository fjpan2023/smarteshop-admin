package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApp;

import com.smarteshop.domain.RelatedProduct;
import com.smarteshop.repository.RelatedProductRepository;
import com.smarteshop.service.RelatedProductService;
import com.smarteshop.repository.search.RelatedProductSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RelatedProductResource REST controller.
 *
 * @see RelatedProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApp.class)
public class RelatedProductResourceIntTest {

    private static final Long DEFAULT_RELATED_PRODUCT_ID = 1L;
    private static final Long UPDATED_RELATED_PRODUCT_ID = 2L;

    @Inject
    private RelatedProductRepository relatedProductRepository;

    @Inject
    private RelatedProductService relatedProductService;

    @Inject
    private RelatedProductSearchRepository relatedProductSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRelatedProductMockMvc;

    private RelatedProduct relatedProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelatedProductResource relatedProductResource = new RelatedProductResource();
        ReflectionTestUtils.setField(relatedProductResource, "relatedProductService", relatedProductService);
        this.restRelatedProductMockMvc = MockMvcBuilders.standaloneSetup(relatedProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedProduct createEntity(EntityManager em) {
        RelatedProduct relatedProduct = new RelatedProduct()
                .relatedProductId(DEFAULT_RELATED_PRODUCT_ID);
        return relatedProduct;
    }

    @Before
    public void initTest() {
        relatedProductSearchRepository.deleteAll();
        relatedProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelatedProduct() throws Exception {
        int databaseSizeBeforeCreate = relatedProductRepository.findAll().size();

        // Create the RelatedProduct

        restRelatedProductMockMvc.perform(post("/api/related-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(relatedProduct)))
                .andExpect(status().isCreated());

        // Validate the RelatedProduct in the database
        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeCreate + 1);
        RelatedProduct testRelatedProduct = relatedProducts.get(relatedProducts.size() - 1);
        assertThat(testRelatedProduct.getRelatedProductId()).isEqualTo(DEFAULT_RELATED_PRODUCT_ID);

        // Validate the RelatedProduct in ElasticSearch
        RelatedProduct relatedProductEs = relatedProductSearchRepository.findOne(testRelatedProduct.getId());
        assertThat(relatedProductEs).isEqualToComparingFieldByField(testRelatedProduct);
    }

    @Test
    @Transactional
    public void checkRelatedProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = relatedProductRepository.findAll().size();
        // set the field null
        relatedProduct.setRelatedProductId(null);

        // Create the RelatedProduct, which fails.

        restRelatedProductMockMvc.perform(post("/api/related-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(relatedProduct)))
                .andExpect(status().isBadRequest());

        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelatedProducts() throws Exception {
        // Initialize the database
        relatedProductRepository.saveAndFlush(relatedProduct);

        // Get all the relatedProducts
        restRelatedProductMockMvc.perform(get("/api/related-products?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(relatedProduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].relatedProductId").value(hasItem(DEFAULT_RELATED_PRODUCT_ID.intValue())));
    }

    @Test
    @Transactional
    public void getRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductRepository.saveAndFlush(relatedProduct);

        // Get the relatedProduct
        restRelatedProductMockMvc.perform(get("/api/related-products/{id}", relatedProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relatedProduct.getId().intValue()))
            .andExpect(jsonPath("$.relatedProductId").value(DEFAULT_RELATED_PRODUCT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRelatedProduct() throws Exception {
        // Get the relatedProduct
        restRelatedProductMockMvc.perform(get("/api/related-products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductService.save(relatedProduct);

        int databaseSizeBeforeUpdate = relatedProductRepository.findAll().size();

        // Update the relatedProduct
        RelatedProduct updatedRelatedProduct = relatedProductRepository.findOne(relatedProduct.getId());
        updatedRelatedProduct
                .relatedProductId(UPDATED_RELATED_PRODUCT_ID);

        restRelatedProductMockMvc.perform(put("/api/related-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRelatedProduct)))
                .andExpect(status().isOk());

        // Validate the RelatedProduct in the database
        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeUpdate);
        RelatedProduct testRelatedProduct = relatedProducts.get(relatedProducts.size() - 1);
        assertThat(testRelatedProduct.getRelatedProductId()).isEqualTo(UPDATED_RELATED_PRODUCT_ID);

        // Validate the RelatedProduct in ElasticSearch
        RelatedProduct relatedProductEs = relatedProductSearchRepository.findOne(testRelatedProduct.getId());
        assertThat(relatedProductEs).isEqualToComparingFieldByField(testRelatedProduct);
    }

    @Test
    @Transactional
    public void deleteRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductService.save(relatedProduct);

        int databaseSizeBeforeDelete = relatedProductRepository.findAll().size();

        // Get the relatedProduct
        restRelatedProductMockMvc.perform(delete("/api/related-products/{id}", relatedProduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean relatedProductExistsInEs = relatedProductSearchRepository.exists(relatedProduct.getId());
        assertThat(relatedProductExistsInEs).isFalse();

        // Validate the database is empty
        List<RelatedProduct> relatedProducts = relatedProductRepository.findAll();
        assertThat(relatedProducts).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRelatedProduct() throws Exception {
        // Initialize the database
        relatedProductService.save(relatedProduct);

        // Search the relatedProduct
        restRelatedProductMockMvc.perform(get("/api/_search/related-products?query=id:" + relatedProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatedProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].relatedProductId").value(hasItem(DEFAULT_RELATED_PRODUCT_ID.intValue())));
    }
}
