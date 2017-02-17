package com.smarteshop.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBilling is a Querydsl query type for Billing
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QBilling extends BeanPath<Billing> {

    private static final long serialVersionUID = 1934070050L;

    public static final QBilling billing = new QBilling("billing");

    public final StringPath address = createString("address");

    public final StringPath city = createString("city");

    public final StringPath company = createString("company");

    public final StringPath country = createString("country");

    public final StringPath firstName = createString("firstName");

    public final StringPath lastName = createString("lastName");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath state = createString("state");

    public final StringPath telephone = createString("telephone");

    public final StringPath zone = createString("zone");

    public QBilling(String variable) {
        super(Billing.class, forVariable(variable));
    }

    public QBilling(Path<? extends Billing> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBilling(PathMetadata metadata) {
        super(Billing.class, metadata);
    }

}

