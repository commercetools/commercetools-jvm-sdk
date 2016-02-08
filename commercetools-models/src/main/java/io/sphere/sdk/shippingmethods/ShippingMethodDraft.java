package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ShippingMethodDraftImpl.class)
public interface ShippingMethodDraft {
    String getName();

    @Nullable
    String getDescription();

    Reference<TaxCategory> getTaxCategory();

    List<ZoneRate> getZoneRates();

    Boolean isDefault();


    static ShippingMethodDraft of(final String name, @Nullable final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRate> zoneRates) {
        return of(name, description, taxCategory, zoneRates, false);
    }

    static ShippingMethodDraft of(final String name, @Nullable final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final boolean isDefault) {
        return new ShippingMethodDraftImpl(name, description, taxCategory.toReference(), zoneRates, isDefault);
    }
}
