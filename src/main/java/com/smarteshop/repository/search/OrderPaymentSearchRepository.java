package com.smarteshop.repository.search;

import com.smarteshop.domain.OrderPayment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrderPayment entity.
 */
public interface OrderPaymentSearchRepository extends ElasticsearchRepository<OrderPayment, Long> {
}
