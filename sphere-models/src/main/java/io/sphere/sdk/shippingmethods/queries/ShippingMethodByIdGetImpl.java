package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

final class ShippingMethodByIdGetImpl extends MetaModelGetDslImpl<ShippingMethod, ShippingMethod, ShippingMethodByIdGet, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodByIdGet {
    ShippingMethodByIdGetImpl(final String id) {
        super(id, ShippingMethodEndpoint.ENDPOINT, ShippingMethodExpansionModel.of(), ShippingMethodByIdGetImpl::new);
    }

    ShippingMethodByIdGetImpl(MetaModelGetDslBuilder<ShippingMethod, ShippingMethod, ShippingMethodByIdGet, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}
