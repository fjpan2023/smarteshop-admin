package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSku is a Querydsl query type for Sku
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSku extends EntityPathBase<Sku> {

    private static final long serialVersionUID = -801186078L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSku sku = new QSku("sku");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final DateTimePath<java.time.ZonedDateTime> activeEndDate = createDateTime("activeEndDate", java.time.ZonedDateTime.class);

    public final DateTimePath<java.time.ZonedDateTime> activeStartDate = createDateTime("activeStartDate", java.time.ZonedDateTime.class);

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final QProduct defaultProduct;

    public final BooleanPath defaultSKU = createBoolean("defaultSKU");

    public final QDimension dimension;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final QProduct product;

    public final NumberPath<java.math.BigDecimal> retailPrice = createNumber("retailPrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> salePrice = createNumber("salePrice", java.math.BigDecimal.class);

    public final EnumPath<com.smarteshop.domain.enumeration.StatusEnum> status = createEnum("status", com.smarteshop.domain.enumeration.StatusEnum.class);

    public final QWeight weight;

    public QSku(String variable) {
        this(Sku.class, forVariable(variable), INITS);
    }

    public QSku(Path<? extends Sku> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSku(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSku(PathMetadata metadata, PathInits inits) {
        this(Sku.class, metadata, inits);
    }

    public QSku(Class<? extends Sku> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.defaultProduct = inits.isInitialized("defaultProduct") ? new QProduct(forProperty("defaultProduct"), inits.get("defaultProduct")) : null;
        this.dimension = inits.isInitialized("dimension") ? new QDimension(forProperty("dimension")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.weight = inits.isInitialized("weight") ? new QWeight(forProperty("weight")) : null;
    }

}

