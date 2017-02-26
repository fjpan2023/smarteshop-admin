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
import com.smarteshop.domain.Manufacturer;
import com.smarteshop.domain.catalog.Sku;
import com.smarteshop.service.ManufacturerService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api")
public class ManufacturerController extends AbstractController<Sku>{

    private final Logger log = LoggerFactory.getLogger(ManufacturerController.class);

    @Inject
    private ManufacturerService manufacturerService;

    /**
     * POST  /manufacturers : Create a new manufacturer.
     *
     * @param manufacturer the manufacturer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manufacturer, or with status 400 (Bad Request) if the manufacturer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manufacturers")
    @Timed
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        if (manufacturer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("manufacturer", "idexists", "A new manufacturer cannot already have an ID")).body(null);
        }
        Manufacturer result = manufacturerService.save(manufacturer);
        return ResponseEntity.created(new URI("/api/manufacturers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("manufacturer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manufacturers : Updates an existing manufacturer.
     *
     * @param manufacturer the manufacturer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manufacturer,
     * or with status 400 (Bad Request) if the manufacturer is not valid,
     * or with status 500 (Internal Server Error) if the manufacturer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manufacturers")
    @Timed
    public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to update Manufacturer : {}", manufacturer);
        if (manufacturer.getId() == null) {
            return createManufacturer(manufacturer);
        }
        Manufacturer result = manufacturerService.save(manufacturer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("manufacturer", manufacturer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manufacturers : get all the manufacturers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of manufacturers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/manufacturers")
    @Timed
    public ResponseEntity<List<Manufacturer>> getAllManufacturers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Manufacturers");
        Page<Manufacturer> page = manufacturerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manufacturers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /manufacturers/:id : get the "id" manufacturer.
     *
     * @param id the id of the manufacturer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manufacturer, or with status 404 (Not Found)
     */
    @GetMapping("/manufacturers/{id}")
    @Timed
    public ResponseEntity<Manufacturer> getManufacturer(@PathVariable Long id) {
        log.debug("REST request to get Manufacturer : {}", id);
        Manufacturer manufacturer = manufacturerService.findOne(id);
        return Optional.ofNullable(manufacturer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /manufacturers/:id : delete the "id" manufacturer.
     *
     * @param id the id of the manufacturer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manufacturers/{id}")
    @Timed
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        log.debug("REST request to delete Manufacturer : {}", id);
        manufacturerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("manufacturer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/manufacturers?query=:query : search for the manufacturer corresponding
     * to the query.
     *
     * @param query the query of the manufacturer search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/manufacturers")
    @Timed
    public ResponseEntity<List<Manufacturer>> searchManufacturers(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Manufacturers for query {}", query);
        Page<Manufacturer> page = manufacturerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/manufacturers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
