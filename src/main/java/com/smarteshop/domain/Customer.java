package com.smarteshop.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.common.model.Billing;
import com.smarteshop.common.model.Delivery;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer  extends BusinessObjectEntity<Long, Customer> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "fax")
  private String fax;

  @Column(name = "remark")
  private String remark;

  @Embedded
  private Delivery delivery;

  @Valid
  @Embedded
  private Billing billing ;
  @Transient
  private List<Address> addresses;

  @Transient
  private String displayName;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public Customer firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Customer lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public Customer email(String email) {
    this.email = email;
    return this;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public Customer phone(String phone) {
    this.phone = phone;
    return this;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFax() {
    return fax;
  }

  public Customer fax(String fax) {
    this.fax = fax;
    return this;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getRemark() {
    return remark;
  }

  public Customer remark(String remark) {
    this.remark = remark;
    return this;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Delivery getDelivery() {
    return delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }

  public Billing getBilling() {
    return billing;
  }

  public void setBilling(Billing billing) {
    this.billing = billing;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public String getDisplayName() {
    return this.lastName+", "+this.firstName;
  }

}
