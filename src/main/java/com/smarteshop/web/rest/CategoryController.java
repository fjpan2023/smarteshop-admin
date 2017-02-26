package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.catalog.Category;
import com.smarteshop.domain.catalog.Product;
import com.smarteshop.service.CategoryService;
import com.smarteshop.service.ProductService;
import com.smarteshop.service.dto.CategoryCountInfo;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController extends AbstractController<Category>{

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Inject
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    /**
     * POST  /categories : Create a new category.
     *
     * @param category the category to create
     * @return the ResponseEntity with status 201 (Created) and with body the new category, or with status 400 (Bad Request) if the category has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);
        if (category.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("category", "idexists", "A new category cannot already have an ID")).body(null);
        }
        Category result = categoryService.save(category);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("category", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categories : Updates an existing category.
     *
     * @param category the category to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated category,
     * or with status 400 (Bad Request) if the category is not valid,
     * or with status 500 (Internal Server Error) if the category couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping()
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to update Category : {}", category);
        if (category.getId() == null) {
            return createCategory(category);
        }
        Category result = categoryService.save(category);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("category", category.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @Timed
    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Categories");
        Page<Category> page = categoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /categories/:id : get the "id" category.
     *
     * @param id the id of the category to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the category, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Category category = categoryService.findOne(id);
        return Optional.ofNullable(category)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("category", id.toString())).build();
    }

    /**
     * SEARCH  /_search/categories?query=:query : search for the category corresponding
     * to the query.
     *
     * @param query the query of the category search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @Timed
    @GetMapping("/_search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Categories for query {}", query);
        Page<Category> page = categoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Timed
    @GetMapping("/countInfo")
    public List<CategoryCountInfo>  queryCountInfo(){
      return this.categoryService.categoryCountInfo();
    }


    @Timed
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable Long id, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Categories");
        Page<Product> page = this.productService.findAllProductsByCategory(id, pageable);
       // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categories");
        return ResponseEntity.ok().body(page.getContent());
    }


    @Timed
    @GetMapping("/{id}/subcategories")
    public ResponseEntity<List<Category>> getAllSubCategoriesByCategory(@PathVariable Long id, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get sub-Categories");
        List<Category> result = this.categoryService.findAllSubCategories(id);
       // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categories");
        return ResponseEntity.ok().body(result);
    }

}
