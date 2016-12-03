package com.smarteshop.service.impl;

import com.smarteshop.service.EmailSettingService;
import com.smarteshop.domain.EmailSetting;
import com.smarteshop.repository.EmailSettingRepository;
import com.smarteshop.repository.search.EmailSettingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EmailSetting.
 */
@Service
@Transactional
public class EmailSettingServiceImpl implements EmailSettingService{

    private final Logger log = LoggerFactory.getLogger(EmailSettingServiceImpl.class);
    
    @Inject
    private EmailSettingRepository emailSettingRepository;

    @Inject
    private EmailSettingSearchRepository emailSettingSearchRepository;

    /**
     * Save a emailSetting.
     *
     * @param emailSetting the entity to save
     * @return the persisted entity
     */
    public EmailSetting save(EmailSetting emailSetting) {
        log.debug("Request to save EmailSetting : {}", emailSetting);
        EmailSetting result = emailSettingRepository.save(emailSetting);
        emailSettingSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the emailSettings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EmailSetting> findAll(Pageable pageable) {
        log.debug("Request to get all EmailSettings");
        Page<EmailSetting> result = emailSettingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one emailSetting by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EmailSetting findOne(Long id) {
        log.debug("Request to get EmailSetting : {}", id);
        EmailSetting emailSetting = emailSettingRepository.findOne(id);
        return emailSetting;
    }

    /**
     *  Delete the  emailSetting by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EmailSetting : {}", id);
        emailSettingRepository.delete(id);
        emailSettingSearchRepository.delete(id);
    }

    /**
     * Search for the emailSetting corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EmailSetting> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EmailSettings for query {}", query);
        Page<EmailSetting> result = emailSettingSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
