package com.smarteshop.repository.search;

import com.smarteshop.domain.ProductOption;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductOption entity.
 */
public interface ProductOptionSearchRepository extends ElasticsearchRepository<ProductOption, Long> {
}
