package com.smarteshop.repository.search;

import com.smarteshop.domain.ProductAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductAttribute entity.
 */
public interface ProductAttributeSearchRepository extends ElasticsearchRepository<ProductAttribute, Long> {
}
