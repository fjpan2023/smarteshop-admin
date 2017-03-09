package com.smarteshop.domain;

import java.io.Serializable;
import java.util.Objects;

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
 * A Locale.
 */
@Entity
@Table(name = "locale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "locale")
public class Locale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected String localeCode;

    @Column (name = "FRIENDLY_NAME")
    protected String friendlyName;

    @Column (name = "DEFAULT_FLAG")
    protected Boolean defaultFlag = false;

    @ManyToOne(targetEntity = Currency.class)
    @JoinColumn(name = "CURRENCY_CODE")
    protected Currency defaultCurrency;

    @Column (name = "USE_IN_SEARCH_INDEX")
    protected Boolean useInSearchIndex = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public Boolean getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(Boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public Currency getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(Currency defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Boolean getUseInSearchIndex() {
		return useInSearchIndex;
	}

	public void setUseInSearchIndex(Boolean useInSearchIndex) {
		this.useInSearchIndex = useInSearchIndex;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Locale locale = (Locale) o;
        if (locale.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, locale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Locale{" +
            "id=" + id +
            '}';
    }
}
