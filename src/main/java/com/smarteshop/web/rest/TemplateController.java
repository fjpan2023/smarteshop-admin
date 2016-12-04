package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import com.smarteshop.domain.Template;
import com.smarteshop.service.TemplateService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api/templates")
public class TemplateController extends AbstractController{

    private final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @Inject
    private TemplateService templateService;

    /**
     * POST  /templates : Create a new template.
     *
     * @param template the template to create
     * @return the ResponseEntity with status 201 (Created) and with body the new template, or with status 400 (Bad Request) if the template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping()
    @Timed
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) throws URISyntaxException {
        log.debug("REST request to save Template : {}", template);
        if (template.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("template", "idexists", "A new template cannot already have an ID")).body(null);
        }
        Template result = templateService.save(template);
        return ResponseEntity.created(new URI("/api /" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("template", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing template.
     *
     * @param template the template to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated template,
     * or with status 400 (Bad Request) if the template is not valid,
     * or with status 500 (Internal Server Error) if the template couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping()
    @Timed
    public ResponseEntity<Template> updateTemplate(@RequestBody Template template) throws URISyntaxException {
        log.debug("REST request to update Template : {}", template);
        if (template.getId() == null) {
            return createTemplate(template);
        }
        Template result = templateService.save(template);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("template", template.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping()
    @Timed
    public ResponseEntity<List<Template>> getAllTemplates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Templates");
        Page<Template> page = templateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the template to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the template, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<Template> getTemplate(@PathVariable Long id) {
        log.debug("REST request to get Template : {}", id);
        Template template = templateService.findOne(id);
        return Optional.ofNullable(template)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /templates/:id : delete the "id" template.
     *
     * @param id the id of the template to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        log.debug("REST request to delete Template : {}", id);
        templateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("template", id.toString())).build();
    }

    /**
     * SEARCH  /_search/templates?query=:query : search for the template corresponding
     * to the query.
     *
     * @param query the query of the template search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search")
    @Timed
    public ResponseEntity<List<Template>> searchTemplates(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Templates for query {}", query);
        Page<Template> page = templateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api//templates/_search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
