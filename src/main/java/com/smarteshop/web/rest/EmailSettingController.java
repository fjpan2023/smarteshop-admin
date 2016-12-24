package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.EmailSetting;
import com.smarteshop.service.EmailSettingService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;

/**
 * REST controller for managing EmailSetting.
 */
@RestController
@RequestMapping("/api/emailSetting")
public class EmailSettingController extends AbstractController<EmailSetting> {

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
    @PostMapping("")
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
    @PutMapping()
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
    @GetMapping()
    @Timed
    public ResponseEntity<EmailSetting> getAllEmailSettings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EmailSettings");
        EmailSetting emailSetting= emailSettingService.find();
        return new ResponseEntity<>(emailSetting, HttpStatus.OK);
    }
}
