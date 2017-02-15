package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMerchantStore is a Querydsl query type for MerchantStore
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMerchantStore extends EntityPathBase<MerchantStore> {

    private static final long serialVersionUID = -236287106L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMerchantStore merchantStore = new QMerchantStore("merchantStore");

    public final com.smarteshop.common.entity.QAbstractBusinessObjectEntity _super = new com.smarteshop.common.entity.QAbstractBusinessObjectEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final QCurrency currency;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final EnumPath<com.smarteshop.domain.enumeration.StatusEnum> status = createEnum("status", com.smarteshop.domain.enumeration.StatusEnum.class);

    public final StringPath url = createString("url");

    public QMerchantStore(String variable) {
        this(MerchantStore.class, forVariable(variable), INITS);
    }

    public QMerchantStore(Path<? extends MerchantStore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMerchantStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMerchantStore(PathMetadata metadata, PathInits inits) {
        this(MerchantStore.class, metadata, inits);
    }

    public QMerchantStore(Class<? extends MerchantStore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.currency = inits.isInitialized("currency") ? new QCurrency(forProperty("currency")) : null;
    }

}

