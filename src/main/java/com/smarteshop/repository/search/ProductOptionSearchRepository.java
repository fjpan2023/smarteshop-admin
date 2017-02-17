package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.ProductOption;

/**
 * Spring Data ElasticSearch repository for the Product entity.
 */
public interface ProductOptionSearchRepository extends ElasticsearchRepository<ProductOption, Long> {
}
