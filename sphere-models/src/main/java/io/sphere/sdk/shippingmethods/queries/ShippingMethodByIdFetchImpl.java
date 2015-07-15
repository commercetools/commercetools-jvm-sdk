package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;

final class ShippingMethodByIdFetchImpl extends MetaModelFetchDslImpl<ShippingMethod, ShippingMethod, ShippingMethodByIdFetch, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodByIdFetch {
    ShippingMethodByIdFetchImpl(final String id) {
        super(id, ShippingMethodEndpoint.ENDPOINT, ShippingMethodExpansionModel.of(), ShippingMethodByIdFetchImpl::new);
    }

    ShippingMethodByIdFetchImpl(MetaModelFetchDslBuilder<ShippingMethod, ShippingMethod, ShippingMethodByIdFetch, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}
