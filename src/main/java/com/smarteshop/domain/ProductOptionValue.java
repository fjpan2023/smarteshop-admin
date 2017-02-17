package com.smarteshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ProductOptionValue.
 */
@Entity
@Table(name = "product_option_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptionvalue")
public class ProductOptionValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    private ProductOption productOption;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "display_order")
    private Integer displayOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public ProductOptionValue attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public ProductOptionValue displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public ProductOption getProductOption() {
      return productOption;
    }

    public void setProductOption(ProductOption productOption) {
      this.productOption = productOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductOptionValue productOptionValue = (ProductOptionValue) o;
        if (productOptionValue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productOptionValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductOptionValue{" +
            "id=" + id +
            ", attributeValue='" + attributeValue + "'" +
            ", displayOrder='" + displayOrder + "'" +
            '}';
    }
}
