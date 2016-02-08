package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

final class TypeQueryImpl extends MetaModelQueryDslImpl<Type, TypeQuery, TypeQueryModel, TypeExpansionModel<Type>> implements TypeQuery {
    TypeQueryImpl(){
        super(TypeEndpoint.ENDPOINT.endpoint(), TypeQuery.resultTypeReference(), TypeQueryModel.of(), TypeExpansionModel.of(), TypeQueryImpl::new);
    }

    private TypeQueryImpl(final MetaModelQueryDslBuilder<Type, TypeQuery, TypeQueryModel, TypeExpansionModel<Type>> builder) {
        super(builder);
    }
}