package com.smarteshop.domain.catalog;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkuProductOptionValueXref is a Querydsl query type for SkuProductOptionValueXref
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSkuProductOptionValueXref extends EntityPathBase<SkuProductOptionValueXref> {

    private static final long serialVersionUID = -224766155L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSkuProductOptionValueXref skuProductOptionValueXref = new QSkuProductOptionValueXref("skuProductOptionValueXref");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProductOptionValue productOptionValue;

    public final QSku sku;

    public QSkuProductOptionValueXref(String variable) {
        this(SkuProductOptionValueXref.class, forVariable(variable), INITS);
    }

    public QSkuProductOptionValueXref(Path<? extends SkuProductOptionValueXref> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSkuProductOptionValueXref(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSkuProductOptionValueXref(PathMetadata metadata, PathInits inits) {
        this(SkuProductOptionValueXref.class, metadata, inits);
    }

    public QSkuProductOptionValueXref(Class<? extends SkuProductOptionValueXref> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productOptionValue = inits.isInitialized("productOptionValue") ? new QProductOptionValue(forProperty("productOptionValue"), inits.get("productOptionValue")) : null;
        this.sku = inits.isInitialized("sku") ? new QSku(forProperty("sku"), inits.get("sku")) : null;
    }

}

