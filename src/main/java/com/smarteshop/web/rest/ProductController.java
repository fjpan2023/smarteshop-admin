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
import com.smarteshop.domain.Product;
import com.smarteshop.service.ProductService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController extends AbstractController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Inject
    private ProductService productService;

    /**
     * POST   : Create a new product.
     *
     * @param product the product to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product, or with status 400 (Bad Request) if the product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping("")  
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("product", "idexists", "A new product cannot already have an ID")).body(null);
        }
        Product result = productService.save(product);
        productService.saveImages(result.getId(),product.getImages());        
        return ResponseEntity.created(new URI("/api/products" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("product", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT   : Updates an existing product.
     *
     * @param product the product to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product,
     * or with status 400 (Bad Request) if the product is not valid,
     * or with status 500 (Internal Server Error) if the product couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping( )  
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);
        if (product.getId() == null) {
            return createProduct(product);
        }
        Product result = productService.save(product);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("product", product.getId().toString()))
            .body(result);
    }

    /**
     * GET   : get all the products.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping( )
    @Timed
    public ResponseEntity<List<Product>> getAllProducts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Products");
        Page<Product> page = productService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" product.
     *
     * @param id the id of the product to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Product product = productService.findOne(id);
        return Optional.ofNullable(product)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /:id : delete the "id" product.
     *
     * @param id the id of the product to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("product", id.toString())).build();
    }

    /**
     * SEARCH  /_search?query=:query : search for the product corresponding
     * to the query.
     *
     * @param query the query of the product search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search")
    @Timed
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Products for query {}", query);
        Page<Product> page = productService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
