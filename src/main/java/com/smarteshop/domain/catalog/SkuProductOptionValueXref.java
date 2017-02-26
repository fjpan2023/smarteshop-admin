package com.smarteshop.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SkuProductOptionValueXref.
 */
@Entity
@Table(name = "sku_product_option_value_xref")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "skuproductoptionvaluexref")
public class SkuProductOptionValueXref implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SKU_ID")
    protected Sku sku;

    @ManyToOne(optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_OPTION_VALUE_ID")
    protected ProductOptionValue productOptionValue;
    
    public SkuProductOptionValueXref(Sku sku, ProductOptionValue val) {
        this.sku = sku;
        this.productOptionValue = val;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public ProductOptionValue getProductOptionValue() {
        return productOptionValue;
    }

    public SkuProductOptionValueXref productOptionValue(ProductOptionValue productOptionValue) {
        this.productOptionValue = productOptionValue;
        return this;
    }

    public void setProductOptionValue(ProductOptionValue productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

    
    
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkuProductOptionValueXref skuProductOptionValueXref = (SkuProductOptionValueXref) o;
        if (skuProductOptionValueXref.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, skuProductOptionValueXref.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SkuProductOptionValueXref{" +
            "id=" + id +
            '}';
    }
}
