package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

final class ShippingMethodQueryImpl extends MetaModelQueryDslImpl<ShippingMethod, ShippingMethodQuery, ShippingMethodQueryModel, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodQuery {
    ShippingMethodQueryImpl(){
        super(ShippingMethodEndpoint.ENDPOINT.endpoint(), ShippingMethodQuery.resultTypeReference(), ShippingMethodQueryModel.of(), ShippingMethodExpansionModel.of(), ShippingMethodQueryImpl::new);
    }

    private ShippingMethodQueryImpl(final MetaModelQueryDslBuilder<ShippingMethod, ShippingMethodQuery, ShippingMethodQueryModel, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}