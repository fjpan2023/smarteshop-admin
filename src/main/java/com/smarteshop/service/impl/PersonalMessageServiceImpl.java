package com.smarteshop.service.impl;

import com.smarteshop.service.PersonalMessageService;
import com.smarteshop.domain.PersonalMessage;
import com.smarteshop.repository.PersonalMessageRepository;
import com.smarteshop.repository.search.PersonalMessageSearchRepository;
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
 * Service Implementation for managing PersonalMessage.
 */
@Service
@Transactional
public class PersonalMessageServiceImpl implements PersonalMessageService{

    private final Logger log = LoggerFactory.getLogger(PersonalMessageServiceImpl.class);
    
    @Inject
    private PersonalMessageRepository personalMessageRepository;

    @Inject
    private PersonalMessageSearchRepository personalMessageSearchRepository;

    /**
     * Save a personalMessage.
     *
     * @param personalMessage the entity to save
     * @return the persisted entity
     */
    public PersonalMessage save(PersonalMessage personalMessage) {
        log.debug("Request to save PersonalMessage : {}", personalMessage);
        PersonalMessage result = personalMessageRepository.save(personalMessage);
        personalMessageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the personalMessages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonalMessage> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalMessages");
        Page<PersonalMessage> result = personalMessageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personalMessage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonalMessage findOne(Long id) {
        log.debug("Request to get PersonalMessage : {}", id);
        PersonalMessage personalMessage = personalMessageRepository.findOne(id);
        return personalMessage;
    }

    /**
     *  Delete the  personalMessage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalMessage : {}", id);
        personalMessageRepository.delete(id);
        personalMessageSearchRepository.delete(id);
    }

    /**
     * Search for the personalMessage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PersonalMessage> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PersonalMessages for query {}", query);
        Page<PersonalMessage> result = personalMessageSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
