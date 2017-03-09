package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.Locale;
import com.smarteshop.service.LocaleService;
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
 * REST controller for managing Locale.
 */
@RestController
@RequestMapping("/api")
public class LocaleResource {

    private final Logger log = LoggerFactory.getLogger(LocaleResource.class);
        
    @Inject
    private LocaleService localeService;

    /**
     * POST  /locales : Create a new locale.
     *
     * @param locale the locale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locale, or with status 400 (Bad Request) if the locale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locales")
    @Timed
    public ResponseEntity<Locale> createLocale(@RequestBody Locale locale) throws URISyntaxException {
        log.debug("REST request to save Locale : {}", locale);
        if (locale.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("locale", "idexists", "A new locale cannot already have an ID")).body(null);
        }
        Locale result = localeService.save(locale);
        return ResponseEntity.created(new URI("/api/locales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("locale", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locales : Updates an existing locale.
     *
     * @param locale the locale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locale,
     * or with status 400 (Bad Request) if the locale is not valid,
     * or with status 500 (Internal Server Error) if the locale couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locales")
    @Timed
    public ResponseEntity<Locale> updateLocale(@RequestBody Locale locale) throws URISyntaxException {
        log.debug("REST request to update Locale : {}", locale);
        if (locale.getId() == null) {
            return createLocale(locale);
        }
        Locale result = localeService.save(locale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("locale", locale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locales : get all the locales.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of locales in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/locales")
    @Timed
    public ResponseEntity<List<Locale>> getAllLocales(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Locales");
        Page<Locale> page = localeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/locales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /locales/:id : get the "id" locale.
     *
     * @param id the id of the locale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locale, or with status 404 (Not Found)
     */
    @GetMapping("/locales/{id}")
    @Timed
    public ResponseEntity<Locale> getLocale(@PathVariable Long id) {
        log.debug("REST request to get Locale : {}", id);
        Locale locale = localeService.findOne(id);
        return Optional.ofNullable(locale)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /locales/:id : delete the "id" locale.
     *
     * @param id the id of the locale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locales/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocale(@PathVariable Long id) {
        log.debug("REST request to delete Locale : {}", id);
        localeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("locale", id.toString())).build();
    }

    /**
     * SEARCH  /_search/locales?query=:query : search for the locale corresponding
     * to the query.
     *
     * @param query the query of the locale search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/locales")
    @Timed
    public ResponseEntity<List<Locale>> searchLocales(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Locales for query {}", query);
        Page<Locale> page = localeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/locales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
