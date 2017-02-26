package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.smarteshop.domain.catalog.ProductAttribute;


/**
 * QProductAttribute is a Querydsl query type for ProductAttribute
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductAttribute extends EntityPathBase<ProductAttribute> {

    private static final long serialVersionUID = -1316044600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductAttribute productAttribute = new QProductAttribute("productAttribute");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QProduct product;

    public QProductAttribute(String variable) {
        this(ProductAttribute.class, forVariable(variable), INITS);
    }

    public QProductAttribute(Path<? extends ProductAttribute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductAttribute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductAttribute(PathMetadata metadata, PathInits inits) {
        this(ProductAttribute.class, metadata, inits);
    }

    public QProductAttribute(Class<? extends ProductAttribute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

