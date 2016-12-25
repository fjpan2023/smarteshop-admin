package com.smarteshop.repository.search;

import com.smarteshop.domain.VariantValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the VariantValue entity.
 */
public interface VariantValueSearchRepository extends ElasticsearchRepository<VariantValue, Long> {
}
