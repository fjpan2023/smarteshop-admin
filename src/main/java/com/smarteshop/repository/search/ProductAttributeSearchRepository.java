package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.catalog.ProductAttribute;

/**
 * Spring Data ElasticSearch repository for the ProductAttribute entity.
 */
public interface ProductAttributeSearchRepository extends ElasticsearchRepository<ProductAttribute, Long> {
}
