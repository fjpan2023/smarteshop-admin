package com.smarteshop.service;

import com.smarteshop.domain.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Comments.
 */
public interface CommentsService {

    /**
     * Save a comments.
     *
     * @param comments the entity to save
     * @return the persisted entity
     */
    Comments save(Comments comments);

    /**
     *  Get all the comments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Comments> findAll(Pageable pageable);

    /**
     *  Get the "id" comments.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Comments findOne(Long id);

    /**
     *  Delete the "id" comments.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comments corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Comments> search(String query, Pageable pageable);
}
