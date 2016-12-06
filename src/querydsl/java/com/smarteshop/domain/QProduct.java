package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 858424372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final QAbstractAuditingEntity _super = new QAbstractAuditingEntity(this);

    public final QBrand brand;

    public final QCategory category;

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.ZonedDateTime> endDate = createDateTime("endDate", java.time.ZonedDateTime.class);

    public final DateTimePath<java.time.ZonedDateTime> fromDate = createDateTime("fromDate", java.time.ZonedDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.smarteshop.domain.enumeration.ProductLabelEnum> label = createEnum("label", com.smarteshop.domain.enumeration.ProductLabelEnum.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> mainImageId = createNumber("mainImageId", Long.class);

    public final StringPath name = createString("name");

    public final SetPath<RelatedProduct, QRelatedProduct> relatedProducts = this.<RelatedProduct, QRelatedProduct>createSet("relatedProducts", RelatedProduct.class, QRelatedProduct.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> sellPrice = createNumber("sellPrice", java.math.BigDecimal.class);

    public final SetPath<Sku, QSku> skuses = this.<Sku, QSku>createSet("skuses", Sku.class, QSku.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> standardPrice = createNumber("standardPrice", java.math.BigDecimal.class);

    public final EnumPath<com.smarteshop.domain.enumeration.StatusEnum> status = createEnum("status", com.smarteshop.domain.enumeration.StatusEnum.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new QBrand(forProperty("brand")) : null;
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

