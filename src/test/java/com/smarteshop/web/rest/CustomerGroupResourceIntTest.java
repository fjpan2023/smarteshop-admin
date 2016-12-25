package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApplication;

import com.smarteshop.domain.CustomerGroup;
import com.smarteshop.repository.CustomerGroupRepository;
import com.smarteshop.service.CustomerGroupService;
import com.smarteshop.repository.search.CustomerGroupSearchRepository;

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
 * Test class for the CustomerGroupResource REST controller.
 *
 * @see CustomerGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class CustomerGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CustomerGroupRepository customerGroupRepository;

    @Inject
    private CustomerGroupService customerGroupService;

    @Inject
    private CustomerGroupSearchRepository customerGroupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCustomerGroupMockMvc;

    private CustomerGroup customerGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerGroupResource customerGroupResource = new CustomerGroupResource();
        ReflectionTestUtils.setField(customerGroupResource, "customerGroupService", customerGroupService);
        this.restCustomerGroupMockMvc = MockMvcBuilders.standaloneSetup(customerGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerGroup createEntity(EntityManager em) {
        CustomerGroup customerGroup = new CustomerGroup()
                .name(DEFAULT_NAME);
        return customerGroup;
    }

    @Before
    public void initTest() {
        customerGroupSearchRepository.deleteAll();
        customerGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerGroup() throws Exception {
        int databaseSizeBeforeCreate = customerGroupRepository.findAll().size();

        // Create the CustomerGroup

        restCustomerGroupMockMvc.perform(post("/api/customer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerGroup)))
            .andExpect(status().isCreated());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroups = customerGroupRepository.findAll();
        assertThat(customerGroups).hasSize(databaseSizeBeforeCreate + 1);
        CustomerGroup testCustomerGroup = customerGroups.get(customerGroups.size() - 1);
        assertThat(testCustomerGroup.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the CustomerGroup in ElasticSearch
        CustomerGroup customerGroupEs = customerGroupSearchRepository.findOne(testCustomerGroup.getId());
        assertThat(customerGroupEs).isEqualToComparingFieldByField(testCustomerGroup);
    }

    @Test
    @Transactional
    public void getAllCustomerGroups() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        // Get all the customerGroups
        restCustomerGroupMockMvc.perform(get("/api/customer-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        // Get the customerGroup
        restCustomerGroupMockMvc.perform(get("/api/customer-groups/{id}", customerGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerGroup() throws Exception {
        // Get the customerGroup
        restCustomerGroupMockMvc.perform(get("/api/customer-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupService.save(customerGroup);

        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();

        // Update the customerGroup
        CustomerGroup updatedCustomerGroup = customerGroupRepository.findOne(customerGroup.getId());
        updatedCustomerGroup
                .name(UPDATED_NAME);

        restCustomerGroupMockMvc.perform(put("/api/customer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerGroup)))
            .andExpect(status().isOk());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroups = customerGroupRepository.findAll();
        assertThat(customerGroups).hasSize(databaseSizeBeforeUpdate);
        CustomerGroup testCustomerGroup = customerGroups.get(customerGroups.size() - 1);
        assertThat(testCustomerGroup.getName()).isEqualTo(UPDATED_NAME);

        // Validate the CustomerGroup in ElasticSearch
        CustomerGroup customerGroupEs = customerGroupSearchRepository.findOne(testCustomerGroup.getId());
        assertThat(customerGroupEs).isEqualToComparingFieldByField(testCustomerGroup);
    }

    @Test
    @Transactional
    public void deleteCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupService.save(customerGroup);

        int databaseSizeBeforeDelete = customerGroupRepository.findAll().size();

        // Get the customerGroup
        restCustomerGroupMockMvc.perform(delete("/api/customer-groups/{id}", customerGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean customerGroupExistsInEs = customerGroupSearchRepository.exists(customerGroup.getId());
        assertThat(customerGroupExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerGroup> customerGroups = customerGroupRepository.findAll();
        assertThat(customerGroups).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupService.save(customerGroup);

        // Search the customerGroup
        restCustomerGroupMockMvc.perform(get("/api/_search/customer-groups?query=id:" + customerGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
