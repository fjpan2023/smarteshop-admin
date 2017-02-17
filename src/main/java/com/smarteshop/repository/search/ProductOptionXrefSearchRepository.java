package com.smarteshop.repository.search;

import com.smarteshop.domain.ProductOptionXref;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductOptionXref entity.
 */
public interface ProductOptionXrefSearchRepository extends ElasticsearchRepository<ProductOptionXref, Long> {
}
