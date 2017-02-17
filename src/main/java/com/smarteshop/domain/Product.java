package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.common.BusinessObjectInterface;
import com.smarteshop.domain.enumeration.ProductLabelEnum;
import com.smarteshop.domain.enumeration.StatusEnum;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product extends BusinessObjectEntity<Long, Product> implements BusinessObjectInterface, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(name="product_url")
  private String url;

  @Column(name="url_key")
  private String urlKey;

  @Column(name = "code")
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="MERCHANT_ID")
  private MerchantStore merchantStore;

  @OneToOne(cascade=CascadeType.ALL )
  private Sku defaultSKU;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusEnum status =StatusEnum.ACTIVITY;

  @Column(name = "standard_price", nullable=false,precision=10, scale=2)
  private BigDecimal standardPrice;

  @Column(name = "sell_price", nullable=false, precision=10, scale=2)
  private BigDecimal sellPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "label")
  private ProductLabelEnum label;

  @Column(name = "main_image_id")
  private Long mainImageId;

  @NotNull
  @Column(name = "Active_from_date",nullable = false)
  private ZonedDateTime activeStartDate;

  @NotNull
  @Column(name = "active_end_date", nullable = false)
  private ZonedDateTime activeEndDate;

  @OneToOne(targetEntity = Sku.class, mappedBy="defaultProduct", cascade = {CascadeType.ALL})
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
  @JoinColumn(name = "DEFAULT_SKU_ID")
  private Sku defaultSku;

  @OneToMany(fetch = FetchType.LAZY, targetEntity = Sku.class, mappedBy = "product", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @BatchSize(size = 50)
  protected List<Sku> additionalSkus = new ArrayList<Sku>();

  @Transient
  private Set<Sku> skus = new HashSet<>();

  @OneToMany(mappedBy = "product")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<RelatedProduct> relatedProducts = new HashSet<>();

  @OneToMany(mappedBy = "product")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductOption> productOptions = new HashSet<>();

  @ManyToOne
  private Brand brand;

  @ManyToOne
  private Category category;

  @Transient
  private Set<Attachment> images = new HashSet<Attachment>();

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

  public Product code(String code) {
    this.code = code;
    return this;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public Product status(StatusEnum status) {
    this.status = status;
    return this;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public BigDecimal getStandardPrice() {
    return standardPrice;
  }

  public Product standardPrice(BigDecimal standardPrice) {
    this.standardPrice = standardPrice;
    return this;
  }

  public void setStandardPrice(BigDecimal standardPrice) {
    this.standardPrice = standardPrice;
  }

  public ProductLabelEnum getLabel() {
    return label;
  }

  public Product label(ProductLabelEnum label) {
    this.label = label;
    return this;
  }

  public void setLabel(ProductLabelEnum label) {
    this.label = label;
  }

  public Long getMainImageId() {
    return mainImageId;
  }

  public Product mainImageId(Long mainImageId) {
    this.mainImageId = mainImageId;
    return this;
  }

  public void setMainImageId(Long mainImageId) {
    this.mainImageId = mainImageId;
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

  public Set<ProductOption> getProductOptions() {
    return productOptions;
  }

  public void setProductOptions(Set<ProductOption> productOptions) {
    this.productOptions = productOptions;
  }

  public Product addSkus(Sku sku) {
    skus.add(sku);
    sku.setProduct(this);
    return this;
  }

  public Product removeSkus(Sku sku) {
    skus.remove(sku);
    sku.setProduct(null);
    return this;
  }

  public void setSkuses(Set<Sku> skus) {
    this.skus = skus;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrlKey() {
    return urlKey;
  }

  public void setUrlKey(String urlKey) {
    this.urlKey = urlKey;
  }

  public MerchantStore getMerchantStore() {
    return merchantStore;
  }

  public void setMerchantStore(MerchantStore merchantStore) {
    this.merchantStore = merchantStore;
  }

  public Sku getDefaultSKU() {
    return defaultSKU;
  }

  public void setDefaultSKU(Sku defaultSKU) {
    this.defaultSKU = defaultSKU;
  }

  public Set<Sku> getSkus() {
    return skus;
  }

  public void setSkus(Set<Sku> skus) {
    this.skus = skus;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public Set<RelatedProduct> getRelatedProducts() {
    return relatedProducts;
  }

  public Product relatedProducts(Set<RelatedProduct> relatedProducts) {
    this.relatedProducts = relatedProducts;
    return this;
  }

  public Product addRelatedProducts(RelatedProduct relatedProduct) {
    relatedProducts.add(relatedProduct);
    relatedProduct.setProduct(this);
    return this;
  }

  public Product removeRelatedProducts(RelatedProduct relatedProduct) {
    relatedProducts.remove(relatedProduct);
    relatedProduct.setProduct(null);
    return this;
  }

  public void setRelatedProducts(Set<RelatedProduct> relatedProducts) {
    this.relatedProducts = relatedProducts;
  }

  public Brand getBrand() {
    return brand;
  }

  public Product brand(Brand brand) {
    this.brand = brand;
    return this;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public Category getCategory() {
    return category;
  }

  public Product category(Category category) {
    this.category = category;
    return this;
  }

  public void setCategory(Category category) {
    this.category = category;
  }



  public BigDecimal getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(BigDecimal sellPrice) {
    this.sellPrice = sellPrice;
  }
  public Collection<Attachment> getImages() {
    return images;
  }
  public void setImages(Set<Attachment> images) {
    this.images = images;
  }

  public Sku getDefaultSku() {
    return defaultSku;
  }

  public void setDefaultSku(Sku defaultSku) {
    this.defaultSku = defaultSku;
  }

  public List<Sku> getAdditionalSkus() {
    return additionalSkus;
  }

  public void setAdditionalSkus(List<Sku> additionalSkus) {
    this.additionalSkus = additionalSkus;
  }

}
