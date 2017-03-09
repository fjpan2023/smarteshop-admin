package com.smarteshop.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smarteshop.Money;

public class OrderItemDTO {
	private Long skuId;
	private Long categoryId;
	private Long productId;
	private Long orderItemId;
	private Integer quantity;
	private Money overrideSalePrice;
	private Money overrideRetailPrice;
	private Map<String,String> itemAttributes = new HashMap<String,String>();
	private List<OrderItemDTO> childOrderItems = new ArrayList<OrderItemDTO>();
	private Long parentOrderItemId;

	public OrderItemDTO() {}

	public OrderItemDTO(Long productId, Integer quantity) {
		setProductId(productId);
		setQuantity(quantity);
	}

	public OrderItemDTO(Long productId, Long skuId, Integer quantity) {
		setProductId(productId);
		setSkuId(skuId);
		setQuantity(quantity);
	}

	public OrderItemDTO(Long productId, Long skuId, Long categoryId, Integer quantity) {
		setProductId(productId);
		setSkuId(skuId);
		setCategoryId(categoryId);
		setQuantity(quantity);
	}

	public Long getSkuId() {
		return skuId;
	}

	public OrderItemDTO setSkuId(Long skuId) {
		this.skuId = skuId;
		return this;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public OrderItemDTO setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
		return this;
	}

	public Long getProductId() {
		return productId;
	}

	public OrderItemDTO setProductId(Long productId) {
		this.productId = productId;
		return this;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public OrderItemDTO setQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public Map<String, String> getItemAttributes() {
		return itemAttributes;
	}

	public OrderItemDTO setItemAttributes(Map<String, String> itemAttributes) {
		this.itemAttributes = itemAttributes;
		return this;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public OrderItemDTO setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
		return this;
	}

	public Money getOverrideSalePrice() {
		return overrideSalePrice;
	}

	public void setOverrideSalePrice(Money overrideSalePrice) {
		this.overrideSalePrice = overrideSalePrice;
	}

	public Money getOverrideRetailPrice() {
		return overrideRetailPrice;
	}

	public void setOverrideRetailPrice(Money overrideRetailPrice) {
		this.overrideRetailPrice = overrideRetailPrice;
	}

	public List<OrderItemDTO> getChildOrderItems() {
		return childOrderItems;
	}

	public void setChildOrderItems(List<OrderItemDTO> childOrderItems) {
		this.childOrderItems = childOrderItems;
	}

	public Long getParentOrderItemId() {
		return parentOrderItemId;
	}

	public void setParentOrderItemId(Long parentOrderItemId) {
		this.parentOrderItemId = parentOrderItemId;
	}


}
