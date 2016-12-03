package com.smarteshop.service;

import com.smarteshop.domain.EmailSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing EmailSetting.
 */
public interface EmailSettingService {

    /**
     * Save a emailSetting.
     *
     * @param emailSetting the entity to save
     * @return the persisted entity
     */
    EmailSetting save(EmailSetting emailSetting);

    /**
     *  Get all the emailSettings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EmailSetting> findAll(Pageable pageable);

    /**
     *  Get the "id" emailSetting.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmailSetting findOne(Long id);

    /**
     *  Delete the "id" emailSetting.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the emailSetting corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EmailSetting> search(String query, Pageable pageable);
}
