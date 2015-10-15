package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

final class TypeByIdGetImpl extends MetaModelGetDslImpl<Type, Type, TypeByIdGet, TypeExpansionModel<Type>> implements TypeByIdGet {
    TypeByIdGetImpl(final String id) {
        super(id, TypeEndpoint.ENDPOINT, TypeExpansionModel.of(), TypeByIdGetImpl::new);
    }

    public TypeByIdGetImpl(MetaModelGetDslBuilder<Type, Type, TypeByIdGet, TypeExpansionModel<Type>> builder) {
        super(builder);
    }
}
