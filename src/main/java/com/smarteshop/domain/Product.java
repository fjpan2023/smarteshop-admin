package com.smarteshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.smarteshop.domain.enumeration.StatusEnum;

import com.smarteshop.domain.enumeration.ProductLabelEnum;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "standard_price", precision=10, scale=2)
    private BigDecimal standardPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "label")
    private ProductLabelEnum label;

    @Column(name = "main_image_id")
    private Long mainImageId;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private ZonedDateTime fromDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sku> skuses = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RelatedProduct> relatedProducts = new HashSet<>();

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

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

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public Product fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Product endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<Sku> getSkuses() {
        return skuses;
    }

    public Product skuses(Set<Sku> skus) {
        this.skuses = skus;
        return this;
    }

    public Product addSkus(Sku sku) {
        skuses.add(sku);
        sku.setProduct(this);
        return this;
    }

    public Product removeSkus(Sku sku) {
        skuses.remove(sku);
        sku.setProduct(null);
        return this;
    }

    public void setSkuses(Set<Sku> skus) {
        this.skuses = skus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if(product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", standardPrice='" + standardPrice + "'" +
            ", label='" + label + "'" +
            ", mainImageId='" + mainImageId + "'" +
            ", fromDate='" + fromDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
