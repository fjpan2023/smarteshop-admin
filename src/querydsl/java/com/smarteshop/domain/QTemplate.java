package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTemplate is a Querydsl query type for Template
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTemplate extends EntityPathBase<Template> {

    private static final long serialVersionUID = 523566549L;

    public static final QTemplate template = new QTemplate("template");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Long> superId = createNumber("superId", Long.class);

    public final StringPath templateKey = createString("templateKey");

    public final EnumPath<com.smarteshop.domain.enumeration.TemplateTypeEnum> type = createEnum("type", com.smarteshop.domain.enumeration.TemplateTypeEnum.class);

    public QTemplate(String variable) {
        super(Template.class, forVariable(variable));
    }

    public QTemplate(Path<? extends Template> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTemplate(PathMetadata metadata) {
        super(Template.class, metadata);
    }

}

