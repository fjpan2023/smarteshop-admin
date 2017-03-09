package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "salesorder")
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Embedded
    protected PreviewStatus previewable = new PreviewStatus();

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(targetEntity = Customer.class, optional=false)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    protected Customer customer;

    @Column(name = "ORDER_STATUS")
    protected String status;

    @Column(name = "TOTAL_TAX", precision=19, scale=5)
    protected BigDecimal totalTax;

    @Column(name = "TOTAL_SHIPPING", precision=19, scale=5)
    protected BigDecimal totalFulfillmentCharges;

    @Column(name = "ORDER_SUBTOTAL", precision=19, scale=5)
    protected BigDecimal subTotal;

    @Column(name = "ORDER_TOTAL", precision=19, scale=5)
    protected BigDecimal total;

    @Column(name = "SUBMIT_DATE")
    protected Date submitDate;

    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @Column(name = "EMAIL_ADDRESS")
    protected String emailAddress;

//    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class, cascade = {CascadeType.ALL})
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<OrderItem> orderItems = new ArrayList<OrderItem>();
//
//    @OneToMany(mappedBy = "order", targetEntity = FulfillmentGroup.class, cascade = {CascadeType.ALL})
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<FulfillmentGroup> fulfillmentGroups = new ArrayList<FulfillmentGroup>();
//
//    @OneToMany(mappedBy = "order", targetEntity = OrderAdjustment.class, cascade = { CascadeType.ALL },
//            orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<OrderAdjustment> orderAdjustments = new ArrayList<OrderAdjustment>();
//
//    @ManyToMany(fetch = FetchType.LAZY, targetEntity = OfferCode.class)
//    @JoinTable(name = "BLC_ORDER_OFFER_CODE_XREF", joinColumns = @JoinColumn(name = "ORDER_ID",
//            referencedColumnName = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "OFFER_CODE_ID",
//            referencedColumnName = "OFFER_CODE_ID"))
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<OfferCode> addedOfferCodes = new ArrayList<OfferCode>();
//
//    @OneToMany(mappedBy = "order", targetEntity = CandidateOrderOfferImpl.class, cascade = { CascadeType.ALL },
//            orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<CandidateOrderOffer> candidateOrderOffers = new ArrayList<CandidateOrderOffer>();
//
//    @OneToMany(mappedBy = "order", targetEntity = OrderPaymentImpl.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    protected List<OrderPayment> payments = new ArrayList<OrderPayment>();
//
//    @ManyToMany(targetEntity=OfferInfo.class)
//    @JoinTable(name = "BLC_ADDITIONAL_OFFER_INFO", joinColumns = @JoinColumn(name = "BLC_ORDER_ORDER_ID",
//            referencedColumnName = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "OFFER_INFO_ID",
//            referencedColumnName = "OFFER_INFO_ID"))
//    @MapKeyJoinColumn(name = "OFFER_ID")
//    @MapKeyClass(Offer.class)
//    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    @BatchSize(size = 50)
//    protected Map<Offer, OfferInfo> additionalOfferInformation = new HashMap<Offer, OfferInfo>();
//
//    @OneToMany(mappedBy = "order", targetEntity = OrderAttributeImpl.class, cascade = { CascadeType.ALL },
//            orphanRemoval = true)
//    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
//    @MapKey(name="name")
//    protected Map<String,OrderAttribute> orderAttributes = new HashMap<String,OrderAttribute>();
//    
    @ManyToOne(targetEntity = Currency.class)
    @JoinColumn(name = "CURRENCY_CODE")
    protected Currency currency;

    @ManyToOne(targetEntity = Locale.class)
    @JoinColumn(name = "LOCALE_CODE")
    protected Locale locale;

    @Column(name = "TAX_OVERRIDE")
    protected Boolean taxOverride;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SalesOrder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalesOrder salesOrder = (SalesOrder) o;
        if (salesOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salesOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
