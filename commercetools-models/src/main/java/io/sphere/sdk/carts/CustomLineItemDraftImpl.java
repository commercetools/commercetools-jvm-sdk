package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

final class CustomLineItemDraftImpl extends Base implements CustomLineItemDraft {
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    @Nullable
    private final ResourceIdentifier<TaxCategory> taxCategory;
    private final Long quantity;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;
    @Nullable
    private final ItemShippingDetailsDraft shippingDetails;

    @JsonCreator
    CustomLineItemDraftImpl(final LocalizedString name, final String slug, final MonetaryAmount money, @Nullable final ResourceIdentifier<TaxCategory> taxCategory, final Long quantity, @Nullable final CustomFieldsDraft custom, final ExternalTaxRateDraft externalTaxRate,@Nullable final ItemShippingDetailsDraft shippingDetails) {
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.custom = custom;
        this.taxCategory = taxCategory;
        this.quantity = quantity;
        this.externalTaxRate = externalTaxRate;
        this.shippingDetails = shippingDetails;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public MonetaryAmount getMoney() {
        return money;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public ResourceIdentifier<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

    @Nullable
    @Override
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    @Override
    public ItemShippingDetailsDraft getShippingDetails() {
        return shippingDetails;
    }
}
