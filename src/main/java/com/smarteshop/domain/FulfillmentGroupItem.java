package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A FulfillmentGroupItem.
 */
@Entity
@Table(name = "fulfillment_group_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fulfillmentgroupitem")
public class FulfillmentGroupItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @ManyToOne(targetEntity = FulfillmentGroup.class, optional=false)
//    @JoinColumn(name = "FULFILLMENT_GROUP_ID")
//    protected FulfillmentGroup fulfillmentGroup;

    //this needs to stay OrderItem in order to provide backwards compatibility for those implementations that place a BundleOrderItem
    @ManyToOne(targetEntity = OrderItem.class, optional=false)
    @JoinColumn(name = "ORDER_ITEM_ID")
    protected OrderItem orderItem;

    @Column(name = "QUANTITY", nullable=false)
    protected int quantity;

    @Column(name = "STATUS")
    private String status;
    
//    @OneToMany(fetch = FetchType.LAZY, targetEntity = TaxDetailImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
//    @JoinTable(name = "BLC_FG_ITEM_TAX_XREF", joinColumns = @JoinColumn(name = "FULFILLMENT_GROUP_ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "TAX_DETAIL_ID"))
//    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<TaxDetail> taxes = new ArrayList<TaxDetail>();
//    
    @Column(name = "TOTAL_ITEM_TAX", precision=19, scale=5)
    protected BigDecimal totalTax;

    @Column(name = "TOTAL_ITEM_AMOUNT", precision = 19, scale = 5)
    protected BigDecimal totalItemAmount;

    @Column(name = "TOTAL_ITEM_TAXABLE_AMOUNT", precision = 19, scale = 5)
    protected BigDecimal totalItemTaxableAmount;

    @Column(name = "PRORATED_ORDER_ADJ")
    protected BigDecimal proratedOrderAdjustment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public BigDecimal getTotalItemAmount() {
		return totalItemAmount;
	}

	public void setTotalItemAmount(BigDecimal totalItemAmount) {
		this.totalItemAmount = totalItemAmount;
	}

	public BigDecimal getTotalItemTaxableAmount() {
		return totalItemTaxableAmount;
	}

	public void setTotalItemTaxableAmount(BigDecimal totalItemTaxableAmount) {
		this.totalItemTaxableAmount = totalItemTaxableAmount;
	}

	public BigDecimal getProratedOrderAdjustment() {
		return proratedOrderAdjustment;
	}

	public void setProratedOrderAdjustment(BigDecimal proratedOrderAdjustment) {
		this.proratedOrderAdjustment = proratedOrderAdjustment;
	}

	@Override
    public String toString() {
        return "FulfillmentGroupItem{" +
            "id=" + id +
            '}';
    }
}
