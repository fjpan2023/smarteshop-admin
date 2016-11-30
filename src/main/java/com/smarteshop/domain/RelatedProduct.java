package com.smarteshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RelatedProduct.
 */
@Entity
@Table(name = "related_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "relatedproduct")
public class RelatedProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "related_product_id", nullable = false)
    private Long relatedProductId;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelatedProduct relatedProduct = (RelatedProduct) o;
        if(relatedProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, relatedProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RelatedProduct{" +
            "id=" + id +
            ", relatedProductId='" + relatedProductId + "'" +
            '}';
    }
}
