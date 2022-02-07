package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.*;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ShippingMethodDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        factoryMethods = {
            @FactoryMethod(parameterNames = {"name", "description", "taxCategory", "zoneRates", "default"}),
            @FactoryMethod(parameterNames = {"name", "taxCategory", "zoneRates", "default"}),
            @FactoryMethod(parameterNames = {"name", "localizedDescription", "taxCategory", "zoneRates", "default"})})
public interface ShippingMethodDraft extends WithKey, CustomDraft {

    @Nullable
    String getKey();

    String getName();

    @Deprecated
    @Nullable
    String getDescription();

    @Nullable
    LocalizedString getLocalizedDescription();

    @Nullable
    LocalizedString getLocalizedName();

    ResourceIdentifier<TaxCategory> getTaxCategory();

    List<ZoneRateDraft> getZoneRates();

    @JsonProperty("isDefault")
    Boolean isDefault();

    @Nullable
    String getPredicate();

    @Nullable
    CustomFieldsDraft getCustom();

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
