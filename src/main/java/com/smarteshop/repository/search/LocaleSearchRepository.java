package com.smarteshop.repository.search;

import com.smarteshop.domain.Locale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Locale entity.
 */
public interface LocaleSearchRepository extends ElasticsearchRepository<Locale, Long> {
}
