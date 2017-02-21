package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMedia is a Querydsl query type for Media
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMedia extends EntityPathBase<Media> {

    private static final long serialVersionUID = -1146407831L;

    public static final QMedia media = new QMedia("media");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final ArrayPath<byte[], Byte> content = createArray("content", byte[].class);

    public final NumberPath<Long> contentSize = createNumber("contentSize", Long.class);

    public final StringPath contentType = createString("contentType");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> entityId = createNumber("entityId", Long.class);

    public final StringPath entityName = createString("entityName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public QMedia(String variable) {
        super(Media.class, forVariable(variable));
    }

    public QMedia(Path<? extends Media> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMedia(PathMetadata metadata) {
        super(Media.class, metadata);
    }

}

