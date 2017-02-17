package com.smarteshop.repository.search;

import com.smarteshop.domain.SkuProductOptionValueXref;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SkuProductOptionValueXref entity.
 */
public interface SkuProductOptionValueXrefSearchRepository extends ElasticsearchRepository<SkuProductOptionValueXref, Long> {
}
