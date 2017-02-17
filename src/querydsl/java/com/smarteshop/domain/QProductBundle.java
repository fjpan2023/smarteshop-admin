package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductBundle is a Querydsl query type for ProductBundle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductBundle extends EntityPathBase<ProductBundle> {

    private static final long serialVersionUID = -1740658218L;

    public static final QProductBundle productBundle = new QProductBundle("productBundle");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final BooleanPath autoBundle = createBoolean("autoBundle");

    public final BooleanPath bundlePromotable = createBoolean("bundlePromotable");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath priceModel = createString("priceModel");

    public QProductBundle(String variable) {
        super(ProductBundle.class, forVariable(variable));
    }

    public QProductBundle(Path<? extends ProductBundle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductBundle(PathMetadata metadata) {
        super(ProductBundle.class, metadata);
    }

}

