package com.smarteshop.repository.search;

import com.smarteshop.domain.OrderItemPriceDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrderItemPrice entity.
 */
public interface OrderItemPriceSearchRepository extends ElasticsearchRepository<OrderItemPriceDetail, Long> {
}
