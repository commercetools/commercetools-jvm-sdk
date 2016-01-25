package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;

public class ShippingMethodDraft {
    private final String name;
    @Nullable
    private final String description;
    private final Reference<TaxCategory> taxCategory;
    private final List<ZoneRate> zoneRates;
    private final Boolean isDefault;

    private ShippingMethodDraft(final String name, @Nullable final String description, final Reference<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final Boolean isDefault) {
        this.name = name;
        this.description = description;
        this.taxCategory = taxCategory;
        this.zoneRates = zoneRates;
        this.isDefault = isDefault;
    }

    public static ShippingMethodDraft of(final String name, @Nullable final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRate> zoneRates) {
        return of(name, description, taxCategory, zoneRates, false);
    }

    public static ShippingMethodDraft of(final String name, @Nullable final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final boolean isDefault) {
        return new ShippingMethodDraft(name, description, taxCategory.toReference(), zoneRates, isDefault);
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public List<ZoneRate> getZoneRates() {
        return zoneRates;
    }

    public Boolean isDefault() {
        return isDefault;
    }
}
