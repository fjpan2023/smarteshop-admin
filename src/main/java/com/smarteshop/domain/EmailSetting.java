package com.smarteshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.AbstractBusinessObjectEntity;
import com.smarteshop.domain.enumeration.SMTPSecurityEnum;

/**
 * A EmailSetting.
 */
@Entity
@Table(name = "email_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "emailsetting")
public class EmailSetting extends AbstractBusinessObjectEntity<Long, EmailSetting> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "host", nullable = false)
    private String host;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "smtp_security", nullable = false)
    private SMTPSecurityEnum smtpSecurity;

    @NotNull
    @Column(name = "from_address", nullable = false)
    private String fromAddress;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public EmailSetting host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public EmailSetting port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public SMTPSecurityEnum getSmtpSecurity() {
        return smtpSecurity;
    }

    public EmailSetting smtpSecurity(SMTPSecurityEnum smtpSecurity) {
        this.smtpSecurity = smtpSecurity;
        return this;
    }

    public void setSmtpSecurity(SMTPSecurityEnum smtpSecurity) {
        this.smtpSecurity = smtpSecurity;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public EmailSetting fromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getUserName() {
        return userName;
    }

    public EmailSetting userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public EmailSetting password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
