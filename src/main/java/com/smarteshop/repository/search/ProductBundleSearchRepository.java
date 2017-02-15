package com.smarteshop.repository.search;

import com.smarteshop.domain.ProductBundle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductBundle entity.
 */
public interface ProductBundleSearchRepository extends ElasticsearchRepository<ProductBundle, Long> {
}
