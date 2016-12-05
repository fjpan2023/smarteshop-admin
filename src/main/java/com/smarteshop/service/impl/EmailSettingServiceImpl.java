package com.smarteshop.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smarteshop.domain.EmailSetting;
import com.smarteshop.repository.EmailSettingRepository;
import com.smarteshop.service.EmailSettingService;

/**
 * Service Implementation for managing EmailSetting.
 */
@Service
@Transactional
public class EmailSettingServiceImpl implements EmailSettingService{

  private final Logger log = LoggerFactory.getLogger(EmailSettingServiceImpl.class);

  @Inject
  private EmailSettingRepository emailSettingRepository;


  /**
   * Save a emailSetting.
   *
   * @param emailSetting the entity to save
   * @return the persisted entity
   */
  @Override
  public EmailSetting save(EmailSetting emailSetting) {
    log.debug("Request to save EmailSetting : {}", emailSetting);
    EmailSetting result = emailSettingRepository.save(emailSetting);
    return result;
  }

  /**
   *  Get all the emailSettings.
   *
   *  @param pageable the pagination information
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public EmailSetting find() {
    log.debug("Request to get all EmailSettings");
    List<EmailSetting> list = emailSettingRepository.findAll();
    if(CollectionUtils.isEmpty(list)){
      return null;
    }
    EmailSetting result = list.get(0);
    return result;
  }

  /**
   *  Get one emailSetting by id.
   *
   *  @param id the id of the entity
   *  @return the entity
   */
  @Override
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
  @Override
  public void delete(Long id) {
    log.debug("Request to delete EmailSetting : {}", id);
    emailSettingRepository.delete(id);
  }


}
