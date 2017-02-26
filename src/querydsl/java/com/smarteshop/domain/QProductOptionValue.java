package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.smarteshop.domain.catalog.ProductOptionValue;
import com.smarteshop.domain.catalog.Sku;


/**
 * QProductOptionValue is a Querydsl query type for ProductOptionValue
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductOptionValue extends EntityPathBase<ProductOptionValue> {

    private static final long serialVersionUID = -2055908056L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOptionValue productOptionValue = new QProductOptionValue("productOptionValue");

    public final StringPath attributeValue = createString("attributeValue");

    public final NumberPath<Long> displayOrder = createNumber("displayOrder", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProductOption productOption;

    public final SetPath<Sku, QSku> skus = this.<Sku, QSku>createSet("skus", Sku.class, QSku.class, PathInits.DIRECT2);

    public QProductOptionValue(String variable) {
        this(ProductOptionValue.class, forVariable(variable), INITS);
    }

    public QProductOptionValue(Path<? extends ProductOptionValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOptionValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOptionValue(PathMetadata metadata, PathInits inits) {
        this(ProductOptionValue.class, metadata, inits);
    }

    public QProductOptionValue(Class<? extends ProductOptionValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productOption = inits.isInitialized("productOption") ? new QProductOption(forProperty("productOption")) : null;
    }

}

