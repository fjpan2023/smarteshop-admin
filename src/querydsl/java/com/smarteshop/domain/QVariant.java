package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVariant is a Querydsl query type for Variant
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVariant extends EntityPathBase<Variant> {

    private static final long serialVersionUID = 1404684234L;

    public static final QVariant variant = new QVariant("variant");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final SetPath<VariantValue, QVariantValue> variantValues = this.<VariantValue, QVariantValue>createSet("variantValues", VariantValue.class, QVariantValue.class, PathInits.DIRECT2);

    public QVariant(String variable) {
        super(Variant.class, forVariable(variable));
    }

    public QVariant(Path<? extends Variant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVariant(PathMetadata metadata) {
        super(Variant.class, metadata);
    }

}

