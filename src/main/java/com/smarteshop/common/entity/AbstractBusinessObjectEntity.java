package com.smarteshop.common.entity;

import java.io.Serializable;
import java.text.Collator;
import java.time.ZonedDateTime;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBusinessObjectEntity<K extends Serializable & Comparable<K>, E extends AbstractBusinessObjectEntity<K, ?>>
implements Serializable, Comparable<E> {

    private static final long serialVersionUID = 1L;

    public static final Collator DEFAULT_STRING_COLLATOR = Collator.getInstance(Locale.FRENCH);

    static {
      DEFAULT_STRING_COLLATOR.setStrength(Collator.PRIMARY);
    }

    public abstract K getId();

    public abstract void setId(K id);


    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable=false)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Indique si l'objet a déjà été persisté ou non
     *
     * @return vrai si l'objet n'a pas encore été persisté
     */
    public boolean isNew() {
      return getId() == null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
      if (object == null) {
        return false;
      }
      if (object == this) {
        return true;
      }

      if (Hibernate.getClass(object) != Hibernate.getClass(this)) {
        return false;
      }

      AbstractBusinessObjectEntity<K, E> entity = (AbstractBusinessObjectEntity<K, E>) object;
      K id = getId();

      if (id == null) {
        return false;
      }

      return id.equals(entity.getId());
    }

    @Override
    public int hashCode() {
      int hash = 7;

      K id = getId();
      hash = 31 * hash + ((id == null) ? 0 : id.hashCode());

      return hash;
    }

    @Override
    public int compareTo(E o) {
      if (this == o) {
        return 0;
      }
      return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("entity.");
      builder.append(Hibernate.getClass(this).getSimpleName());
      builder.append("<");
      builder.append(getId());
      builder.append("-");
      builder.append(super.toString());
      builder.append(">");

      return builder.toString();
    }

}
