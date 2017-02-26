package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.catalog.RelatedProduct;

/**
 * Spring Data ElasticSearch repository for the RelatedProduct entity.
 */
public interface RelatedProductSearchRepository extends ElasticsearchRepository<RelatedProduct, Long> {
}
