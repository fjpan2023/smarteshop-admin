package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApp;

import com.smarteshop.domain.Sku;
import com.smarteshop.repository.SkuRepository;
import com.smarteshop.service.SkuService;
import com.smarteshop.repository.search.SkuSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarteshop.domain.enumeration.StatusEnum;
/**
 * Test class for the SkuResource REST controller.
 *
 * @see SKUController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApp.class)
public class SkuResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WIDTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_WIDTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HEIGH = new BigDecimal(1);
    private static final BigDecimal UPDATED_HEIGH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LENGTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_LENGTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_STANDARD_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_STANDARD_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SELL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELL_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_DEFAULT_SKU = false;
    private static final Boolean UPDATED_DEFAULT_SKU = true;

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.ACTIVITY;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.UNACTIVITY;

    @Inject
    private SkuRepository skuRepository;

    @Inject
    private SkuService skuService;

    @Inject
    private SkuSearchRepository skuSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSkuMockMvc;

    private Sku sku;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SKUController skuResource = new SKUController();
        ReflectionTestUtils.setField(skuResource, "skuService", skuService);
        this.restSkuMockMvc = MockMvcBuilders.standaloneSetup(skuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sku createEntity(EntityManager em) {
        Sku sku = new Sku()
                .code(DEFAULT_CODE)
                .name(DEFAULT_NAME)
                .size(DEFAULT_SIZE)
                .width(DEFAULT_WIDTH)
                .heigh(DEFAULT_HEIGH)
                .length(DEFAULT_LENGTH)
                .weight(DEFAULT_WEIGHT)
                .standardPrice(DEFAULT_STANDARD_PRICE)
                .sellPrice(DEFAULT_SELL_PRICE)
                .defaultSku(DEFAULT_DEFAULT_SKU)
                .status(DEFAULT_STATUS);
        return sku;
    }

    @Before
    public void initTest() {
        skuSearchRepository.deleteAll();
        sku = createEntity(em);
    }

    @Test
    @Transactional
    public void createSku() throws Exception {
        int databaseSizeBeforeCreate = skuRepository.findAll().size();

        // Create the Sku

        restSkuMockMvc.perform(post("/api/skus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sku)))
                .andExpect(status().isCreated());

        // Validate the Sku in the database
        List<Sku> skus = skuRepository.findAll();
        assertThat(skus).hasSize(databaseSizeBeforeCreate + 1);
        Sku testSku = skus.get(skus.size() - 1);
        assertThat(testSku.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSku.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSku.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testSku.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testSku.getHeigh()).isEqualTo(DEFAULT_HEIGH);
        assertThat(testSku.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testSku.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testSku.getStandardPrice()).isEqualTo(DEFAULT_STANDARD_PRICE);
        assertThat(testSku.getSellPrice()).isEqualTo(DEFAULT_SELL_PRICE);
        assertThat(testSku.isDefaultSku()).isEqualTo(DEFAULT_DEFAULT_SKU);
        assertThat(testSku.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Sku in ElasticSearch
        Sku skuEs = skuSearchRepository.findOne(testSku.getId());
        assertThat(skuEs).isEqualToComparingFieldByField(testSku);
    }

    @Test
    @Transactional
    public void checkStandardPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setStandardPrice(null);

        // Create the Sku, which fails.

        restSkuMockMvc.perform(post("/api/skus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sku)))
                .andExpect(status().isBadRequest());

        List<Sku> skus = skuRepository.findAll();
        assertThat(skus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setSellPrice(null);

        // Create the Sku, which fails.

        restSkuMockMvc.perform(post("/api/skus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sku)))
                .andExpect(status().isBadRequest());

        List<Sku> skus = skuRepository.findAll();
        assertThat(skus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkus() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skus
        restSkuMockMvc.perform(get("/api/skus?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sku.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
                .andExpect(jsonPath("$.[*].heigh").value(hasItem(DEFAULT_HEIGH.intValue())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
                .andExpect(jsonPath("$.[*].standardPrice").value(hasItem(DEFAULT_STANDARD_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].sellPrice").value(hasItem(DEFAULT_SELL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].defaultSku").value(hasItem(DEFAULT_DEFAULT_SKU.booleanValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get the sku
        restSkuMockMvc.perform(get("/api/skus/{id}", sku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sku.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.intValue()))
            .andExpect(jsonPath("$.heigh").value(DEFAULT_HEIGH.intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.standardPrice").value(DEFAULT_STANDARD_PRICE.intValue()))
            .andExpect(jsonPath("$.sellPrice").value(DEFAULT_SELL_PRICE.intValue()))
            .andExpect(jsonPath("$.defaultSku").value(DEFAULT_DEFAULT_SKU.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSku() throws Exception {
        // Get the sku
        restSkuMockMvc.perform(get("/api/skus/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSku() throws Exception {
        // Initialize the database
        skuService.save(sku);

        int databaseSizeBeforeUpdate = skuRepository.findAll().size();

        // Update the sku
        Sku updatedSku = skuRepository.findOne(sku.getId());
        updatedSku
                .code(UPDATED_CODE)
                .name(UPDATED_NAME)
                .size(UPDATED_SIZE)
                .width(UPDATED_WIDTH)
                .heigh(UPDATED_HEIGH)
                .length(UPDATED_LENGTH)
                .weight(UPDATED_WEIGHT)
                .standardPrice(UPDATED_STANDARD_PRICE)
                .sellPrice(UPDATED_SELL_PRICE)
                .defaultSku(UPDATED_DEFAULT_SKU)
                .status(UPDATED_STATUS);

        restSkuMockMvc.perform(put("/api/skus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSku)))
                .andExpect(status().isOk());

        // Validate the Sku in the database
        List<Sku> skus = skuRepository.findAll();
        assertThat(skus).hasSize(databaseSizeBeforeUpdate);
        Sku testSku = skus.get(skus.size() - 1);
        assertThat(testSku.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSku.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSku.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testSku.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testSku.getHeigh()).isEqualTo(UPDATED_HEIGH);
        assertThat(testSku.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testSku.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testSku.getStandardPrice()).isEqualTo(UPDATED_STANDARD_PRICE);
        assertThat(testSku.getSellPrice()).isEqualTo(UPDATED_SELL_PRICE);
        assertThat(testSku.isDefaultSku()).isEqualTo(UPDATED_DEFAULT_SKU);
        assertThat(testSku.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Sku in ElasticSearch
        Sku skuEs = skuSearchRepository.findOne(testSku.getId());
        assertThat(skuEs).isEqualToComparingFieldByField(testSku);
    }

    @Test
    @Transactional
    public void deleteSku() throws Exception {
        // Initialize the database
        skuService.save(sku);

        int databaseSizeBeforeDelete = skuRepository.findAll().size();

        // Get the sku
        restSkuMockMvc.perform(delete("/api/skus/{id}", sku.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean skuExistsInEs = skuSearchRepository.exists(sku.getId());
        assertThat(skuExistsInEs).isFalse();

        // Validate the database is empty
        List<Sku> skus = skuRepository.findAll();
        assertThat(skus).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSku() throws Exception {
        // Initialize the database
        skuService.save(sku);

        // Search the sku
        restSkuMockMvc.perform(get("/api/_search/skus?query=id:" + sku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sku.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].heigh").value(hasItem(DEFAULT_HEIGH.intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].standardPrice").value(hasItem(DEFAULT_STANDARD_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].sellPrice").value(hasItem(DEFAULT_SELL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].defaultSku").value(hasItem(DEFAULT_DEFAULT_SKU.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
