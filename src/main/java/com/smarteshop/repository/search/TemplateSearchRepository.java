package com.smarteshop.repository.search;

import com.smarteshop.domain.Template;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Template entity.
 */
public interface TemplateSearchRepository extends ElasticsearchRepository<Template, Long> {
}
