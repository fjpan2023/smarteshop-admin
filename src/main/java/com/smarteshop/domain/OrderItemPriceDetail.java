package com.smarteshop.domain;

import java.io.Serializable;

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
 * A OrderItemPrice.
 */
@Entity
@Table(name = "order_item_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderitemprice")
public class OrderItemPriceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(targetEntity = OrderItem.class)
    @JoinColumn(name = "ORDER_ITEM_ID")
    protected OrderItem orderItem;

//    @OneToMany(mappedBy = "orderItemPriceDetail", targetEntity = OrderItemPriceDetailAdjustment.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blOrderElements")
//    protected List<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments = new ArrayList<OrderItemPriceDetailAdjustment>();

    @Column(name = "QUANTITY", nullable=false)
    protected int quantity;

    @Column(name = "USE_SALE_PRICE")
    protected Boolean useSalePrice = true;

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

	public Boolean getUseSalePrice() {
		return useSalePrice;
	}

	public void setUseSalePrice(Boolean useSalePrice) {
		this.useSalePrice = useSalePrice;
	}

	@Override
    public String toString() {
        return "OrderItemPrice{" +
            "id=" + id +
            '}';
    }
}
