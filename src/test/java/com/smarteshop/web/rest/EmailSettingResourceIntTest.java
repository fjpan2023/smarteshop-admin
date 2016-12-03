package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApp;

import com.smarteshop.domain.EmailSetting;
import com.smarteshop.repository.EmailSettingRepository;
import com.smarteshop.service.EmailSettingService;
import com.smarteshop.repository.search.EmailSettingSearchRepository;

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

import com.smarteshop.domain.enumeration.SMTPSecurityEnum;
/**
 * Test class for the EmailSettingResource REST controller.
 *
 * @see EmailSettingController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApp.class)
public class EmailSettingResourceIntTest {

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final SMTPSecurityEnum DEFAULT_SMTP_SECURITY = SMTPSecurityEnum.PLAIN;
    private static final SMTPSecurityEnum UPDATED_SMTP_SECURITY = SMTPSecurityEnum.SSL;

    private static final String DEFAULT_FROM_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FROM_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Inject
    private EmailSettingRepository emailSettingRepository;

    @Inject
    private EmailSettingService emailSettingService;

    @Inject
    private EmailSettingSearchRepository emailSettingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEmailSettingMockMvc;

    private EmailSetting emailSetting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailSettingController emailSettingResource = new EmailSettingController();
        ReflectionTestUtils.setField(emailSettingResource, "emailSettingService", emailSettingService);
        this.restEmailSettingMockMvc = MockMvcBuilders.standaloneSetup(emailSettingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailSetting createEntity(EntityManager em) {
        EmailSetting emailSetting = new EmailSetting()
                .host(DEFAULT_HOST)
                .port(DEFAULT_PORT)
                .smtpSecurity(DEFAULT_SMTP_SECURITY)
                .fromAddress(DEFAULT_FROM_ADDRESS)
                .userName(DEFAULT_USER_NAME)
                .password(DEFAULT_PASSWORD);
        return emailSetting;
    }

    @Before
    public void initTest() {
        emailSettingSearchRepository.deleteAll();
        emailSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailSetting() throws Exception {
        int databaseSizeBeforeCreate = emailSettingRepository.findAll().size();

        // Create the EmailSetting

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isCreated());

        // Validate the EmailSetting in the database
        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeCreate + 1);
        EmailSetting testEmailSetting = emailSettings.get(emailSettings.size() - 1);
        assertThat(testEmailSetting.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testEmailSetting.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testEmailSetting.getSmtpSecurity()).isEqualTo(DEFAULT_SMTP_SECURITY);
        assertThat(testEmailSetting.getFromAddress()).isEqualTo(DEFAULT_FROM_ADDRESS);
        assertThat(testEmailSetting.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testEmailSetting.getPassword()).isEqualTo(DEFAULT_PASSWORD);

        // Validate the EmailSetting in ElasticSearch
        EmailSetting emailSettingEs = emailSettingSearchRepository.findOne(testEmailSetting.getId());
        assertThat(emailSettingEs).isEqualToComparingFieldByField(testEmailSetting);
    }

    @Test
    @Transactional
    public void checkHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailSettingRepository.findAll().size();
        // set the field null
        emailSetting.setHost(null);

        // Create the EmailSetting, which fails.

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isBadRequest());

        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailSettingRepository.findAll().size();
        // set the field null
        emailSetting.setPort(null);

        // Create the EmailSetting, which fails.

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isBadRequest());

        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmtpSecurityIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailSettingRepository.findAll().size();
        // set the field null
        emailSetting.setSmtpSecurity(null);

        // Create the EmailSetting, which fails.

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isBadRequest());

        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailSettingRepository.findAll().size();
        // set the field null
        emailSetting.setFromAddress(null);

        // Create the EmailSetting, which fails.

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isBadRequest());

        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailSettingRepository.findAll().size();
        // set the field null
        emailSetting.setUserName(null);

        // Create the EmailSetting, which fails.

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isBadRequest());

        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailSettingRepository.findAll().size();
        // set the field null
        emailSetting.setPassword(null);

        // Create the EmailSetting, which fails.

        restEmailSettingMockMvc.perform(post("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSetting)))
            .andExpect(status().isBadRequest());

        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailSettings() throws Exception {
        // Initialize the database
        emailSettingRepository.saveAndFlush(emailSetting);

        // Get all the emailSettings
        restEmailSettingMockMvc.perform(get("/api/email-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].smtpSecurity").value(hasItem(DEFAULT_SMTP_SECURITY.toString())))
            .andExpect(jsonPath("$.[*].fromAddress").value(hasItem(DEFAULT_FROM_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getEmailSetting() throws Exception {
        // Initialize the database
        emailSettingRepository.saveAndFlush(emailSetting);

        // Get the emailSetting
        restEmailSettingMockMvc.perform(get("/api/email-settings/{id}", emailSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailSetting.getId().intValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.smtpSecurity").value(DEFAULT_SMTP_SECURITY.toString()))
            .andExpect(jsonPath("$.fromAddress").value(DEFAULT_FROM_ADDRESS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmailSetting() throws Exception {
        // Get the emailSetting
        restEmailSettingMockMvc.perform(get("/api/email-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailSetting() throws Exception {
        // Initialize the database
        emailSettingService.save(emailSetting);

        int databaseSizeBeforeUpdate = emailSettingRepository.findAll().size();

        // Update the emailSetting
        EmailSetting updatedEmailSetting = emailSettingRepository.findOne(emailSetting.getId());
        updatedEmailSetting
                .host(UPDATED_HOST)
                .port(UPDATED_PORT)
                .smtpSecurity(UPDATED_SMTP_SECURITY)
                .fromAddress(UPDATED_FROM_ADDRESS)
                .userName(UPDATED_USER_NAME)
                .password(UPDATED_PASSWORD);

        restEmailSettingMockMvc.perform(put("/api/email-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmailSetting)))
            .andExpect(status().isOk());

        // Validate the EmailSetting in the database
        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeUpdate);
        EmailSetting testEmailSetting = emailSettings.get(emailSettings.size() - 1);
        assertThat(testEmailSetting.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testEmailSetting.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testEmailSetting.getSmtpSecurity()).isEqualTo(UPDATED_SMTP_SECURITY);
        assertThat(testEmailSetting.getFromAddress()).isEqualTo(UPDATED_FROM_ADDRESS);
        assertThat(testEmailSetting.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testEmailSetting.getPassword()).isEqualTo(UPDATED_PASSWORD);

        // Validate the EmailSetting in ElasticSearch
        EmailSetting emailSettingEs = emailSettingSearchRepository.findOne(testEmailSetting.getId());
        assertThat(emailSettingEs).isEqualToComparingFieldByField(testEmailSetting);
    }

    @Test
    @Transactional
    public void deleteEmailSetting() throws Exception {
        // Initialize the database
        emailSettingService.save(emailSetting);

        int databaseSizeBeforeDelete = emailSettingRepository.findAll().size();

        // Get the emailSetting
        restEmailSettingMockMvc.perform(delete("/api/email-settings/{id}", emailSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean emailSettingExistsInEs = emailSettingSearchRepository.exists(emailSetting.getId());
        assertThat(emailSettingExistsInEs).isFalse();

        // Validate the database is empty
        List<EmailSetting> emailSettings = emailSettingRepository.findAll();
        assertThat(emailSettings).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmailSetting() throws Exception {
        // Initialize the database
        emailSettingService.save(emailSetting);

        // Search the emailSetting
        restEmailSettingMockMvc.perform(get("/api/_search/email-settings?query=id:" + emailSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].smtpSecurity").value(hasItem(DEFAULT_SMTP_SECURITY.toString())))
            .andExpect(jsonPath("$.[*].fromAddress").value(hasItem(DEFAULT_FROM_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
}
