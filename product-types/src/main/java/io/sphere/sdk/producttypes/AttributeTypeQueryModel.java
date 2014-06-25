package io.sphere.sdk.producttypes;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public final class AttributeTypeQueryModel<T> extends EmbeddedQueryModel<T, ProductTypeQueryModel<ProductType>> {
    private static final AttributeTypeQueryModel<AttributeTypeQueryModel<ProductType>> instance =
            new AttributeTypeQueryModel<>(Optional.absent(), Optional.<String>absent());

    public static AttributeTypeQueryModel<AttributeTypeQueryModel<ProductType>> get() {
        return instance;
    }

    AttributeTypeQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<T> name() {
        return nameModel();
    }

    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModel<T>(Optional.of(this), Optional.of("elementType"));
    }
}
