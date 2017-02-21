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
import com.smarteshop.domain.Category;
import com.smarteshop.domain.enumeration.StatusEnum;
import com.smarteshop.repository.CategoryRepository;
import com.smarteshop.repository.search.CategorySearchRepository;
import com.smarteshop.service.CategoryService;
/**
 * Test class for the CategoryResource REST controller.
 *
 * @see CategoryController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class CategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    private static final Boolean DEFAULT_INCLUDE_MENU = false;
    private static final Boolean UPDATED_INCLUDE_MENU = true;

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.ACTIVITY;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.UNACTIVITY;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private CategoryService categoryService;

    @Inject
    private CategorySearchRepository categorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCategoryMockMvc;

    private Category category;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryController categoryResource = new CategoryController();
        ReflectionTestUtils.setField(categoryResource, "categoryService", categoryService);
        this.restCategoryMockMvc = MockMvcBuilders.standaloneSetup(categoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
                .name(DEFAULT_NAME)
                .parentId(DEFAULT_PARENT_ID)
                .includeMenu(DEFAULT_INCLUDE_MENU)
                .status(DEFAULT_STATUS);
        return category;
    }

    @Before
    public void initTest() {
        categorySearchRepository.deleteAll();
        category = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category

        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categories.get(categories.size() - 1);
        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testCategory.isIncludeMenu()).isEqualTo(DEFAULT_INCLUDE_MENU);
        assertThat(testCategory.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Category in ElasticSearch
        Category categoryEs = categorySearchRepository.findOne(testCategory.getId());
        assertThat(categoryEs).isEqualToComparingFieldByField(testCategory);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categories
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].includeMenu").value(hasItem(DEFAULT_INCLUDE_MENU.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()))
            .andExpect(jsonPath("$.includeMenu").value(DEFAULT_INCLUDE_MENU.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findOne(category.getId());
        updatedCategory
                .name(UPDATED_NAME)
                .parentId(UPDATED_PARENT_ID)
                .includeMenu(UPDATED_INCLUDE_MENU)
                .status(UPDATED_STATUS);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategory)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categories.get(categories.size() - 1);
        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCategory.isIncludeMenu()).isEqualTo(UPDATED_INCLUDE_MENU);
        assertThat(testCategory.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Category in ElasticSearch
        Category categoryEs = categorySearchRepository.findOne(testCategory.getId());
        assertThat(categoryEs).isEqualToComparingFieldByField(testCategory);
    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Get the category
        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean categoryExistsInEs = categorySearchRepository.exists(category.getId());
        assertThat(categoryExistsInEs).isFalse();

        // Validate the database is empty
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        // Search the category
        restCategoryMockMvc.perform(get("/api/_search/categories?query=id:" + category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].includeMenu").value(hasItem(DEFAULT_INCLUDE_MENU.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
