package com.smarteshop.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.common.BusinessObjectInterface;

/**
 * A ProductOption.
 */
@Entity
@Table(name = "product_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productoption")
public class ProductOption extends BusinessObjectEntity<Long, ProductOption> implements BusinessObjectInterface, Serializable {

  private static final long serialVersionUID = -7745644142066416162L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Column(name = "type", nullable=false)
  private String type;

  @NotNull
  @Column(name = "name", nullable=false)
  private String name;

  @Column(name = "attribute_name")
  private String attributeName;

  @Column(name = "label")
  private String label;

  @Column(name = "display_order")
  private int displayOrder;

  @OneToMany(mappedBy = "productOption",fetch=FetchType.EAGER)
  @OrderBy(value = "displayOrder")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductOptionValue> productOptionValues = new HashSet<>();

  @ManyToMany(targetEntity=Product.class)
  private Set<Product> products;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public ProductOption type(String type) {
    this.type = type;
    return this;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public ProductOption name(String name) {
    this.name = name;
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public ProductOption attributeName(String attributeName) {
    this.attributeName = attributeName;
    return this;
  }

  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }

  public String getLabel() {
    return label;
  }

  public ProductOption label(String label) {
    this.label = label;
    return this;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Set<ProductOptionValue> getProductOptionValues() {
    return productOptionValues;
  }

  public void setProductOptionValues(Set<ProductOptionValue> productOptionValues) {
    this.productOptionValues = productOptionValues;
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int displayOrder) {
    this.displayOrder = displayOrder;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }

  public ProductOption addProductOptionValue(ProductOptionValue productOptionValue) {
    productOptionValues.add(productOptionValue);
    return this;
  }

  public ProductOption removeProductOptionValue(ProductOptionValue productOptionValue) {
    productOptionValues.remove(productOptionValue);
    return this;
  }

}
