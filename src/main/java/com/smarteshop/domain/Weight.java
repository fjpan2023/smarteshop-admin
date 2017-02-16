package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class Weight implements Serializable {

  @Column(name = "WEIGHT")
  protected BigDecimal weight;

  @Column(name = "WEIGHT_UNIT_OF_MEASURE")
  protected String weightUnitOfMeasure;

//  public WeightUnitOfMeasureType getWeightUnitOfMeasure() {
//      return WeightUnitOfMeasureType.getInstance(weightUnitOfMeasure);
//  }
//
//  public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure) {
//      if (weightUnitOfMeasure != null) {
//          this.weightUnitOfMeasure = weightUnitOfMeasure.getType();
//      }
//  }

  public BigDecimal getWeight() {
      return weight;
  }

  public void setWeight(BigDecimal weight) {
      this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (!getClass().isAssignableFrom(o.getClass())) return false;

      Weight weight1 = (Weight) o;

      if (weight != null ? !weight.equals(weight1.weight) : weight1.weight != null) return false;
      if (weightUnitOfMeasure != null ? !weightUnitOfMeasure.equals(weight1.weightUnitOfMeasure) : weight1
              .weightUnitOfMeasure != null)
          return false;

      return true;
  }

  @Override
  public int hashCode() {
      int result = weight != null ? weight.hashCode() : 0;
      result = 31 * result + (weightUnitOfMeasure != null ? weightUnitOfMeasure.hashCode() : 0);
      return result;
  }

}