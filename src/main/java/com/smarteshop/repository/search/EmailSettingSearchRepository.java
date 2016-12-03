package com.smarteshop.repository.search;

import com.smarteshop.domain.EmailSetting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmailSetting entity.
 */
public interface EmailSettingSearchRepository extends ElasticsearchRepository<EmailSetting, Long> {
}
