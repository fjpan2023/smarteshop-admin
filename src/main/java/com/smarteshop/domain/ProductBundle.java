package com.smarteshop.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.catalog.Product;
import com.smarteshop.domain.common.BusinessObjectInterface;

/**
 * A ProductBundle.
 */
@Entity
@Table(name = "product_bundle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productbundle")
public class ProductBundle extends BusinessObjectEntity<Long, Product> implements BusinessObjectInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price_model")
    private String priceModel;

    @Column(name = "auto_bundle")
    private Boolean autoBundle;

    @Column(name = "bundle_promotable")
    private Boolean bundlePromotable;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPriceModel() {
        return priceModel;
    }

    public ProductBundle priceModel(String priceModel) {
        this.priceModel = priceModel;
        return this;
    }

    public void setPriceModel(String priceModel) {
        this.priceModel = priceModel;
    }

    public Boolean isAutoBundle() {
        return autoBundle;
    }

    public ProductBundle autoBundle(Boolean autoBundle) {
        this.autoBundle = autoBundle;
        return this;
    }

    public void setAutoBundle(Boolean autoBundle) {
        this.autoBundle = autoBundle;
    }

    public Boolean isBundlePromotable() {
        return bundlePromotable;
    }

    public ProductBundle bundlePromotable(Boolean bundlePromotable) {
        this.bundlePromotable = bundlePromotable;
        return this;
    }

    public void setBundlePromotable(Boolean bundlePromotable) {
        this.bundlePromotable = bundlePromotable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductBundle productBundle = (ProductBundle) o;
        if (productBundle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productBundle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
            "id=" + id +
            ", priceModel='" + priceModel + "'" +
            ", autoBundle='" + autoBundle + "'" +
            ", bundlePromotable='" + bundlePromotable + "'" +
            '}';
    }
}
