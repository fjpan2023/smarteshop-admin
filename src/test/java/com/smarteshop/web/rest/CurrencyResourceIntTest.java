package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApplication;

import com.smarteshop.domain.Currency;
import com.smarteshop.repository.CurrencyRepository;
import com.smarteshop.service.CurrencyService;
import com.smarteshop.repository.search.CurrencySearchRepository;

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
 * Test class for the CurrencyResource REST controller.
 *
 * @see CurrencyController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApplication.class)
public class CurrencyResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL_LEFT = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL_LEFT = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL_RIGHT = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL_RIGHT = "BBBBBBBBBB";

    @Inject
    private CurrencyRepository currencyRepository;

    @Inject
    private CurrencyService currencyService;

    @Inject
    private CurrencySearchRepository currencySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurrencyController currencyResource = new CurrencyController();
        ReflectionTestUtils.setField(currencyResource, "currencyService", currencyService);
        this.restCurrencyMockMvc = MockMvcBuilders.standaloneSetup(currencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
                .title(DEFAULT_TITLE)
                .symbolLeft(DEFAULT_SYMBOL_LEFT)
                .symbolRight(DEFAULT_SYMBOL_RIGHT);
        return currency;
    }

    @Before
    public void initTest() {
        currencySearchRepository.deleteAll();
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencies = currencyRepository.findAll();
        assertThat(currencies).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencies.get(currencies.size() - 1);
        assertThat(testCurrency.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCurrency.getSymbolLeft()).isEqualTo(DEFAULT_SYMBOL_LEFT);
        assertThat(testCurrency.getSymbolRight()).isEqualTo(DEFAULT_SYMBOL_RIGHT);

        // Validate the Currency in ElasticSearch
        Currency currencyEs = currencySearchRepository.findOne(testCurrency.getId());
        assertThat(currencyEs).isEqualToComparingFieldByField(testCurrency);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencies
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].symbolLeft").value(hasItem(DEFAULT_SYMBOL_LEFT.toString())))
            .andExpect(jsonPath("$.[*].symbolRight").value(hasItem(DEFAULT_SYMBOL_RIGHT.toString())));
    }

    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.symbolLeft").value(DEFAULT_SYMBOL_LEFT.toString()))
            .andExpect(jsonPath("$.symbolRight").value(DEFAULT_SYMBOL_RIGHT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findOne(currency.getId());
        updatedCurrency
                .title(UPDATED_TITLE)
                .symbolLeft(UPDATED_SYMBOL_LEFT)
                .symbolRight(UPDATED_SYMBOL_RIGHT);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrency)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencies = currencyRepository.findAll();
        assertThat(currencies).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencies.get(currencies.size() - 1);
        assertThat(testCurrency.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCurrency.getSymbolLeft()).isEqualTo(UPDATED_SYMBOL_LEFT);
        assertThat(testCurrency.getSymbolRight()).isEqualTo(UPDATED_SYMBOL_RIGHT);

        // Validate the Currency in ElasticSearch
        Currency currencyEs = currencySearchRepository.findOne(testCurrency.getId());
        assertThat(currencyEs).isEqualToComparingFieldByField(testCurrency);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Get the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean currencyExistsInEs = currencySearchRepository.exists(currency.getId());
        assertThat(currencyExistsInEs).isFalse();

        // Validate the database is empty
        List<Currency> currencies = currencyRepository.findAll();
        assertThat(currencies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        // Search the currency
        restCurrencyMockMvc.perform(get("/api/_search/currencies?query=id:" + currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].symbolLeft").value(hasItem(DEFAULT_SYMBOL_LEFT.toString())))
            .andExpect(jsonPath("$.[*].symbolRight").value(hasItem(DEFAULT_SYMBOL_RIGHT.toString())));
    }
}
