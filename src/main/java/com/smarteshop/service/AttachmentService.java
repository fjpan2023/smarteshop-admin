package com.smarteshop.service;

import com.smarteshop.domain.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Attachment.
 */
public interface AttachmentService {

    /**
     * Save a attachment.
     *
     * @param attachment the entity to save
     * @return the persisted entity
     */
    Attachment save(Attachment attachment);

    /**
     *  Get all the attachments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Attachment> findAll(Pageable pageable);

    /**
     *  Get the "id" attachment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Attachment findOne(Long id);

    /**
     *  Delete the "id" attachment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the attachment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Attachment> search(String query, Pageable pageable);
}
