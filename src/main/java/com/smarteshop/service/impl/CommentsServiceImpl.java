package com.smarteshop.service.impl;

import com.smarteshop.service.CommentsService;
import com.smarteshop.domain.Comments;
import com.smarteshop.repository.CommentsRepository;
import com.smarteshop.repository.search.CommentsSearchRepository;
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
 * Service Implementation for managing Comments.
 */
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService{

    private final Logger log = LoggerFactory.getLogger(CommentsServiceImpl.class);
    
    @Inject
    private CommentsRepository commentsRepository;

    @Inject
    private CommentsSearchRepository commentsSearchRepository;

    /**
     * Save a comments.
     *
     * @param comments the entity to save
     * @return the persisted entity
     */
    public Comments save(Comments comments) {
        log.debug("Request to save Comments : {}", comments);
        Comments result = commentsRepository.save(comments);
        commentsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the comments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Comments> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        Page<Comments> result = commentsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one comments by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Comments findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        Comments comments = commentsRepository.findOne(id);
        return comments;
    }

    /**
     *  Delete the  comments by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Comments : {}", id);
        commentsRepository.delete(id);
        commentsSearchRepository.delete(id);
    }

    /**
     * Search for the comments corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Comments> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Comments for query {}", query);
        Page<Comments> result = commentsSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
