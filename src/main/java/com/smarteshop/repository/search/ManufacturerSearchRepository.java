package com.smarteshop.repository.search;

import com.smarteshop.domain.Manufacturer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Manufacturer entity.
 */
public interface ManufacturerSearchRepository extends ElasticsearchRepository<Manufacturer, Long> {
}
