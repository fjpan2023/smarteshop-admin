package com.smarteshop.repository.search;

import com.smarteshop.domain.OrderItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ItemOrder entity.
 */
public interface ItemOrderSearchRepository extends ElasticsearchRepository<OrderItem, Long> {
}
