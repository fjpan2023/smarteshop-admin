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
import com.smarteshop.domain.Variant;
import com.smarteshop.repository.VariantRepository;
import com.smarteshop.repository.search.VariantSearchRepository;
import com.smarteshop.service.VariantService;

/**
 * Test class for the VariantResource REST controller.
 *
 * @see VariantController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class VariantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    @Inject
    private VariantRepository variantRepository;

    @Inject
    private VariantService variantService;

    @Inject
    private VariantSearchRepository variantSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVariantMockMvc;

    private Variant variant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VariantController variantResource = new VariantController();
        ReflectionTestUtils.setField(variantResource, "variantService", variantService);
        this.restVariantMockMvc = MockMvcBuilders.standaloneSetup(variantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variant createEntity(EntityManager em) {
        Variant variant = new Variant()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .displayOrder(DEFAULT_DISPLAY_ORDER);
        return variant;
    }

    @Before
    public void initTest() {
        variantSearchRepository.deleteAll();
        variant = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariant() throws Exception {
        int databaseSizeBeforeCreate = variantRepository.findAll().size();

        // Create the Variant

        restVariantMockMvc.perform(post("/api/variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variant)))
            .andExpect(status().isCreated());

        // Validate the Variant in the database
        List<Variant> variants = variantRepository.findAll();
        assertThat(variants).hasSize(databaseSizeBeforeCreate + 1);
        Variant testVariant = variants.get(variants.size() - 1);
        assertThat(testVariant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVariant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVariant.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);

        // Validate the Variant in ElasticSearch
        Variant variantEs = variantSearchRepository.findOne(testVariant.getId());
        assertThat(variantEs).isEqualToComparingFieldByField(testVariant);
    }

    @Test
    @Transactional
    public void getAllVariants() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variants
        restVariantMockMvc.perform(get("/api/variants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }

    @Test
    @Transactional
    public void getVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get the variant
        restVariantMockMvc.perform(get("/api/variants/{id}", variant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingVariant() throws Exception {
        // Get the variant
        restVariantMockMvc.perform(get("/api/variants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariant() throws Exception {
        // Initialize the database
        variantService.save(variant);

        int databaseSizeBeforeUpdate = variantRepository.findAll().size();

        // Update the variant
        Variant updatedVariant = variantRepository.findOne(variant.getId());
        updatedVariant
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .displayOrder(UPDATED_DISPLAY_ORDER);

        restVariantMockMvc.perform(put("/api/variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVariant)))
            .andExpect(status().isOk());

        // Validate the Variant in the database
        List<Variant> variants = variantRepository.findAll();
        assertThat(variants).hasSize(databaseSizeBeforeUpdate);
        Variant testVariant = variants.get(variants.size() - 1);
        assertThat(testVariant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVariant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVariant.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);

        // Validate the Variant in ElasticSearch
        Variant variantEs = variantSearchRepository.findOne(testVariant.getId());
        assertThat(variantEs).isEqualToComparingFieldByField(testVariant);
    }

    @Test
    @Transactional
    public void deleteVariant() throws Exception {
        // Initialize the database
        variantService.save(variant);

        int databaseSizeBeforeDelete = variantRepository.findAll().size();

        // Get the variant
        restVariantMockMvc.perform(delete("/api/variants/{id}", variant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean variantExistsInEs = variantSearchRepository.exists(variant.getId());
        assertThat(variantExistsInEs).isFalse();

        // Validate the database is empty
        List<Variant> variants = variantRepository.findAll();
        assertThat(variants).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVariant() throws Exception {
        // Initialize the database
        variantService.save(variant);

        // Search the variant
        restVariantMockMvc.perform(get("/api/_search/variants?query=id:" + variant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }
}
