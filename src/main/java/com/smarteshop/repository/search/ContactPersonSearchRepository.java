package com.smarteshop.repository.search;

import com.smarteshop.domain.ContactPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ContactPerson entity.
 */
public interface ContactPersonSearchRepository extends ElasticsearchRepository<ContactPerson, Long> {
}
