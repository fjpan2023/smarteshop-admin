package com.smarteshop.web.rest;

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
import com.smarteshop.domain.Brand;
import com.smarteshop.service.BrandService;
import com.smarteshop.web.rest.common.AbstractBaseController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Brand.
 */
@RestController
@RequestMapping("/api/brands")
public class BrandController extends AbstractBaseController<Brand> {

    private final Logger log = LoggerFactory.getLogger(BrandController.class);

    @Inject
    private BrandService brandService;


    /**
     * POST  /brands : Create a new brand.
     *
     * @param brand the brand to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brand, or with status 400 (Bad Request) if the brand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping()
    public ResponseEntity<Long> createBrand(@RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to save Brand : {}", brand);
        if (brand.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("brand", "idexists", "A new brand cannot already have an ID")).body(null);
        }
        Brand result = brandService.save(brand);
        return new ResponseEntity<Long>(result.getId(),HttpStatus.CREATED);
    }

    /**
     * PUT  /brands : Updates an existing brand.
     *
     * @param brand the brand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brand,
     * or with status 400 (Bad Request) if the brand is not valid,
     * or with status 500 (Internal Server Error) if the brand couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping()
    public ResponseEntity<Void> updateBrand(@RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to update Brand : {}", brand);

        brandService.save(brand);
        return ResponseEntity.ok().build();

    }

    /**
     * GET  /brands : get all the brands.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brands in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping()
    @Timed
    public ResponseEntity<List<Brand>> getAllBrands(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Brands");
        Page<Brand> page = brandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brands/:id : get the "id" brand.
     *
     * @param id the id of the brand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brand, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        log.debug("REST request to get Brand : {}", id);
        Brand brand = brandService.findOne(id);
        return Optional.ofNullable(brand)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /brands/:id : delete the "id" brand.
     *
     * @param id the id of the brand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        log.debug("REST request to delete Brand : {}", id);
        brandService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param query the query of the brand search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @Timed
    @GetMapping("/_search")
    public ResponseEntity<List<Brand>> searchBrands(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Brands for query {}", query);
        Page<Brand> page = brandService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/brands/_search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
