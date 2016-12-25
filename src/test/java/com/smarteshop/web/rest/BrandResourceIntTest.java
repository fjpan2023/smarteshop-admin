package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApplication;

import com.smarteshop.domain.Brand;
import com.smarteshop.repository.BrandRepository;
import com.smarteshop.service.BrandService;
import com.smarteshop.repository.search.BrandSearchRepository;

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
 * Test class for the BrandResource REST controller.
 *
 * @see BrandController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class BrandResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.ACTIVITY;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.UNACTIVITY;

    @Inject
    private BrandRepository brandRepository;

    @Inject
    private BrandService brandService;

    @Inject
    private BrandSearchRepository brandSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBrandMockMvc;

    private Brand brand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BrandController brandResource = new BrandController();
        ReflectionTestUtils.setField(brandResource, "brandService", brandService);
        this.restBrandMockMvc = MockMvcBuilders.standaloneSetup(brandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createEntity(EntityManager em) {
        Brand brand = new Brand()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .status(DEFAULT_STATUS);
        return brand;
    }

    @Before
    public void initTest() {
        brandSearchRepository.deleteAll();
        brand = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrand() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand

        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeCreate + 1);
        Brand testBrand = brands.get(brands.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBrand.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBrand.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Brand in ElasticSearch
        Brand brandEs = brandSearchRepository.findOne(testBrand.getId());
        assertThat(brandEs).isEqualToComparingFieldByField(testBrand);
    }

    @Test
    @Transactional
    public void getAllBrands() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brands
        restBrandMockMvc.perform(get("/api/brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand
        Brand updatedBrand = brandRepository.findOne(brand.getId());
        updatedBrand
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .status(UPDATED_STATUS);

        restBrandMockMvc.perform(put("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrand)))
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brands.get(brands.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrand.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBrand.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Brand in ElasticSearch
        Brand brandEs = brandSearchRepository.findOne(testBrand.getId());
        assertThat(brandEs).isEqualToComparingFieldByField(testBrand);
    }

    @Test
    @Transactional
    public void deleteBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        int databaseSizeBeforeDelete = brandRepository.findAll().size();

        // Get the brand
        restBrandMockMvc.perform(delete("/api/brands/{id}", brand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean brandExistsInEs = brandSearchRepository.exists(brand.getId());
        assertThat(brandExistsInEs).isFalse();

        // Validate the database is empty
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        // Search the brand
        restBrandMockMvc.perform(get("/api/_search/brands?query=id:" + brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
