package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApp;

import com.smarteshop.domain.Template;
import com.smarteshop.repository.TemplateRepository;
import com.smarteshop.service.TemplateService;
import com.smarteshop.repository.search.TemplateSearchRepository;

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

import com.smarteshop.domain.enumeration.TemplateTypeEnum;
/**
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApp.class)
public class TemplateResourceIntTest {

    private static final TemplateTypeEnum DEFAULT_TYPE = TemplateTypeEnum.EMAIL;
    private static final TemplateTypeEnum UPDATED_TYPE = TemplateTypeEnum.LAYOUT;

    private static final Long DEFAULT_SUPER_ID = 1L;
    private static final Long UPDATED_SUPER_ID = 2L;

    private static final String DEFAULT_TEMPLATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Inject
    private TemplateRepository templateRepository;

    @Inject
    private TemplateService templateService;

    @Inject
    private TemplateSearchRepository templateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TemplateController templateResource = new TemplateController();
        ReflectionTestUtils.setField(templateResource, "templateService", templateService);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
                .type(DEFAULT_TYPE)
                .superId(DEFAULT_SUPER_ID)
                .templateKey(DEFAULT_TEMPLATE_KEY)
                .content(DEFAULT_CONTENT);
        return template;
    }

    @Before
    public void initTest() {
        templateSearchRepository.deleteAll();
        template = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template

        restTemplateMockMvc.perform(post("/api/templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(template)))
                .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templates = templateRepository.findAll();
        assertThat(templates).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templates.get(templates.size() - 1);
        assertThat(testTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTemplate.getSuperId()).isEqualTo(DEFAULT_SUPER_ID);
        assertThat(testTemplate.getTemplateKey()).isEqualTo(DEFAULT_TEMPLATE_KEY);
        assertThat(testTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the Template in ElasticSearch
        Template templateEs = templateSearchRepository.findOne(testTemplate.getId());
        assertThat(templateEs).isEqualToComparingFieldByField(testTemplate);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templates
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].superId").value(hasItem(DEFAULT_SUPER_ID.intValue())))
                .andExpect(jsonPath("$.[*].templateKey").value(hasItem(DEFAULT_TEMPLATE_KEY.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.superId").value(DEFAULT_SUPER_ID.intValue()))
            .andExpect(jsonPath("$.templateKey").value(DEFAULT_TEMPLATE_KEY.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findOne(template.getId());
        updatedTemplate
                .type(UPDATED_TYPE)
                .superId(UPDATED_SUPER_ID)
                .templateKey(UPDATED_TEMPLATE_KEY)
                .content(UPDATED_CONTENT);

        restTemplateMockMvc.perform(put("/api/templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTemplate)))
                .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templates = templateRepository.findAll();
        assertThat(templates).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templates.get(templates.size() - 1);
        assertThat(testTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTemplate.getSuperId()).isEqualTo(UPDATED_SUPER_ID);
        assertThat(testTemplate.getTemplateKey()).isEqualTo(UPDATED_TEMPLATE_KEY);
        assertThat(testTemplate.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the Template in ElasticSearch
        Template templateEs = templateSearchRepository.findOne(testTemplate.getId());
        assertThat(templateEs).isEqualToComparingFieldByField(testTemplate);
    }

    @Test
    @Transactional
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Get the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean templateExistsInEs = templateSearchRepository.exists(template.getId());
        assertThat(templateExistsInEs).isFalse();

        // Validate the database is empty
        List<Template> templates = templateRepository.findAll();
        assertThat(templates).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        // Search the template
        restTemplateMockMvc.perform(get("/api/_search/templates?query=id:" + template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].superId").value(hasItem(DEFAULT_SUPER_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateKey").value(hasItem(DEFAULT_TEMPLATE_KEY.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }
}
