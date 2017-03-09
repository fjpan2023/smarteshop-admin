package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.PersonalMessage;
import com.smarteshop.service.PersonalMessageService;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PersonalMessage.
 */
@RestController
@RequestMapping("/api")
public class PersonalMessageResource {

    private final Logger log = LoggerFactory.getLogger(PersonalMessageResource.class);
        
    @Inject
    private PersonalMessageService personalMessageService;

    /**
     * POST  /personal-messages : Create a new personalMessage.
     *
     * @param personalMessage the personalMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personalMessage, or with status 400 (Bad Request) if the personalMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personal-messages")
    @Timed
    public ResponseEntity<PersonalMessage> createPersonalMessage(@RequestBody PersonalMessage personalMessage) throws URISyntaxException {
        log.debug("REST request to save PersonalMessage : {}", personalMessage);
        if (personalMessage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personalMessage", "idexists", "A new personalMessage cannot already have an ID")).body(null);
        }
        PersonalMessage result = personalMessageService.save(personalMessage);
        return ResponseEntity.created(new URI("/api/personal-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personalMessage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personal-messages : Updates an existing personalMessage.
     *
     * @param personalMessage the personalMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personalMessage,
     * or with status 400 (Bad Request) if the personalMessage is not valid,
     * or with status 500 (Internal Server Error) if the personalMessage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personal-messages")
    @Timed
    public ResponseEntity<PersonalMessage> updatePersonalMessage(@RequestBody PersonalMessage personalMessage) throws URISyntaxException {
        log.debug("REST request to update PersonalMessage : {}", personalMessage);
        if (personalMessage.getId() == null) {
            return createPersonalMessage(personalMessage);
        }
        PersonalMessage result = personalMessageService.save(personalMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personalMessage", personalMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personal-messages : get all the personalMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personalMessages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/personal-messages")
    @Timed
    public ResponseEntity<List<PersonalMessage>> getAllPersonalMessages(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonalMessages");
        Page<PersonalMessage> page = personalMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personal-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personal-messages/:id : get the "id" personalMessage.
     *
     * @param id the id of the personalMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalMessage, or with status 404 (Not Found)
     */
    @GetMapping("/personal-messages/{id}")
    @Timed
    public ResponseEntity<PersonalMessage> getPersonalMessage(@PathVariable Long id) {
        log.debug("REST request to get PersonalMessage : {}", id);
        PersonalMessage personalMessage = personalMessageService.findOne(id);
        return Optional.ofNullable(personalMessage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /personal-messages/:id : delete the "id" personalMessage.
     *
     * @param id the id of the personalMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personal-messages/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonalMessage(@PathVariable Long id) {
        log.debug("REST request to delete PersonalMessage : {}", id);
        personalMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personalMessage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/personal-messages?query=:query : search for the personalMessage corresponding
     * to the query.
     *
     * @param query the query of the personalMessage search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/personal-messages")
    @Timed
    public ResponseEntity<List<PersonalMessage>> searchPersonalMessages(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of PersonalMessages for query {}", query);
        Page<PersonalMessage> page = personalMessageService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/personal-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
