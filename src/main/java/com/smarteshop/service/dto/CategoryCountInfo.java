package com.smarteshop.service.dto;

import com.smarteshop.domain.catalog.Category;

public class CategoryCountInfo {
	private String name;
	private Category category;
	private Long count;
	CategoryCountInfo(String name,Category category,Long count){
		this.name = name;
		this.category = category;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
