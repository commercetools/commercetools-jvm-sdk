package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ShippingMethodDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        factoryMethods = {
            @FactoryMethod(parameterNames = {"name", "description", "taxCategory", "zoneRates", "default"}),
            @FactoryMethod(parameterNames = {"name", "taxCategory", "zoneRates", "default"}),
            @FactoryMethod(parameterNames = {"name", "localizedDescription", "taxCategory", "zoneRates", "default"})})
public interface ShippingMethodDraft {

    @Nullable
    String getKey();

    String getName();

    @Nullable
    String getDescription();

    @Nullable
    LocalizedString getLocalizedDescription();

    ResourceIdentifier<TaxCategory> getTaxCategory();

    List<ZoneRateDraft> getZoneRates();

    @JsonProperty("isDefault")
    Boolean isDefault();

    @Nullable
    String getPredicate();

    @Deprecated
    static ShippingMethodDraft of(final String name, @Nullable final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRateDraft> zoneRates) {
        return of(name, description, taxCategory, zoneRates, false);
    }

    @Deprecated
    static ShippingMethodDraft of(final String name, @Nullable final String description, final Referenceable<TaxCategory> taxCategory, final List<ZoneRateDraft> zoneRates, final boolean isDefault) {
        return ShippingMethodDraftDsl.of(name, description, taxCategory.toReference(), zoneRates, isDefault);
    }

    static ShippingMethodDraft of(final String name, @Nullable final LocalizedString localizedDescription, final Referenceable<TaxCategory> taxCategory, final List<ZoneRateDraft> zoneRates) {
        return of(name, localizedDescription, taxCategory, zoneRates, false);
    }

    static ShippingMethodDraft of(final String name, @Nullable final LocalizedString localizedDescription, final Referenceable<TaxCategory> taxCategory, final List<ZoneRateDraft> zoneRates, final boolean isDefault) {
        return ShippingMethodDraftDsl.of(name, localizedDescription, taxCategory.toReference(), zoneRates, isDefault);
    }
}
