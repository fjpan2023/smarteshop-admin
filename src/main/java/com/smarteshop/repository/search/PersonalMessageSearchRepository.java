package com.smarteshop.repository.search;

import com.smarteshop.domain.PersonalMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PersonalMessage entity.
 */
public interface PersonalMessageSearchRepository extends ElasticsearchRepository<PersonalMessage, Long> {
}
