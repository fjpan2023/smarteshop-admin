package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.enumeration.StatusEnum;

/**
 * A Sku.
 */
@Entity
@Table(name = "sku")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sku")
public class Sku extends BusinessObjectEntity<Long, Sku>  implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "RETAIL_PRICE", precision = 19, scale = 5,nullable=false)
  protected BigDecimal retailPrice=BigDecimal.ZERO;

  @NotNull
  @Column(name = "SALE_PRICE", precision = 19, scale = 5,nullable=false)
  private BigDecimal salePrice=BigDecimal.ZERO;

  @Embedded
  protected Dimension dimension = new Dimension();

  @Embedded
  protected Weight weight = new Weight();


  @Column(name = "default_sku")
  private Boolean defaultSKU;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusEnum status = StatusEnum.ACTIVITY;

  @Column(name = "ACTIVE_START_DATE")
  protected ZonedDateTime activeStartDate;

  @Column(name = "ACTIVE_END_DATE")
  protected ZonedDateTime activeEndDate;

  @OneToOne(optional = true, targetEntity = Product.class, cascade = {CascadeType.ALL})
  @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
  @JoinColumn(name = "DEFAULT_PRODUCT_ID")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  protected Product defaultProduct;

  /**
   * This relationship will be non-null if and only if this Sku is contained in the list of
   * additional Skus for a Product (for Skus based on ProductOptions)
   */
  @ManyToOne(optional = true, targetEntity = Product.class, cascade = {CascadeType.ALL})
  @JoinColumn(name = "ADDL_PRODUCT_ID")
  protected Product product;

  @ManyToMany(fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JoinTable(name = "sku_product_option_value_xref",
  joinColumns = @JoinColumn(name="sku_id", referencedColumnName="ID"),
  inverseJoinColumns = @JoinColumn(name="product_option_value_id", referencedColumnName="ID"))
  private Set<ProductOption> productOptions = new HashSet<>();@Transient
  protected Set<ProductOptionValue> productOptionValues = new HashSet<ProductOptionValue>();

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public Sku code(String code) {
    this.code = code;
    return this;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public Sku name(String name) {
    this.name = name;
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  public Weight getWeight() {
    return weight;
  }

  public void setWeight(Weight weight) {
    this.weight = weight;
  }

  public Boolean getDefaultSKU() {
    return defaultSKU;
  }

  public void setDefaultSKU(Boolean defaultSKU) {
    this.defaultSKU = defaultSKU;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public Sku  status(StatusEnum status) {
    this.status = status;
    return this;
  }

  public Product getDefaultProduct() {
    return defaultProduct;
  }

  public void setDefaultProduct(Product defaultProduct) {
    this.defaultProduct = defaultProduct;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }



  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ZonedDateTime getActiveStartDate() {
    return activeStartDate;
  }

  public void setActiveStartDate(ZonedDateTime activeStartDate) {
    this.activeStartDate = activeStartDate;
  }

  public ZonedDateTime getActiveEndDate() {
    return activeEndDate;
  }

  public void setActiveEndDate(ZonedDateTime activeEndDate) {
    this.activeEndDate = activeEndDate;
  }

  public Set<ProductOptionValue> getProductOptionValues() {
    return productOptionValues;
  }

  public void setProductOptionValues(Set<ProductOptionValue> productOptionValues) {
    this.productOptionValues = productOptionValues;
  }

  public Sku removeProductOptionValue(ProductOptionValue productOptionValue) {
    this.productOptionValues.remove( productOptionValue);
    return this;
  }

  public Sku addProductOptionValues(ProductOptionValue productOptionValue) {
    this.productOptionValues.remove( productOptionValue);
    return this;
  }

  public boolean isOnSale() {
    BigDecimal retailPrice = getRetailPrice();
    BigDecimal salePrice = getSalePrice();
    // return (salePrice != null && !salePrice.ZERO && salePrice.lessThan(retailPrice));
    return true;
  }

  protected boolean hasDefaultSku() {
    return (product != null && product.getDefaultSku() != null && !getId().equals(product.getDefaultSku().getId()));
  }

  protected Sku lookupDefaultSku() {
    if (product != null && product.getDefaultSku() != null) {
      return product.getDefaultSku();
    } else {
      return null;
    }
  }

}
