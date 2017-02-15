package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QContactPerson is a Querydsl query type for ContactPerson
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContactPerson extends EntityPathBase<ContactPerson> {

    private static final long serialVersionUID = -1780382950L;

    public static final QContactPerson contactPerson = new QContactPerson("contactPerson");

    public final com.smarteshop.common.entity.QAbstractBusinessObjectEntity _super = new com.smarteshop.common.entity.QAbstractBusinessObjectEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lastName = createString("lastName");

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public QContactPerson(String variable) {
        super(ContactPerson.class, forVariable(variable));
    }

    public QContactPerson(Path<? extends ContactPerson> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContactPerson(PathMetadata metadata) {
        super(ContactPerson.class, metadata);
    }

}

