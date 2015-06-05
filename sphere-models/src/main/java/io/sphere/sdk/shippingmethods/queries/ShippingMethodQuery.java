package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.UltraQueryDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;
import io.sphere.sdk.taxcategories.TaxCategory;

public interface ShippingMethodQuery extends UltraQueryDsl<ShippingMethod, ShippingMethodQuery, ShippingMethodQueryModel<ShippingMethod>, ShippingMethodExpansionModel<ShippingMethod>> {
    static TypeReference<PagedQueryResult<ShippingMethod>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ShippingMethod>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ShippingMethod>>";
            }
        };
    }

    static ShippingMethodQuery of() {
        return new ShippingMethodQueryImpl();
    }

    default ShippingMethodQuery byName(final String name) {
        return withPredicate(ShippingMethodQueryModel.of().name().is(name));
    }

    default ShippingMethodQuery byTaxCategory(final Referenceable<TaxCategory> taxCategory) {
        return withPredicate(m -> m.taxCategory().is(taxCategory));
    }

    default ShippingMethodQuery byIsDefault() {
        return withPredicate(m -> m.isDefault().is(true));
    }
}
