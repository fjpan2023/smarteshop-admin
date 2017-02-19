package com.smarteshop.repository.search;

import com.smarteshop.domain.Media;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Media entity.
 */
public interface MediaSearchRepository extends ElasticsearchRepository<Media, Long> {
}
