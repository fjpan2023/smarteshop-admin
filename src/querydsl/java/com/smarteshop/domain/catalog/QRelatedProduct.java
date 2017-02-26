package com.smarteshop.domain.catalog;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRelatedProduct is a Querydsl query type for RelatedProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRelatedProduct extends EntityPathBase<RelatedProduct> {

    private static final long serialVersionUID = -541095212L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRelatedProduct relatedProduct = new QRelatedProduct("relatedProduct");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QProduct product;

    public final NumberPath<Long> relatedProductId = createNumber("relatedProductId", Long.class);

    public QRelatedProduct(String variable) {
        this(RelatedProduct.class, forVariable(variable), INITS);
    }

    public QRelatedProduct(Path<? extends RelatedProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRelatedProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRelatedProduct(PathMetadata metadata, PathInits inits) {
        this(RelatedProduct.class, metadata, inits);
    }

    public QRelatedProduct(Class<? extends RelatedProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

