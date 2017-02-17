package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.ProductOptionValue;

/**
 * Spring Data ElasticSearch repository for the Product entity.
 */
public interface ProductOptionValueSearchRepository extends ElasticsearchRepository<ProductOptionValue, Long> {
}
