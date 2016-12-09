package com.smarteshop.repository.search;

import com.smarteshop.domain.CustomerGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CustomerGroup entity.
 */
public interface CustomerGroupSearchRepository extends ElasticsearchRepository<CustomerGroup, Long> {
}
