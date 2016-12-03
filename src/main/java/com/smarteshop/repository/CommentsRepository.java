package com.smarteshop.repository;

import com.smarteshop.domain.Comments;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Comments entity.
 */
@SuppressWarnings("unused")
public interface CommentsRepository extends JpaRepository<Comments,Long> {

}
