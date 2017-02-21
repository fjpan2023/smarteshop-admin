package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOption is a Querydsl query type for ProductOption
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductOption extends EntityPathBase<ProductOption> {

    private static final long serialVersionUID = -1372913207L;

    public static final QProductOption productOption = new QProductOption("productOption");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final StringPath attributeName = createString("attributeName");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> displayOrder = createNumber("displayOrder", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final SetPath<ProductOptionValue, QProductOptionValue> productOptionValues = this.<ProductOptionValue, QProductOptionValue>createSet("productOptionValues", ProductOptionValue.class, QProductOptionValue.class, PathInits.DIRECT2);

    public final SetPath<Product, QProduct> products = this.<Product, QProduct>createSet("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final StringPath type = createString("type");

    public QProductOption(String variable) {
        super(ProductOption.class, forVariable(variable));
    }

    public QProductOption(Path<? extends ProductOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductOption(PathMetadata metadata) {
        super(ProductOption.class, metadata);
    }

}

