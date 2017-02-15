package com.smarteshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.AbstractBusinessObjectEntity;

/**
 * A RelatedProduct.
 */
@Entity
@Table(name = "related_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "relatedproduct")
public class RelatedProduct extends AbstractBusinessObjectEntity<Long, RelatedProduct> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "related_product_id", nullable = false)
    private Long relatedProductId;

    @ManyToOne
    private Product product;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelatedProductId() {
        return relatedProductId;
    }

    public RelatedProduct relatedProductId(Long relatedProductId) {
        this.relatedProductId = relatedProductId;
        return this;
    }

    public void setRelatedProductId(Long relatedProductId) {
        this.relatedProductId = relatedProductId;
    }

    public Product getProduct() {
        return product;
    }

    public RelatedProduct product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
