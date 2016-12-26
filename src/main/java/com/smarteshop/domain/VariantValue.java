package com.smarteshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.domain.common.BusinessObjectInterface;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VariantValue.
 */
@Entity
@Table(name = "variant_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "variantvalue")
public class VariantValue extends AbstractAuditingEntity implements BusinessObjectInterface, Serializable  {



	/**
	 * 
	 */
	private static final long serialVersionUID = -8155280208012644059L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "display_order")
	private Integer displayOrder;

	@ManyToOne
	private Variant variant;

	@Transient
	private String info;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public VariantValue value(String value) {
		this.value = value;
		return this;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public VariantValue code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public VariantValue displayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
		return this;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Variant getVariant() {
		return variant;
	}

	public VariantValue variant(Variant variant) {
		this.variant = variant;
		return this;
	}

	public void setVariant(Variant variant) {
		this.variant = variant;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		VariantValue variantValue = (VariantValue) o;
		if (variantValue.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, variantValue.id);
	}

	public String getInfo(){
		return  this.variant.getId().toString() + '@'+ getId().toString() ;
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "VariantValue{" +
				"id=" + id +
				", value='" + value + "'" +
				", code='" + code + "'" +
				", displayOrder='" + displayOrder + "'" +
				'}';
	}
}
