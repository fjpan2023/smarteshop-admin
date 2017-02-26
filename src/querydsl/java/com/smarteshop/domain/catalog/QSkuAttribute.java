package com.smarteshop.domain.catalog;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkuAttribute is a Querydsl query type for SkuAttribute
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSkuAttribute extends EntityPathBase<SkuAttribute> {

    private static final long serialVersionUID = -284091761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSkuAttribute skuAttribute = new QSkuAttribute("skuAttribute");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QSku sku;

    public QSkuAttribute(String variable) {
        this(SkuAttribute.class, forVariable(variable), INITS);
    }

    public QSkuAttribute(Path<? extends SkuAttribute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSkuAttribute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSkuAttribute(PathMetadata metadata, PathInits inits) {
        this(SkuAttribute.class, metadata, inits);
    }

    public QSkuAttribute(Class<? extends SkuAttribute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sku = inits.isInitialized("sku") ? new QSku(forProperty("sku"), inits.get("sku")) : null;
    }

}

