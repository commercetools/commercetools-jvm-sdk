package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class ShippingMethodImpl extends ResourceImpl<ShippingMethod> implements ShippingMethod {
    private final String name;
    @Nullable
    private final String description;
    private final Reference<io.sphere.sdk.taxcategories.TaxCategory> taxCategory;
    private final List<ZoneRate> zoneRates;
    private final Boolean isDefault;

    @JsonCreator
    private ShippingMethodImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String name, @Nullable final String description, final Reference<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final Boolean isDefault) {
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
    @Nullable
    public String getDescription() {
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
    public Boolean isDefault() {
        return isDefault;
    }
}
