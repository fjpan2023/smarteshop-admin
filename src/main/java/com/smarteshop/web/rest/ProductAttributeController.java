package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.ProductAttribute;
import com.smarteshop.service.ProductAttributeService;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductAttribute.
 */
@RestController
@RequestMapping("/api")
public class ProductAttributeController {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeController.class);
        
    @Inject
    private ProductAttributeService productAttributeService;

    /**
     * POST  /product-attributes : Create a new productAttribute.
     *
     * @param productAttribute the productAttribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productAttribute, or with status 400 (Bad Request) if the productAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-attributes")
    @Timed
    public ResponseEntity<ProductAttribute> createProductAttribute(@Valid @RequestBody ProductAttribute productAttribute) throws URISyntaxException {
        log.debug("REST request to save ProductAttribute : {}", productAttribute);
        if (productAttribute.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productAttribute", "idexists", "A new productAttribute cannot already have an ID")).body(null);
        }
        ProductAttribute result = productAttributeService.save(productAttribute);
        return ResponseEntity.created(new URI("/api/product-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productAttribute", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-attributes : Updates an existing productAttribute.
     *
     * @param productAttribute the productAttribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productAttribute,
     * or with status 400 (Bad Request) if the productAttribute is not valid,
     * or with status 500 (Internal Server Error) if the productAttribute couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-attributes")
    @Timed
    public ResponseEntity<ProductAttribute> updateProductAttribute(@Valid @RequestBody ProductAttribute productAttribute) throws URISyntaxException {
        log.debug("REST request to update ProductAttribute : {}", productAttribute);
        if (productAttribute.getId() == null) {
            return createProductAttribute(productAttribute);
        }
        ProductAttribute result = productAttributeService.save(productAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productAttribute", productAttribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-attributes : get all the productAttributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productAttributes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-attributes")
    @Timed
    public ResponseEntity<List<ProductAttribute>> getAllProductAttributes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductAttributes");
        Page<ProductAttribute> page = productAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-attributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-attributes/:id : get the "id" productAttribute.
     *
     * @param id the id of the productAttribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productAttribute, or with status 404 (Not Found)
     */
    @GetMapping("/product-attributes/{id}")
    @Timed
    public ResponseEntity<ProductAttribute> getProductAttribute(@PathVariable Long id) {
        log.debug("REST request to get ProductAttribute : {}", id);
        ProductAttribute productAttribute = productAttributeService.findOne(id);
        return Optional.ofNullable(productAttribute)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-attributes/:id : delete the "id" productAttribute.
     *
     * @param id the id of the productAttribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttribute : {}", id);
        productAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productAttribute", id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-attributes?query=:query : search for the productAttribute corresponding
     * to the query.
     *
     * @param query the query of the productAttribute search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/product-attributes")
    @Timed
    public ResponseEntity<List<ProductAttribute>> searchProductAttributes(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ProductAttributes for query {}", query);
        Page<ProductAttribute> page = productAttributeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-attributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
