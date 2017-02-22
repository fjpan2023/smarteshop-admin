package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.Media;
import com.smarteshop.service.MediaService;
import com.smarteshop.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final Logger log = LoggerFactory.getLogger(MediaController.class);

    @Inject
    private MediaService mediaService;

    /**
     * POST  /media : Create a new media.
     *
     * @param media the media to create
     * @return the ResponseEntity with status 201 (Created) and with body the new media, or with status 400 (Bad Request) if the media has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping()
    public ResponseEntity<Media> createMedia(@Valid @RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to save Media : {}", media);
        if (media.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("media", "idexists", "A new media cannot already have an ID")).body(null);
        }
        Media result = mediaService.save(media);
        return ResponseEntity.created(new URI("/api/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("media", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media : Updates an existing media.
     *
     * @param media the media to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated media,
     * or with status 400 (Bad Request) if the media is not valid,
     * or with status 500 (Internal Server Error) if the media couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping()
    public ResponseEntity<Media> updateMedia(@Valid @RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to update Media : {}", media);
        if (media.getId() == null) {
            return createMedia(media);
        }
        Media result = mediaService.save(media);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("media", media.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media : get all the media.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of media in body
     */
    @GetMapping()
    @Timed
    public List<Media> getAllMedia() {
        log.debug("REST request to get all Media");
        return mediaService.findAll();
    }

    /**
     * GET  /media/:id : get the "id" media.
     *
     * @param id the id of the media to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the media, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getMedia(@PathVariable Long id) {
        log.debug("REST request to get Media : {}", id);
        Media media = mediaService.findOne(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(media.getContentType()));
     //   headers.setContentDispositionFormData("attachment", media.getTitle());
        return ResponseEntity.ok().headers(headers).body(media.getContent());
    }

    /**
     * DELETE  /media/:id : delete the "id" media.
     *
     * @param id the id of the media to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        log.debug("REST request to delete Media : {}", id);
        mediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("media", id.toString())).build();
    }

    /**
     * SEARCH  /_search/media?query=:query : search for the media corresponding
     * to the query.
     *
     * @param query the query of the media search
     * @return the result of the search
     */
    @GetMapping("/_search")
    @Timed
    public List<Media> searchMedia(@RequestParam String query) {
        log.debug("REST request to search Media for query {}", query);
        return mediaService.search(query);
    }


}
