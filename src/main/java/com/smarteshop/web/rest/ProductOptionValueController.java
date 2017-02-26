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
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.catalog.ProductOptionValue;
import com.smarteshop.service.ProductOptionValueService;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing ProductOptionValue.
 */
@RestController
@RequestMapping("/api/productOptionValues")
public class ProductOptionValueController {

    private final Logger log = LoggerFactory.getLogger(ProductOptionValueController.class);

    @Inject
    private ProductOptionValueService productOptionValueService;

    @Timed
    @PostMapping()
    public ResponseEntity<ProductOptionValue> createProductOptionValue(@RequestBody ProductOptionValue productOptionValue) throws URISyntaxException {
        log.debug("REST request to save ProductOptionValue : {}", productOptionValue);
        if (productOptionValue.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productOptionValue", "idexists", "A new productOptionValue cannot already have an ID")).body(null);
        }
        ProductOptionValue result = productOptionValueService.save(productOptionValue);
        return ResponseEntity.created(new URI("/api/product-option-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productOptionValue", result.getId().toString()))
            .body(result);
    }

    @Timed
    @PutMapping()
    public ResponseEntity<ProductOptionValue> updateProductOptionValue(@RequestBody ProductOptionValue productOptionValue) throws URISyntaxException {
        log.debug("REST request to update ProductOptionValue : {}", productOptionValue);
        if (productOptionValue.getId() == null) {
            return createProductOptionValue(productOptionValue);
        }
        ProductOptionValue result = productOptionValueService.save(productOptionValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productOptionValue", productOptionValue.getId().toString()))
            .body(result);
    }

    @Timed
    @GetMapping()
    public ResponseEntity<List<ProductOptionValue>> getAllProductOptionValues(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductOptionValues");
        Page<ProductOptionValue> page = productOptionValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-option-values");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<ProductOptionValue> getProductOptionValue(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionValue : {}", id);
        ProductOptionValue productOptionValue = productOptionValueService.findOne(id);
        return Optional.ofNullable(productOptionValue)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductOptionValue(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionValue : {}", id);
        productOptionValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productOptionValue", id.toString())).build();
    }
}
