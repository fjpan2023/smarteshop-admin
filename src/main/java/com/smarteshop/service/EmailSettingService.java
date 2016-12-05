package com.smarteshop.service;

import com.smarteshop.domain.EmailSetting;

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
     EmailSetting find();

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

}
