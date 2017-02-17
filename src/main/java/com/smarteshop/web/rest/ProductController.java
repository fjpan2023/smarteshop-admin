package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
import com.querydsl.core.types.Predicate;
import com.smarteshop.domain.Product;
import com.smarteshop.domain.Sku;
import com.smarteshop.exception.BusinessException;
import com.smarteshop.service.ProductService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController extends AbstractController<Product> {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

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
		LOGGER.debug("REST request to save Product : {}", product);
		if (product.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("product", "idexists", "A new product cannot already have an ID")).body(null);
		}
		if(this.productService.exist(product.getName(), product.getCode())){
			throw new  BusinessException("{} / {}has been existed", product.getName(), product.getCode());
		}
		Sku defaultSKU = product.getDefaultSku();
		defaultSKU.setDefaultProduct(product);
		defaultSKU.setName(product.getName());
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
		LOGGER.debug("REST request to update Product : {}", product);
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
	@Timed
	@GetMapping( )
	public ResponseEntity<List<Product>> getAllProducts(@QuerydslPredicate(root=Product.class) Predicate predicate,Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of Products");
		Page<Product> page = productService.findAll( pageable);
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
		LOGGER.debug("REST request to get Product : {}", id);
		Product product = productService.findOne(id);
		return Optional.ofNullable(product)
				.map(result -> new ResponseEntity<>(
						result,
						HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	@Timed
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		LOGGER.debug("REST request to delete Product : {}", id);
		productService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("product", id.toString())).build();
	}

	@GetMapping("/_search")
	@Timed
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String query, Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to search for a page of Products for query {}", query);
		Page<Product> page = productService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	
	@Timed
	@PostMapping("{id}/additionalSkus/batch")
	public ResponseEntity<Long> generateAdditionalSkusByBatch(@PathVariable Long id) throws URISyntaxException {

		return ResponseEntity.ok().body(1L);
	}
	
	
	@Timed
	@PostMapping("{id}/additionalSkus")
	public ResponseEntity<Long> createAdditionalSkus(@PathVariable Long id, @Valid @RequestBody Sku sku) throws URISyntaxException {

		return ResponseEntity.ok().body(1L);
	}
	
	@Timed
	@GetMapping("{id}/additionalSkus")
	public ResponseEntity<List<Sku>> listAdditionalSkus(@PathVariable Long id, @Valid @RequestBody Sku sku) throws URISyntaxException {

		return ResponseEntity.ok().body(new LinkedList<Sku>());
	}
	
	@Timed
	@GetMapping("{id}/additionalSkus/{skuId}")
	public ResponseEntity<Void> listAdditionalSkus(@PathVariable Long id,@PathVariable Long skuId, @Valid @RequestBody Sku sku) throws URISyntaxException {

		return ResponseEntity.ok().build();
	}


	@Timed
	@GetMapping("/{id}/relatedProducts")
	public ResponseEntity<List<Product>> relatedProducts(@PathVariable Long id, Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of Products");
		Page<Product> page = productService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/{}");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@Timed
	@PutMapping("/{id}/skus")
	public ResponseEntity<Void> createSKUs(@PathVariable Long id, @Valid @RequestBody Product product)
			throws URISyntaxException {
		LOGGER.debug("create skus for this product{}",product.getId());
		return ResponseEntity.ok().build();
	}

}
