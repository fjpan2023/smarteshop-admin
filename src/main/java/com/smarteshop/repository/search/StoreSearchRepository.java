package com.smarteshop.repository.search;

import com.smarteshop.domain.MerchantStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Store entity.
 */
public interface StoreSearchRepository extends ElasticsearchRepository<MerchantStore, Long> {
}
