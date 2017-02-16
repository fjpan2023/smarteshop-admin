package com.smarteshop.repository.search;

import com.smarteshop.domain.ProductOptionValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductOptionValue entity.
 */
public interface ProductOptionValueSearchRepository extends ElasticsearchRepository<ProductOptionValue, Long> {
}
