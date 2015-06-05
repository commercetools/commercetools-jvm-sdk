package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

final class ShippingMethodQueryImpl extends UltraQueryDslImpl<ShippingMethod, ShippingMethodQuery, ShippingMethodQueryModel<ShippingMethod>, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodQuery {
    ShippingMethodQueryImpl(){
        super(ShippingMethodEndpoint.ENDPOINT.endpoint(), ShippingMethodQuery.resultTypeReference(), ShippingMethodQueryModel.of(), ShippingMethodExpansionModel.of(), ShippingMethodQueryImpl::new);
    }

    private ShippingMethodQueryImpl(final UltraQueryDslBuilder<ShippingMethod, ShippingMethodQuery, ShippingMethodQueryModel<ShippingMethod>, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}