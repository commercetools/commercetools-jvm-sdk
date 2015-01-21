package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.List;
import java.util.Optional;

public class ShippingMethodDraft {
    private final String name;
    private final Optional<String> description;
    private final Reference<TaxCategory> taxCategory;
    private final List<ZoneRate> zoneRates;
    private final boolean isDefault;

    private ShippingMethodDraft(final String name, final Optional<String> description, final Reference<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final boolean isDefault) {
        this.name = name;
        this.description = description;
        this.taxCategory = taxCategory;
        this.zoneRates = zoneRates;
        this.isDefault = isDefault;
    }

    public static ShippingMethodDraft of(final String name, final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRate> zoneRates) {
        return of(name, Optional.of(description), taxCategory, zoneRates, false);
    }

    public static ShippingMethodDraft of(final String name, final Optional<String> description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final boolean isDefault) {
        return new ShippingMethodDraft(name, description, taxCategory.toReference(), zoneRates, isDefault);
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public List<ZoneRate> getZoneRates() {
        return zoneRates;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
