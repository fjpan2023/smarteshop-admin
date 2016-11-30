package com.smarteshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.smarteshop.domain.enumeration.TemplateTypeEnum;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TemplateTypeEnum type;

    @Column(name = "super_id")
    private Long superId;

    @Column(name = "template_key")
    private String templateKey;

    @Column(name = "content")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TemplateTypeEnum getType() {
        return type;
    }

    public Template type(TemplateTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(TemplateTypeEnum type) {
        this.type = type;
    }

    public Long getSuperId() {
        return superId;
    }

    public Template superId(Long superId) {
        this.superId = superId;
        return this;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public Template templateKey(String templateKey) {
        this.templateKey = templateKey;
        return this;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getContent() {
        return content;
    }

    public Template content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        if(template.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, template.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", superId='" + superId + "'" +
            ", templateKey='" + templateKey + "'" +
            ", content='" + content + "'" +
            '}';
    }
}