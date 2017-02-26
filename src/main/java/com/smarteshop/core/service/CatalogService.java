package com.smarteshop.core.service;

import java.util.Date;
import java.util.List;

import com.smarteshop.domain.ProductBundle;
import com.smarteshop.domain.catalog.Category;
import com.smarteshop.domain.catalog.Product;
import com.smarteshop.domain.catalog.ProductOption;
import com.smarteshop.domain.catalog.ProductOptionValue;
import com.smarteshop.domain.catalog.Sku;

public interface CatalogService {

	public Product saveProduct(Product product);

	public Product findProductById(Long productId);

	public Product findProductByExternalId(String externalId);

	public List<Product> findActiveProductsByCategory(Category category);

	/*public List<Product> findFilteredActiveProductsByCategory(Category category, SearchCriteria searchCriteria);
	public List<Product> findFilteredActiveProductsByCategory(Category category, Date currentDate, SearchCriteria searchCriteria);

	public List<Product> findFilteredActiveProductsByQuery(String query, SearchCriteria searchCriteria);

	public List<Product> findFilteredActiveProductsByQuery(String query, Date currentDate, SearchCriteria searchCriteria);

	*/public List<Product> findActiveProductsByCategory(Category category, int limit, int offset);
	public List<ProductBundle> findAutomaticProductBundles();


	public Category saveCategory(Category category);

	public void removeCategory(Category category);

	public void removeProduct(Product product);

	public void removeSku(Sku sku);

	public Category findCategoryById(Long categoryId);

	public Category findCategoryByExternalId(String externalId);
	public List<Category> findCategoriesByName(String categoryName);

	public List<Category> findCategoriesByName(String categoryName, int limit, int offset);

	public List<Category> findAllCategories();

	public List<Category> findAllCategories(int limit, int offset);

	public List<Product> findAllProducts();

	public List<Product> findAllProducts(int limit, int offset);

	public List<Product> findProductsForCategory(Category category);

	public List<Product> findProductsForCategory(Category category, int limit, int offset);

	public Sku saveSku(Sku sku);

/*	public SkuFee saveSkuFee(SkuFee fee);
*/
	public List<Sku> findAllSkus();

	public List<Sku> findSkusByIds(List<Long> ids);

	public Sku findSkuById(Long skuId);

	public Sku findSkuByExternalId(String externalId);

	public Sku findSkuByUpc(String upc);


	public Category createCategory();

	public Sku createSku();

	/*	    public Product createProduct(ProductType productType);
	 */
	public Category findRootCategory();

	public List<Category> findAllSubCategories(Category category);

	public List<Category> findAllSubCategories(Category category, int limit, int offset);

	public List<Category> findActiveSubCategoriesByCategory(Category category);

	public List<Category> findActiveSubCategoriesByCategory(Category category, int limit, int offset);

	public List<ProductOption> readAllProductOptions();

	public ProductOption saveProductOption(ProductOption option);

	public ProductOption findProductOptionById(Long productOptionId);

	public ProductOptionValue findProductOptionValueById(Long productOptionValueId);

	public Category findCategoryByURI(String uri);

	public Product findProductByURI(String uri);

	public Sku findSkuByURI(String uri);

	/*public List<AssignedProductOptionDTO> findAssignedProductOptionsByProductId(Long productId);

	*//**
	 * Returns a list of {@link org.broadleafcommerce.core.catalog.domain.dto.AssignedProductOptionDTO}
	 * found for given the {@link org.broadleafcommerce.core.catalog.domain.Product}.
	 *
	 * @param product
	 * @return
	 *//*
	public List<AssignedProductOptionDTO> findAssignedProductOptionsByProduct(Product product);*/

}
