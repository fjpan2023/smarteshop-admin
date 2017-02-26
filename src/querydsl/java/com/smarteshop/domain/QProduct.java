package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.smarteshop.domain.catalog.Product;
import com.smarteshop.domain.catalog.ProductOption;
import com.smarteshop.domain.catalog.RelatedProduct;
import com.smarteshop.domain.catalog.Sku;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 858424372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final ListPath<Sku, QSku> additionalSkus = this.<Sku, QSku>createList("additionalSkus", Sku.class, QSku.class, PathInits.DIRECT2);

    public final QBrand brand;

    public final QCategory category;

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final QSku defaultSku;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.smarteshop.domain.enumeration.ProductLabelEnum> label = createEnum("label", com.smarteshop.domain.enumeration.ProductLabelEnum.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> mainImageId = createNumber("mainImageId", Long.class);

    public final QMerchantStore merchantStore;

    public final StringPath name = createString("name");

    public final SetPath<ProductOption, QProductOption> productOptions = this.<ProductOption, QProductOption>createSet("productOptions", ProductOption.class, QProductOption.class, PathInits.DIRECT2);

    public final SetPath<RelatedProduct, QRelatedProduct> relatedProducts = this.<RelatedProduct, QRelatedProduct>createSet("relatedProducts", RelatedProduct.class, QRelatedProduct.class, PathInits.DIRECT2);

    public final EnumPath<com.smarteshop.domain.enumeration.StatusEnum> status = createEnum("status", com.smarteshop.domain.enumeration.StatusEnum.class);

    public final StringPath url = createString("url");

    public final StringPath urlKey = createString("urlKey");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new QBrand(forProperty("brand")) : null;
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.defaultSku = inits.isInitialized("defaultSku") ? new QSku(forProperty("defaultSku"), inits.get("defaultSku")) : null;
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

