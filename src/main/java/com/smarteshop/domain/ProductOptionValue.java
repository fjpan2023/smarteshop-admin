package com.smarteshop.domain;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.LiteBusinessObject;

/**
 * A ProductOptionValue.
 */
@Entity
@Table(name = "product_option_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoptionvalue")
public class ProductOptionValue implements LiteBusinessObject {

  private static final long serialVersionUID = 4099535236481378120L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY)
  private ProductOption productOption;

  @NotNull
  @Column(name = "attribute_value", nullable=false)
  private String attributeValue;

  @NotNull
  @Column(name = "display_order", nullable=false)
  private Long displayOrder;

  @ManyToMany(targetEntity=Sku.class,fetch=FetchType.LAZY)
  private Set<Sku> skus;

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

  public Long getDisplayOrder() {
    return displayOrder;
  }

  public ProductOptionValue displayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
    return this;
  }

  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  public ProductOption getProductOption() {
    return productOption;
  }

  public void setProductOption(ProductOption productOption) {
    this.productOption = productOption;
  }

  public Set<Sku> getSkus() {
    return skus;
  }

  public void setSkus(Set<Sku> skus) {
    this.skus = skus;
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
