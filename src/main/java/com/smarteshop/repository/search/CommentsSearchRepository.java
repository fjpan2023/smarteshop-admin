package com.smarteshop.repository.search;

import com.smarteshop.domain.Comments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Comments entity.
 */
public interface CommentsSearchRepository extends ElasticsearchRepository<Comments, Long> {
}
