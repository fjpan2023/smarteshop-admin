package com.smarteshop.repository.search;

import com.smarteshop.domain.Sku;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Sku entity.
 */
public interface SkuSearchRepository extends ElasticsearchRepository<Sku, Long> {
}
