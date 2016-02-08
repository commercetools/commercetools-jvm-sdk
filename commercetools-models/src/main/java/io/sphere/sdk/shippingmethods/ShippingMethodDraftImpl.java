package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;

final class ShippingMethodDraftImpl extends Base implements ShippingMethodDraft {
    private final String name;
    @Nullable
    private final String description;
    private final Reference<TaxCategory> taxCategory;
    private final List<ZoneRate> zoneRates;
    private final Boolean isDefault;

    @JsonCreator
    ShippingMethodDraftImpl(final String name, @Nullable final String description, final Reference<TaxCategory> taxCategory, final List<ZoneRate> zoneRates, final Boolean isDefault) {
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
