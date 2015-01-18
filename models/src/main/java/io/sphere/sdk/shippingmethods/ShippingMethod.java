package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.List;
import java.util.Optional;

public interface ShippingMethod extends DefaultModel<ShippingMethod> {
    String getName();

    Optional<String> getDescription();

    Reference<TaxCategory> getTaxCategory();

    List<ZoneRate> getZoneRates();

    boolean isDefault();

    @Override
    default Reference<ShippingMethod> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    public static String typeId(){
        return "shipping-method";
    }

    public static TypeReference<ShippingMethod> typeReference(){
        return new TypeReference<ShippingMethod>() {
            @Override
            public String toString() {
                return "TypeReference<ShippingMethod>";
            }
        };
    }
}
