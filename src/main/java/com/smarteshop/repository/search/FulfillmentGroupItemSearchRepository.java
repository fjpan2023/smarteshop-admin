package com.smarteshop.repository.search;

import com.smarteshop.domain.FulfillmentGroupItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FulfillmentGroupItem entity.
 */
public interface FulfillmentGroupItemSearchRepository extends ElasticsearchRepository<FulfillmentGroupItem, Long> {
}
