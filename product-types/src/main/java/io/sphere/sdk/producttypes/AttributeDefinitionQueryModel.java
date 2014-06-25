package io.sphere.sdk.producttypes;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public final class AttributeDefinitionQueryModel<T> extends EmbeddedQueryModel<T, ProductTypeQueryModel<ProductType>> {

    private static final AttributeDefinitionQueryModel<AttributeDefinitionQueryModel<ProductType>> instance =
            new AttributeDefinitionQueryModel<>(Optional.absent(), Optional.<String>absent());

    public static AttributeDefinitionQueryModel<AttributeDefinitionQueryModel<ProductType>> get() {
        return instance;
    }

    AttributeDefinitionQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<T> name() {
        return nameModel();
    }

    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModel<T>(Optional.of(this), Optional.of("type"));
    }
}
