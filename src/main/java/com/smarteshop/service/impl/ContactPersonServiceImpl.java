package com.smarteshop.service.impl;

import com.smarteshop.service.ContactPersonService;
import com.smarteshop.domain.ContactPerson;
import com.smarteshop.repository.ContactPersonRepository;
import com.smarteshop.repository.search.ContactPersonSearchRepository;
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
 * Service Implementation for managing ContactPerson.
 */
@Service
@Transactional
public class ContactPersonServiceImpl implements ContactPersonService{

    private final Logger log = LoggerFactory.getLogger(ContactPersonServiceImpl.class);
    
    @Inject
    private ContactPersonRepository contactPersonRepository;

    @Inject
    private ContactPersonSearchRepository contactPersonSearchRepository;

    /**
     * Save a contactPerson.
     *
     * @param contactPerson the entity to save
     * @return the persisted entity
     */
    public ContactPerson save(ContactPerson contactPerson) {
        log.debug("Request to save ContactPerson : {}", contactPerson);
        ContactPerson result = contactPersonRepository.save(contactPerson);
        contactPersonSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the contactPeople.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ContactPerson> findAll(Pageable pageable) {
        log.debug("Request to get all ContactPeople");
        Page<ContactPerson> result = contactPersonRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one contactPerson by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ContactPerson findOne(Long id) {
        log.debug("Request to get ContactPerson : {}", id);
        ContactPerson contactPerson = contactPersonRepository.findOne(id);
        return contactPerson;
    }

    /**
     *  Delete the  contactPerson by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactPerson : {}", id);
        contactPersonRepository.delete(id);
        contactPersonSearchRepository.delete(id);
    }

    /**
     * Search for the contactPerson corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContactPerson> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContactPeople for query {}", query);
        Page<ContactPerson> result = contactPersonSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
