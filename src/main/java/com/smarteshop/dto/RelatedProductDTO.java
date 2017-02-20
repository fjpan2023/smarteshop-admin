package com.smarteshop.dto;

import java.util.Set;

public class RelatedProductDTO {
  private Set<Long> productIds;

  public Set<Long> getProductIds() {
    return productIds;
  }

  public void setProductIds(Set<Long> productIds) {
    this.productIds = productIds;
  }


}
