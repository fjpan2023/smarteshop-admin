package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.domain.catalog.Category;

/**
 * A ItemOrder.
 */
@Entity
@Table(name = "item_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "itemorder")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    protected Category category;

    @ManyToOne(targetEntity = SalesOrder.class)
    @JoinColumn(name = "ORDER_ID")
    protected SalesOrder order;

    @Column(name = "PRICE", precision = 19, scale = 5)
    protected BigDecimal price;

    @Column(name = "QUANTITY", nullable = false)
    protected int quantity;

    @Column(name = "RETAIL_PRICE", precision=19, scale=5)
    protected BigDecimal retailPrice;

    @Column(name = "SALE_PRICE", precision=19, scale=5)
    protected BigDecimal salePrice;

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(targetEntity = PersonalMessage.class, cascade = { CascadeType.ALL })
    @JoinColumn(name = "PERSONAL_MESSAGE_ID")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="OrderElements")
    protected PersonalMessage personalMessage;

//   
//    @OneToMany(mappedBy = "orderItem", targetEntity = OrderItemAdjustmentImpl.class, cascade = { CascadeType.ALL },
//            orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blOrderElements")
//    protected List<OrderItemAdjustment> orderItemAdjustments = new ArrayList<OrderItemAdjustment>();
//
//    
//    @OneToMany(mappedBy = "orderItem", targetEntity = OrderItemQualifier.class, cascade = { CascadeType.ALL },
//            orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blOrderElements")
//    protected List<OrderItemQualifier> orderItemQualifiers = new ArrayList<OrderItemQualifier>();
//
//    @OneToMany(mappedBy = "orderItem", targetEntity = CandidateItemOffer.class, cascade = { CascadeType.ALL },
//            orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blOrderElements")
//    protected List<CandidateItemOffer> candidateItemOffers = new ArrayList<CandidateItemOffer>();

    @OneToMany(mappedBy = "orderItem", targetEntity = OrderItemPriceDetail.class, cascade = { CascadeType.ALL },
            orphanRemoval = true)
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
    protected List<OrderItemPriceDetail> orderItemPriceDetails = new ArrayList<OrderItemPriceDetail>();
    
    @Column(name = "ORDER_ITEM_TYPE")
    protected String orderItemType;

    @Column(name = "ITEM_TAXABLE_FLAG")
    protected Boolean itemTaxable;

    @Column(name = "RETAIL_PRICE_OVERRIDE")
    protected Boolean retailPriceOverride;

    @Column(name = "SALE_PRICE_OVERRIDE")
    protected Boolean salePriceOverride;

    @Column(name = "DISCOUNTS_ALLOWED")
    protected Boolean discountsAllowed;

//    @OneToMany(mappedBy = "orderItem", targetEntity = OrderItemAttribute.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
//    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    @MapKey(name="name")
//    protected Map<String, OrderItemAttribute> orderItemAttributeMap = new HashMap<String, OrderItemAttribute>();

    /**
     * @deprecated use {@link FulfillmentGroupItem#getTaxes()} or {@link FulfillmentGroupItem#getTotalTax()} instead
     */
    @Column(name = "TOTAL_TAX")
    @Deprecated
    protected BigDecimal totalTax;
    
    @OneToMany(mappedBy = "parentOrderItem", targetEntity = OrderItem.class)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blOrderElements")
    protected List<OrderItem> childOrderItems = new ArrayList<OrderItem>();

    @ManyToOne(targetEntity = OrderItem.class)
    @JoinColumn(name = "PARENT_ORDER_ITEM_ID")
    protected OrderItem parentOrderItem;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OrderItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    @Override
    public String toString() {
        return "ItemOrder{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
