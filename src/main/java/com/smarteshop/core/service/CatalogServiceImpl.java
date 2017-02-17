package com.smarteshop.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarteshop.domain.Category;
import com.smarteshop.domain.Product;
import com.smarteshop.domain.ProductBundle;
import com.smarteshop.domain.ProductOption;
import com.smarteshop.domain.ProductOptionValue;
import com.smarteshop.domain.Sku;
import com.smarteshop.repository.SkuRepository;
import com.smarteshop.service.ProductOptionService;
import com.smarteshop.service.ProductOptionValueService;
import com.smarteshop.service.ProductService;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductOptionService productOptionService;
	@Autowired
	private ProductOptionValueService productOptionValueService;
	
	@Autowired
	private SkuRepository skuValueRepository;
	@Override
	public Product saveProduct(Product product) {
		this.productService.save(product);
		return null;
	}

	@Override
	public Product findProductById(Long productId) {
		return this.productService.findOne(productId);
	}

	@Override
	public Product findProductByExternalId(String externalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findActiveProductsByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findActiveProductsByCategory(Category category, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBundle> findAutomaticProductBundles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category saveCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCategory(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSku(Sku sku) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category findCategoryById(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findCategoryByExternalId(String externalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findCategoriesByName(String categoryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findCategoriesByName(String categoryName, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAllCategories(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findAllProducts(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findProductsForCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findProductsForCategory(Category category, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku saveSku(Sku sku) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sku> findAllSkus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sku> findSkusByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku findSkuById(Long skuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku findSkuByExternalId(String externalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku findSkuByUpc(String upc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category createCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku createSku() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findRootCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAllSubCategories(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAllSubCategories(Category category, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findActiveSubCategoriesByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findActiveSubCategoriesByCategory(Category category, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductOption> readAllProductOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductOption saveProductOption(ProductOption option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductOption findProductOptionById(Long productOptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductOptionValue findProductOptionValueById(Long productOptionValueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findCategoryByURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductByURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sku findSkuByURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

}
