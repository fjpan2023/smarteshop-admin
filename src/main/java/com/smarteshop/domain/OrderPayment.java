package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
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
 * A OrderPayment.
 */
@Entity
@Table(name = "order_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderpayment")
public class OrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = SalesOrder.class, optional = true)
    @JoinColumn(name = "ORDER_ID", nullable = true)
    protected SalesOrder order;

    @ManyToOne(targetEntity = Address.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ADDRESS_ID")
    protected Address billingAddress;

    @Column(name = "AMOUNT", precision=19, scale=5)
    protected BigDecimal amount;

    @Column(name = "REFERENCE_NUMBER")
    protected String referenceNumber;

    @Column(name = "PAYMENT_TYPE", nullable = false)
    protected String type;
    
    @Column(name = "GATEWAY_TYPE")
    protected String gatewayType;
    
    //TODO: add a filter for archived transactions
//    @OneToMany(mappedBy = "orderPayment", targetEntity = PaymentTransaction.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
//    protected List<PaymentTransaction> transactions = new ArrayList<PaymentTransaction>();

//    @Embedded
//    protected ArchiveStatus archiveStatus = new ArchiveStatus();
//    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public SalesOrder getOrder() {
		return order;
	}

	public void setOrder(SalesOrder order) {
		this.order = order;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGatewayType() {
		return gatewayType;
	}

	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}

	@Override
    public String toString() {
        return "OrderPayment{" +
            "id=" + id +
            '}';
    }
}
