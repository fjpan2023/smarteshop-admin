package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersistentToken is a Querydsl query type for PersistentToken
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPersistentToken extends EntityPathBase<PersistentToken> {

    private static final long serialVersionUID = 1650746343L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersistentToken persistentToken = new QPersistentToken("persistentToken");

    public final StringPath ipAddress = createString("ipAddress");

    public final StringPath series = createString("series");

    public final DatePath<java.time.LocalDate> tokenDate = createDate("tokenDate", java.time.LocalDate.class);

    public final StringPath tokenValue = createString("tokenValue");

    public final QUser user;

    public final StringPath userAgent = createString("userAgent");

    public QPersistentToken(String variable) {
        this(PersistentToken.class, forVariable(variable), INITS);
    }

    public QPersistentToken(Path<? extends PersistentToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersistentToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersistentToken(PathMetadata metadata, PathInits inits) {
        this(PersistentToken.class, metadata, inits);
    }

    public QPersistentToken(Class<? extends PersistentToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

