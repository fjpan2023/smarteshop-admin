package com.smarteshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PreviewStatus implements Serializable {
	@Column(name = "IS_PREVIEW")
    protected Boolean isPreview;

    public Boolean getPreview() {
        return isPreview;
    }

    public void setPreview(Boolean preview) {
        isPreview = preview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        PreviewStatus that = (PreviewStatus) o;

        if (isPreview != null ? !isPreview.equals(that.isPreview) : that.isPreview != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isPreview != null ? isPreview.hashCode() : 0;
    }
}
