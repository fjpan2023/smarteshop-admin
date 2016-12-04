package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.smarteshop.domain.EmailSetting;
import com.smarteshop.service.EmailSettingService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing EmailSetting.
 */
@RestController
@RequestMapping("/api")
public class EmailSettingController extends AbstractController {

    private final Logger log = LoggerFactory.getLogger(EmailSettingController.class);

    @Inject
    private EmailSettingService emailSettingService;

    /**
     * POST  /email-settings : Create a new emailSetting.
     *
     * @param emailSetting the emailSetting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailSetting, or with status 400 (Bad Request) if the emailSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-settings")
    @Timed
    public ResponseEntity<EmailSetting> createEmailSetting(@Valid @RequestBody EmailSetting emailSetting) throws URISyntaxException {
        log.debug("REST request to save EmailSetting : {}", emailSetting);
        if (emailSetting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("emailSetting", "idexists", "A new emailSetting cannot already have an ID")).body(null);
        }
        EmailSetting result = emailSettingService.save(emailSetting);
        return ResponseEntity.created(new URI("/api/email-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("emailSetting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-settings : Updates an existing emailSetting.
     *
     * @param emailSetting the emailSetting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailSetting,
     * or with status 400 (Bad Request) if the emailSetting is not valid,
     * or with status 500 (Internal Server Error) if the emailSetting couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-settings")
    @Timed
    public ResponseEntity<EmailSetting> updateEmailSetting(@Valid @RequestBody EmailSetting emailSetting) throws URISyntaxException {
        log.debug("REST request to update EmailSetting : {}", emailSetting);
        if (emailSetting.getId() == null) {
            return createEmailSetting(emailSetting);
        }
        EmailSetting result = emailSettingService.save(emailSetting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("emailSetting", emailSetting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-settings : get all the emailSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emailSettings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/email-settings")
    @Timed
    public ResponseEntity<List<EmailSetting>> getAllEmailSettings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EmailSettings");
        Page<EmailSetting> page = emailSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /email-settings/:id : get the "id" emailSetting.
     *
     * @param id the id of the emailSetting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailSetting, or with status 404 (Not Found)
     */
    @GetMapping("/email-settings/{id}")
    @Timed
    public ResponseEntity<EmailSetting> getEmailSetting(@PathVariable Long id) {
        log.debug("REST request to get EmailSetting : {}", id);
        EmailSetting emailSetting = emailSettingService.findOne(id);
        return Optional.ofNullable(emailSetting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /email-settings/:id : delete the "id" emailSetting.
     *
     * @param id the id of the emailSetting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailSetting(@PathVariable Long id) {
        log.debug("REST request to delete EmailSetting : {}", id);
        emailSettingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("emailSetting", id.toString())).build();
    }

    /**
     * SEARCH  /_search/email-settings?query=:query : search for the emailSetting corresponding
     * to the query.
     *
     * @param query the query of the emailSetting search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/email-settings")
    @Timed
    public ResponseEntity<List<EmailSetting>> searchEmailSettings(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of EmailSettings for query {}", query);
        Page<EmailSetting> page = emailSettingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/email-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
