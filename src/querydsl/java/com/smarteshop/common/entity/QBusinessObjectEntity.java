package com.smarteshop.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessObjectEntity is a Querydsl query type for BusinessObjectEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBusinessObjectEntity extends EntityPathBase<BusinessObjectEntity<? extends java.io.Serializable, ?>> {

    private static final long serialVersionUID = -451599175L;

    public static final QBusinessObjectEntity businessObjectEntity = new QBusinessObjectEntity("businessObjectEntity");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.ZonedDateTime> createdDate = createDateTime("createdDate", java.time.ZonedDateTime.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.ZonedDateTime.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBusinessObjectEntity(String variable) {
        super((Class) BusinessObjectEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBusinessObjectEntity(Path<? extends BusinessObjectEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBusinessObjectEntity(PathMetadata metadata) {
        super((Class) BusinessObjectEntity.class, metadata);
    }

}

