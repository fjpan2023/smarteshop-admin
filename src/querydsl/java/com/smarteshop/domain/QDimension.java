package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDimension is a Querydsl query type for Dimension
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QDimension extends BeanPath<Dimension> {

    private static final long serialVersionUID = 268920683L;

    public static final QDimension dimension = new QDimension("dimension");

    public final StringPath container = createString("container");

    public final NumberPath<java.math.BigDecimal> depth = createNumber("depth", java.math.BigDecimal.class);

    public final StringPath dimensionUnitOfMeasure = createString("dimensionUnitOfMeasure");

    public final NumberPath<java.math.BigDecimal> girth = createNumber("girth", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> height = createNumber("height", java.math.BigDecimal.class);

    public final StringPath size = createString("size");

    public final NumberPath<java.math.BigDecimal> width = createNumber("width", java.math.BigDecimal.class);

    public QDimension(String variable) {
        super(Dimension.class, forVariable(variable));
    }

    public QDimension(Path<? extends Dimension> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDimension(PathMetadata metadata) {
        super(Dimension.class, metadata);
    }

}

