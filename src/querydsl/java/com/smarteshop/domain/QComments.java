package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QComments is a Querydsl query type for Comments
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComments extends EntityPathBase<Comments> {

    private static final long serialVersionUID = 1242697551L;

    public static final QComments comments = new QComments("comments");

    public final com.smarteshop.common.entity.QAbstractBusinessObjectEntity _super = new com.smarteshop.common.entity.QAbstractBusinessObjectEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isHandle = createBoolean("isHandle");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath subject = createString("subject");

    public final StringPath userName = createString("userName");

    public QComments(String variable) {
        super(Comments.class, forVariable(variable));
    }

    public QComments(Path<? extends Comments> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComments(PathMetadata metadata) {
        super(Comments.class, metadata);
    }

}

