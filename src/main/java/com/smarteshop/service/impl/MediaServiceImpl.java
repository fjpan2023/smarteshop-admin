package com.smarteshop.service.impl;

import com.smarteshop.service.MediaService;
import com.smarteshop.domain.Media;
import com.smarteshop.repository.MediaRepository;
import com.smarteshop.repository.search.MediaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Media.
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaService{

    private final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
    
    @Inject
    private MediaRepository mediaRepository;

    @Inject
    private MediaSearchRepository mediaSearchRepository;

    /**
     * Save a media.
     *
     * @param media the entity to save
     * @return the persisted entity
     */
    public Media save(Media media) {
        log.debug("Request to save Media : {}", media);
        Media result = mediaRepository.save(media);
        mediaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the media.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Media> findAll() {
        log.debug("Request to get all Media");
        List<Media> result = mediaRepository.findAll();

        return result;
    }

    /**
     *  Get one media by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Media findOne(Long id) {
        log.debug("Request to get Media : {}", id);
        Media media = mediaRepository.findOne(id);
        return media;
    }

    /**
     *  Delete the  media by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.delete(id);
        mediaSearchRepository.delete(id);
    }

    /**
     * Search for the media corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Media> search(String query) {
        log.debug("Request to search Media for query {}", query);
        return StreamSupport
            .stream(mediaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
