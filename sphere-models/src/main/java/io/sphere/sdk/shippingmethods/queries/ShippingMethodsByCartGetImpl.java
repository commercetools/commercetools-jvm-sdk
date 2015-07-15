package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;

final class ShippingMethodsByCartGetImpl extends MetaModelFetchDslImpl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodsByCartGet {
    ShippingMethodsByCartGetImpl(final String cartId) {
        super("?cartId=" + cartId, ShippingMethodEndpoint.ENDPOINT.withTypeReference(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }), ShippingMethodExpansionModel.of(), ShippingMethodsByCartGetImpl::new);
    }

    public ShippingMethodsByCartGetImpl(MetaModelFetchDslBuilder<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}
