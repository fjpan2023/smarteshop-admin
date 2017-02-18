package com.smarteshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.smarteshop.common.service.BusinessObjectEntityServiceImpl;
import com.smarteshop.domain.Attachment;
import com.smarteshop.domain.Product;
import com.smarteshop.domain.ProductOption;
import com.smarteshop.domain.ProductOptionValue;
import com.smarteshop.domain.QProduct;
import com.smarteshop.domain.Sku;
import com.smarteshop.repository.ProductRepository;
import com.smarteshop.repository.SkuRepository;
import com.smarteshop.repository.search.ProductSearchRepository;
import com.smarteshop.service.AttachmentService;
import com.smarteshop.service.ProductService;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl extends BusinessObjectEntityServiceImpl<Long, Product> implements ProductService{

	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	private ProductRepository productRepository;

	@Autowired
	private ProductSearchRepository productSearchRepository;
	@Autowired
	private SkuRepository skuRepository;

	@Autowired
	private AttachmentService attachmentService;

	public ProductServiceImpl(ProductRepository productRepository){
		super(productRepository);
		this.productRepository = productRepository;

	}
	/**
	 * Save a product.
	 *
	 * @param product the entity to save
	 * @return the persisted entity
	 */
	@Override
	public Product save(Product product) {
		log.debug("Request to save Product : {}", product);
		Product result = productRepository.save(product);
		productSearchRepository.save(result);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Product findOne(Long id) {
		log.debug("Request to get Product : {}", id);
		Product product = productRepository.findOne(id);
		return product;
	}

	/**
	 *  Delete the  product by id.
	 *
	 *  @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Product : {}", id);
		productRepository.delete(id);
		productSearchRepository.delete(id);
	}

	/**
	 * Search for the product corresponding to the query.
	 *
	 *  @param query the query of the search
	 *  @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Product> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Products for query {}", query);
		Page<Product> result = productSearchRepository.search(queryStringQuery(query), pageable);
		return result;
	}

	@Override
	public void saveImages(Long productId, Collection<Attachment> images) {
		for(Attachment each : images){
			each.setEntityId(productId);
			this.attachmentService.save(each);
		}

	}

	@Override
	public boolean exist(String name) {
		QProduct qProduct = QProduct.product;
		Predicate predicate = qProduct.name.eq(name);
		return this.productRepository.exists(predicate);
	}

	@Override
	public Page<Product> findRelatedProduct(Long id, Pageable pageable) {
		log.debug("Request to get all Products");
		// Specification spec = null;
		Page<Product> result =null;// productRepository.findAll(predicate, pageable);
		return result;
	}

	@Override
	public Page<Product> findAll(Predicate predicate, Pageable pageable) {
		log.debug("Request to get all Products");
		Page<Product> result = productRepository.findAll(predicate, pageable);
		return result;
	}
	@Override
	public void createSKUsByBatch(Long productId, List<String> productOptionValues) {
		// TODO Auto-generated method stub

	}
	@Override
	public void generateAdditionalSkusByBatch(Long productId) {
		Product product = this.productRepository.getOne(productId);
		if(product ==null){
			throw new RuntimeException("null option");
		}
		Set<ProductOption> productOptions = product.getProductOptions();
		if(CollectionUtils.isEmpty(productOptions)){
		  log.info("product options is empty,product{} ", product.getId());
			return;
		}
		List<List<ProductOptionValue>> allPermutations = generatePermutations(0, new ArrayList<ProductOptionValue>(), new ArrayList(product.getProductOptions()));
		if (allPermutations == null) {
			return ;
		}
		List<List<ProductOptionValue>> previouslyGeneratedPermutations = new ArrayList<List<ProductOptionValue>>();
		if (CollectionUtils.isNotEmpty(product.getAdditionalSkus())) {
			for (Sku additionalSku : product.getAdditionalSkus()) {
				if (CollectionUtils.isNotEmpty(additionalSku.getProductOptionValues())) {
					previouslyGeneratedPermutations.add(new ArrayList(additionalSku.getProductOptionValues()));
				}
			}
		}

		List<List<ProductOptionValue>> permutationsToGenerate = new ArrayList<List<ProductOptionValue>>();
		for (List<ProductOptionValue> permutation : allPermutations) {
			boolean previouslyGenerated = false;
			for (List<ProductOptionValue> generatedPermutation : previouslyGeneratedPermutations) {
				if (isSamePermutation(permutation, generatedPermutation)) {
					previouslyGenerated = true;
					break;
				}
			}

			if (!previouslyGenerated) {
				permutationsToGenerate.add(permutation);
			}
		}

		Sku defaultSku = product.getDefaultSku();
		for (List<ProductOptionValue> permutation : permutationsToGenerate) {
			if (permutation.isEmpty())
				continue;
			Sku permutatedSku = new Sku();
			permutatedSku.setName(defaultSku.getName());
			permutatedSku.setRetailPrice(defaultSku.getRetailPrice());
			permutatedSku.setSalePrice(defaultSku.getSalePrice());
			permutatedSku.setDescription(defaultSku.getDescription());
			permutatedSku.setActiveEndDate(defaultSku.getActiveEndDate());
			permutatedSku.setActiveStartDate(defaultSku.getActiveStartDate());
			permutatedSku.setProduct(product);
			log.info("permutation list : ",permutation);
			permutatedSku = this.skuRepository.save(permutatedSku);


			permutatedSku.setProductOptionValues(new HashSet<ProductOptionValue>(permutation));
			this.skuRepository.save(permutatedSku);
			product.getAdditionalSkus().add(permutatedSku);
		}
		return ;
	}
	private  boolean isSamePermutation(List<ProductOptionValue> perm1, List<ProductOptionValue> perm2) {
		if (perm1.size() == perm2.size()) {
			Collection<Long> perm1Ids = CollectionUtils.collect(perm1, new Transformer<ProductOptionValue, Long>() {
				@Override
				public Long transform(ProductOptionValue input) {
					// TODO Auto-generated method stub
					return input.getId();
				}
			});
			Collection<Long> perm2Ids = CollectionUtils.collect(perm2, new Transformer<ProductOptionValue, Long>() {
				@Override
				public Long transform(ProductOptionValue input) {
					// TODO Auto-generated method stub
					return input.getId();
				}
			});
			return perm1Ids.containsAll(perm2Ids);
		}
		return false;
	}

	private List<List<ProductOptionValue>> generatePermutations(int currentTypeIndex, List<ProductOptionValue> currentPermutation, List<ProductOption> options) {
		List<List<ProductOptionValue>> result = new ArrayList<List<ProductOptionValue>>();
		if (currentTypeIndex == options.size()) {
			result.add(currentPermutation);
			return result;
		}

		ProductOption currentOption = options.get(currentTypeIndex);
		Set<ProductOptionValue> allowedValues = currentOption.getProductOptionValues();
		// Check to make sure there is at least 1 Allowed Value, else prevent generation
		if (CollectionUtils.isEmpty(allowedValues)) {
          result.addAll(generatePermutations(currentTypeIndex + 1, currentPermutation, options));
		}
		for (ProductOptionValue option : allowedValues) {
			List<ProductOptionValue> permutation = new ArrayList<ProductOptionValue>();
			permutation.addAll(currentPermutation);
			permutation.add(option);
			result.addAll(generatePermutations(currentTypeIndex + 1, permutation, options));
		}
		return result;
	}


}
