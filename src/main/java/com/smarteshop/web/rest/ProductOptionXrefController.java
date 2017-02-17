package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.ProductOptionXref;
import com.smarteshop.service.ProductOptionXrefService;
import com.smarteshop.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ProductOptionXref.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionXrefController {

    private final Logger log = LoggerFactory.getLogger(ProductOptionXrefController.class);
        
    @Inject
    private ProductOptionXrefService productOptionXrefService;

    /**
     * POST  /product-option-xrefs : Create a new productOptionXref.
     *
     * @param productOptionXref the productOptionXref to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productOptionXref, or with status 400 (Bad Request) if the productOptionXref has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-option-xrefs")
    @Timed
    public ResponseEntity<ProductOptionXref> createProductOptionXref(@RequestBody ProductOptionXref productOptionXref) throws URISyntaxException {
        log.debug("REST request to save ProductOptionXref : {}", productOptionXref);
        if (productOptionXref.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productOptionXref", "idexists", "A new productOptionXref cannot already have an ID")).body(null);
        }
        ProductOptionXref result = productOptionXrefService.save(productOptionXref);
        return ResponseEntity.created(new URI("/api/product-option-xrefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productOptionXref", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-option-xrefs : Updates an existing productOptionXref.
     *
     * @param productOptionXref the productOptionXref to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productOptionXref,
     * or with status 400 (Bad Request) if the productOptionXref is not valid,
     * or with status 500 (Internal Server Error) if the productOptionXref couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-option-xrefs")
    @Timed
    public ResponseEntity<ProductOptionXref> updateProductOptionXref(@RequestBody ProductOptionXref productOptionXref) throws URISyntaxException {
        log.debug("REST request to update ProductOptionXref : {}", productOptionXref);
        if (productOptionXref.getId() == null) {
            return createProductOptionXref(productOptionXref);
        }
        ProductOptionXref result = productOptionXrefService.save(productOptionXref);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productOptionXref", productOptionXref.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-option-xrefs : get all the productOptionXrefs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productOptionXrefs in body
     */
    @GetMapping("/product-option-xrefs")
    @Timed
    public List<ProductOptionXref> getAllProductOptionXrefs() {
        log.debug("REST request to get all ProductOptionXrefs");
        return productOptionXrefService.findAll();
    }

    /**
     * GET  /product-option-xrefs/:id : get the "id" productOptionXref.
     *
     * @param id the id of the productOptionXref to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productOptionXref, or with status 404 (Not Found)
     */
    @GetMapping("/product-option-xrefs/{id}")
    @Timed
    public ResponseEntity<ProductOptionXref> getProductOptionXref(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionXref : {}", id);
        ProductOptionXref productOptionXref = productOptionXrefService.findOne(id);
        return Optional.ofNullable(productOptionXref)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-option-xrefs/:id : delete the "id" productOptionXref.
     *
     * @param id the id of the productOptionXref to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-option-xrefs/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductOptionXref(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionXref : {}", id);
        productOptionXrefService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productOptionXref", id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-option-xrefs?query=:query : search for the productOptionXref corresponding
     * to the query.
     *
     * @param query the query of the productOptionXref search 
     * @return the result of the search
     */
    @GetMapping("/_search/product-option-xrefs")
    @Timed
    public List<ProductOptionXref> searchProductOptionXrefs(@RequestParam String query) {
        log.debug("REST request to search ProductOptionXrefs for query {}", query);
        return productOptionXrefService.search(query);
    }


}
