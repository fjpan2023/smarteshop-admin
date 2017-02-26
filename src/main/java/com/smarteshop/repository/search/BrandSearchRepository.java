package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.catalog.Brand;

/**
 * Spring Data ElasticSearch repository for the Brand entity.
 */
public interface BrandSearchRepository extends ElasticsearchRepository<Brand, Long> {
}
