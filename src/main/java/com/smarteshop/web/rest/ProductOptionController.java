package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.smarteshop.domain.catalog.ProductOption;
import com.smarteshop.domain.catalog.ProductOptionValue;
import com.smarteshop.service.ProductOptionService;
import com.smarteshop.service.ProductOptionValueService;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing ProductOption.
 */
@RestController
@RequestMapping("/api/productOptions")
public class ProductOptionController {

  private final Logger log = LoggerFactory.getLogger(ProductOptionController.class);

  @Autowired
  private ProductOptionService productOptionService;
  @Autowired
  private ProductOptionValueService productOptionValueService;

  /**
   * POST  /product-options : Create a new productOption.
   *
   * @param productOption the productOption to create
   * @return the ResponseEntity with status 201 (Created) and with body the new productOption, or with status 400 (Bad Request) if the productOption has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @Timed
  @PostMapping()
  public ResponseEntity<ProductOption> createProductOption(@RequestBody ProductOption productOption) throws URISyntaxException {
    log.debug("REST request to save ProductOption : {}", productOption);
    if (productOption.getId() != null) {
      return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productOption", "idexists", "A new productOption cannot already have an ID")).body(null);
    }
    ProductOption result = productOptionService.save(productOption);
    return ResponseEntity.created(new URI("/api/product-options/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert("productOption", result.getId().toString()))
        .body(result);
  }

  /**
   * PUT  /product-options : Updates an existing productOption.
   *
   * @param productOption the productOption to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated productOption,
   * or with status 400 (Bad Request) if the productOption is not valid,
   * or with status 500 (Internal Server Error) if the productOption couldnt be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @Timed
  @PutMapping()
  public ResponseEntity<ProductOption> updateProductOption(@RequestBody ProductOption productOption) throws URISyntaxException {
    log.debug("REST request to update ProductOption : {}", productOption);
    if (productOption.getId() == null) {
      return createProductOption(productOption);
    }
    ProductOption result = productOptionService.save(productOption);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert("productOption", productOption.getId().toString()))
        .body(result);
  }

  /**
   * GET  /product-options : get all the productOptions.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of productOptions in body
   * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
   */
  @GetMapping()
  @Timed
  public ResponseEntity<List<ProductOption>> getAllProductOptions(Pageable pageable)
      throws URISyntaxException {
    log.debug("REST request to get a page of ProductOptions");
    Page<ProductOption> page = productOptionService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-options");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET  /product-options/:id : get the "id" productOption.
   *
   * @param id the id of the productOption to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the productOption, or with status 404 (Not Found)
   */
  @Timed
  @GetMapping("/{id}")
  public ResponseEntity<ProductOption> getProductOption(@PathVariable Long id) {
    log.debug("REST request to get ProductOption : {}", id);
    ProductOption productOption = productOptionService.findOne(id);
    return Optional.ofNullable(productOption)
        .map(result -> new ResponseEntity<>(
            result,
            HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE  /product-options/:id : delete the "id" productOption.
   *
   * @param id the id of the productOption to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  /*@Timed
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductOption(@PathVariable Long id) {
    log.debug("REST request to delete ProductOption : {}", id);
    productOptionService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productOption", id.toString())).build();
  }*/


  @Timed
  @PostMapping("/{optionId}/productOptionValues")
  public ResponseEntity<ProductOption> createProductOptionValue(@PathVariable Long optionId,@RequestBody ProductOptionValue value) throws URISyntaxException {
    ProductOption productOption = this.productOptionService.findOne(optionId);
    value.setProductOption(productOption);
    this.productOptionValueService.save(value);
    productOption.addProductOptionValue(value);
    this.productOptionService.save(productOption);
    return ResponseEntity.ok().body(productOption);
  }

  @Timed
  @DeleteMapping("/{optionId}/productOptionValues/{id}")
  public ResponseEntity<ProductOption> deleteProductOptionValue(@PathVariable Long optionId, @PathVariable Long id) throws URISyntaxException {
    ProductOption productOption = this.productOptionService.findOne(optionId);
    for(ProductOptionValue each: productOption.getProductOptionValues()){
      if(each.getId()==id){
        productOption.removeProductOptionValue(each);
        break;
      }
    }
    this.productOptionService.save(productOption);
    return ResponseEntity.ok().body(productOption);
  }
}
