package com.smarteshop.repository.search;

import com.smarteshop.domain.Variant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Variant entity.
 */
public interface VariantSearchRepository extends ElasticsearchRepository<Variant, Long> {
}
