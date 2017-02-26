package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.catalog.Category;

/**
 * Spring Data ElasticSearch repository for the Category entity.
 */
public interface CategorySearchRepository extends ElasticsearchRepository<Category, Long> {
}
