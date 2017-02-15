package com.smarteshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "address")
public class Address extends BusinessObjectEntity<Long, Address>  implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 5781169088997567683L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "entity_id", nullable=false, updatable=false)
  private Long entityId;

  @Column(name = "entity_name", nullable=false, updatable=false)
  private String entityName;

  @Column(name = "country")
  private String country;

  @Column(name = "street_1")
  private String street1;

  @Column(name = "state")
  private String state;

  @Column(name = "state_code")
  private String stateCode;

  @Column(name = "city_name")
  private String cityName;

  @Column(name = "street_2")
  private String street2;

  @Column(name = "zip_code")
  private String zipCode;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public Address country(String country) {
    this.country = country;
    return this;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getStreet1() {
    return street1;
  }

  public Address street1(String street1) {
    this.street1 = street1;
    return this;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getState() {
    return state;
  }

  public Address state(String state) {
    this.state = state;
    return this;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getStateCode() {
    return stateCode;
  }

  public Address stateCode(String stateCode) {
    this.stateCode = stateCode;
    return this;
  }

  public void setStateCode(String stateCode) {
    this.stateCode = stateCode;
  }

  public String getCityName() {
    return cityName;
  }

  public Address cityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getStreet2() {
    return street2;
  }

  public Address street2(String street2) {
    this.street2 = street2;
    return this;
  }

  public void setStreet2(String street2) {
    this.street2 = street2;
  }

  public String getZipCode() {
    return zipCode;
  }

  public Address zipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }


  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }


  @Override
  public String toString() {
    return "Address [id=" + id + ", entityId=" + entityId + ", entityName=" + entityName
        + ", country=" + country + ", street1=" + street1 + ", state=" + state + ", stateCode="
        + stateCode + ", cityName=" + cityName + ", street2=" + street2 + ", zipCode=" + zipCode
        + "]";
  }




}
