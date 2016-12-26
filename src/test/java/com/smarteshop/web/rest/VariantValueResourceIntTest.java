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
import com.smarteshop.domain.VariantValue;
import com.smarteshop.repository.VariantValueRepository;
import com.smarteshop.repository.search.VariantValueSearchRepository;
import com.smarteshop.service.VariantValueService;

/**
 * Test class for the VariantValueResource REST controller.
 *
 * @see VariantValueController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class VariantValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    @Inject
    private VariantValueRepository variantValueRepository;

    @Inject
    private VariantValueService variantValueService;

    @Inject
    private VariantValueSearchRepository variantValueSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVariantValueMockMvc;

    private VariantValue variantValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VariantValueController variantValueResource = new VariantValueController();
        ReflectionTestUtils.setField(variantValueResource, "variantValueService", variantValueService);
        this.restVariantValueMockMvc = MockMvcBuilders.standaloneSetup(variantValueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariantValue createEntity(EntityManager em) {
        VariantValue variantValue = new VariantValue()
                .value(DEFAULT_VALUE)
                .code(DEFAULT_CODE)
                .displayOrder(DEFAULT_DISPLAY_ORDER);
        return variantValue;
    }

    @Before
    public void initTest() {
        variantValueSearchRepository.deleteAll();
        variantValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariantValue() throws Exception {
        int databaseSizeBeforeCreate = variantValueRepository.findAll().size();

        // Create the VariantValue

        restVariantValueMockMvc.perform(post("/api/variant-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variantValue)))
            .andExpect(status().isCreated());

        // Validate the VariantValue in the database
        List<VariantValue> variantValues = variantValueRepository.findAll();
        assertThat(variantValues).hasSize(databaseSizeBeforeCreate + 1);
        VariantValue testVariantValue = variantValues.get(variantValues.size() - 1);
        assertThat(testVariantValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testVariantValue.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVariantValue.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);

        // Validate the VariantValue in ElasticSearch
        VariantValue variantValueEs = variantValueSearchRepository.findOne(testVariantValue.getId());
        assertThat(variantValueEs).isEqualToComparingFieldByField(testVariantValue);
    }

    @Test
    @Transactional
    public void getAllVariantValues() throws Exception {
        // Initialize the database
        variantValueRepository.saveAndFlush(variantValue);

        // Get all the variantValues
        restVariantValueMockMvc.perform(get("/api/variant-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variantValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }

    @Test
    @Transactional
    public void getVariantValue() throws Exception {
        // Initialize the database
        variantValueRepository.saveAndFlush(variantValue);

        // Get the variantValue
        restVariantValueMockMvc.perform(get("/api/variant-values/{id}", variantValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variantValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingVariantValue() throws Exception {
        // Get the variantValue
        restVariantValueMockMvc.perform(get("/api/variant-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariantValue() throws Exception {
        // Initialize the database
        variantValueService.save(variantValue);

        int databaseSizeBeforeUpdate = variantValueRepository.findAll().size();

        // Update the variantValue
        VariantValue updatedVariantValue = variantValueRepository.findOne(variantValue.getId());
        updatedVariantValue
                .value(UPDATED_VALUE)
                .code(UPDATED_CODE)
                .displayOrder(UPDATED_DISPLAY_ORDER);

        restVariantValueMockMvc.perform(put("/api/variant-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVariantValue)))
            .andExpect(status().isOk());

        // Validate the VariantValue in the database
        List<VariantValue> variantValues = variantValueRepository.findAll();
        assertThat(variantValues).hasSize(databaseSizeBeforeUpdate);
        VariantValue testVariantValue = variantValues.get(variantValues.size() - 1);
        assertThat(testVariantValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testVariantValue.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVariantValue.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);

        // Validate the VariantValue in ElasticSearch
        VariantValue variantValueEs = variantValueSearchRepository.findOne(testVariantValue.getId());
        assertThat(variantValueEs).isEqualToComparingFieldByField(testVariantValue);
    }

    @Test
    @Transactional
    public void deleteVariantValue() throws Exception {
        // Initialize the database
        variantValueService.save(variantValue);

        int databaseSizeBeforeDelete = variantValueRepository.findAll().size();

        // Get the variantValue
        restVariantValueMockMvc.perform(delete("/api/variant-values/{id}", variantValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean variantValueExistsInEs = variantValueSearchRepository.exists(variantValue.getId());
        assertThat(variantValueExistsInEs).isFalse();

        // Validate the database is empty
        List<VariantValue> variantValues = variantValueRepository.findAll();
        assertThat(variantValues).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVariantValue() throws Exception {
        // Initialize the database
        variantValueService.save(variantValue);

        // Search the variantValue
        restVariantValueMockMvc.perform(get("/api/_search/variant-values?query=id:" + variantValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variantValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)));
    }
}
