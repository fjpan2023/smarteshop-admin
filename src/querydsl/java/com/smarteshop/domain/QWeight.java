package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeight is a Querydsl query type for Weight
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QWeight extends BeanPath<Weight> {

    private static final long serialVersionUID = -892465517L;

    public static final QWeight weight1 = new QWeight("weight1");

    public final NumberPath<java.math.BigDecimal> weight = createNumber("weight", java.math.BigDecimal.class);

    public final StringPath weightUnitOfMeasure = createString("weightUnitOfMeasure");

    public QWeight(String variable) {
        super(Weight.class, forVariable(variable));
    }

    public QWeight(Path<? extends Weight> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeight(PathMetadata metadata) {
        super(Weight.class, metadata);
    }

}

