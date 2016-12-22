package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerGroup is a Querydsl query type for CustomerGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerGroup extends EntityPathBase<CustomerGroup> {

    private static final long serialVersionUID = -169004858L;

    public static final QCustomerGroup customerGroup = new QCustomerGroup("customerGroup");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QCustomerGroup(String variable) {
        super(CustomerGroup.class, forVariable(variable));
    }

    public QCustomerGroup(Path<? extends CustomerGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerGroup(PathMetadata metadata) {
        super(CustomerGroup.class, metadata);
    }

}

