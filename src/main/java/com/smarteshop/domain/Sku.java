package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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

    @Column(name = "size")
    private String size;

    @Column(name = "width", precision=10, scale=2)
    private BigDecimal width;

    @Column(name = "heigh", precision=10, scale=2)
    private BigDecimal heigh;

    @Column(name = "length", precision=10, scale=2)
    private BigDecimal length;

    @Column(name = "weight", precision=10, scale=2)
    private BigDecimal weight;

    @NotNull
    @Column(name = "standard_price", precision=10, scale=2, nullable = false)
    private BigDecimal standardPrice;

    @NotNull
    @Column(name = "sell_price", precision=10, scale=2, nullable = false)
    private BigDecimal sellPrice;

    @Column(name = "default_sku")
    private Boolean defaultSKU;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

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

    public String getSize() {
        return size;
    }

    public Sku size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public Sku width(BigDecimal width) {
        this.width = width;
        return this;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeigh() {
        return heigh;
    }

    public Sku heigh(BigDecimal heigh) {
        this.heigh = heigh;
        return this;
    }

    public void setHeigh(BigDecimal heigh) {
        this.heigh = heigh;
    }

    public BigDecimal getLength() {
        return length;
    }

    public Sku length(BigDecimal length) {
        this.length = length;
        return this;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public Sku weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public Sku standardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
        return this;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public Sku sellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Boolean isDefaultSKU() {
        return defaultSKU;
    }

    public Sku defaultSKU(Boolean defaultSKU) {
        this.defaultSKU = defaultSKU;
        return this;
    }

    public void setDefaultSKU(Boolean defaultSKU) {
        this.defaultSKU = defaultSKU;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Sku status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public Sku product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
