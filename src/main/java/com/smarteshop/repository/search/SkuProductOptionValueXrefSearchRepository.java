package com.smarteshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smarteshop.domain.catalog.SkuProductOptionValueXref;

/**
 * Spring Data ElasticSearch repository for the SkuProductOptionValueXref entity.
 */
public interface SkuProductOptionValueXrefSearchRepository extends ElasticsearchRepository<SkuProductOptionValueXref, Long> {
}
