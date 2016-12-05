package com.smarteshop.repository;

import com.smarteshop.domain.Attachment;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
@SuppressWarnings("unused")
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {


	@Query("SELECT u.id from Attachment u where u.entityName = :entityName and u.entityId= :entityId")
	public List<Long> getAttachmentIdsByEntityInfo(@Param("entityName") String entityName, @Param("entityId") Long id);
	
	@Query("SELECT u from Attachment u where u.entityName = :entityName and u.entityId= :entityId")
	public List<Attachment> getAttachmentsByEntityInfo(@Param("entityName") String entityName, @Param("entityId") Long id);
}
