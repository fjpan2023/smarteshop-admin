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

import com.smarteshop.common.entity.AbstractBusinessObjectEntity;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "currency")
public class Currency extends AbstractBusinessObjectEntity<Long, Currency> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "symbol_left")
    private String symbolLeft;

    @Column(name = "symbol_right")
    private String symbolRight;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Currency title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSymbolLeft() {
        return symbolLeft;
    }

    public Currency symbolLeft(String symbolLeft) {
        this.symbolLeft = symbolLeft;
        return this;
    }

    public void setSymbolLeft(String symbolLeft) {
        this.symbolLeft = symbolLeft;
    }

    public String getSymbolRight() {
        return symbolRight;
    }

    public Currency symbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
        return this;
    }

    public void setSymbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
    }


}
