package com.smarteshop.repository.search;

import com.smarteshop.domain.RelatedProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RelatedProduct entity.
 */
public interface RelatedProductSearchRepository extends ElasticsearchRepository<RelatedProduct, Long> {
}
