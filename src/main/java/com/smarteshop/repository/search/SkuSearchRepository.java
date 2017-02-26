package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.catalog.Sku;

/**
 * Spring Data ElasticSearch repository for the Sku entity.
 */
public interface SkuSearchRepository extends ElasticsearchRepository<Sku, Long> {
}
