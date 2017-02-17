package com.smarteshop.repository.search;

import com.smarteshop.domain.SkuAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SkuAttribute entity.
 */
public interface SkuAttributeSearchRepository extends ElasticsearchRepository<SkuAttribute, Long> {
}
