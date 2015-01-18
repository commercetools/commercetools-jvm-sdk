package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

final class ShippingMethodImpl extends DefaultModelImpl<ShippingMethod> implements ShippingMethod {
    private final String name;
    private final Optional<String> description;
    private final Reference<io.sphere.sdk.taxcategories.TaxCategory> taxCategory;
    private final List<ZoneRate> zoneRates;
    private final boolean isDefault;

    private ShippingMethodImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final String name, final Optional<String> description, final Reference<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final boolean isDefault) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.description = description;
        this.taxCategory = taxCategory;
        this.zoneRates = zoneRates;
        this.isDefault = isDefault;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    public List<ZoneRate> getZoneRates() {
        return zoneRates;
    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }
}
