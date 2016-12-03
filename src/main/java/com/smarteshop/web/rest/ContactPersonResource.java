package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.ContactPerson;
import com.smarteshop.service.ContactPersonService;
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
 * REST controller for managing ContactPerson.
 */
@RestController
@RequestMapping("/api")
public class ContactPersonResource {

    private final Logger log = LoggerFactory.getLogger(ContactPersonResource.class);
        
    @Inject
    private ContactPersonService contactPersonService;

    /**
     * POST  /contact-people : Create a new contactPerson.
     *
     * @param contactPerson the contactPerson to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactPerson, or with status 400 (Bad Request) if the contactPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-people")
    @Timed
    public ResponseEntity<ContactPerson> createContactPerson(@RequestBody ContactPerson contactPerson) throws URISyntaxException {
        log.debug("REST request to save ContactPerson : {}", contactPerson);
        if (contactPerson.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contactPerson", "idexists", "A new contactPerson cannot already have an ID")).body(null);
        }
        ContactPerson result = contactPersonService.save(contactPerson);
        return ResponseEntity.created(new URI("/api/contact-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contactPerson", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-people : Updates an existing contactPerson.
     *
     * @param contactPerson the contactPerson to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactPerson,
     * or with status 400 (Bad Request) if the contactPerson is not valid,
     * or with status 500 (Internal Server Error) if the contactPerson couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-people")
    @Timed
    public ResponseEntity<ContactPerson> updateContactPerson(@RequestBody ContactPerson contactPerson) throws URISyntaxException {
        log.debug("REST request to update ContactPerson : {}", contactPerson);
        if (contactPerson.getId() == null) {
            return createContactPerson(contactPerson);
        }
        ContactPerson result = contactPersonService.save(contactPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contactPerson", contactPerson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-people : get all the contactPeople.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactPeople in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/contact-people")
    @Timed
    public ResponseEntity<List<ContactPerson>> getAllContactPeople(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ContactPeople");
        Page<ContactPerson> page = contactPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contact-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contact-people/:id : get the "id" contactPerson.
     *
     * @param id the id of the contactPerson to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactPerson, or with status 404 (Not Found)
     */
    @GetMapping("/contact-people/{id}")
    @Timed
    public ResponseEntity<ContactPerson> getContactPerson(@PathVariable Long id) {
        log.debug("REST request to get ContactPerson : {}", id);
        ContactPerson contactPerson = contactPersonService.findOne(id);
        return Optional.ofNullable(contactPerson)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contact-people/:id : delete the "id" contactPerson.
     *
     * @param id the id of the contactPerson to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactPerson(@PathVariable Long id) {
        log.debug("REST request to delete ContactPerson : {}", id);
        contactPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contactPerson", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contact-people?query=:query : search for the contactPerson corresponding
     * to the query.
     *
     * @param query the query of the contactPerson search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/contact-people")
    @Timed
    public ResponseEntity<List<ContactPerson>> searchContactPeople(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ContactPeople for query {}", query);
        Page<ContactPerson> page = contactPersonService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contact-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
