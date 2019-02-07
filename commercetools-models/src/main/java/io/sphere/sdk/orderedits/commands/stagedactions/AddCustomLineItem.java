package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class AddCustomLineItem extends OrderEditStagedUpdateActionBase {

    private final LocalizedString name;
    private final Long quantity;
    private final MonetaryAmount money;
    private final String slug;
    @Nullable
    private final ResourceIdentifier<TaxCategory> taxCategory;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    private AddCustomLineItem(final LocalizedString name, final String slug,
                              final MonetaryAmount money, @Nullable final ResourceIdentifier<TaxCategory> taxCategory,
                              final Long quantity, @Nullable final CustomFieldsDraft custom,
                              @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("addCustomLineItem");
        this.name = name;
        this.quantity = quantity;
        this.money = money;
        this.slug = slug;
        this.custom = custom;
        this.externalTaxRate = externalTaxRate;
        this.taxCategory = taxCategory;
    }

    public static AddCustomLineItem of(final LocalizedString name, final String slug,
                                       final MonetaryAmount money, @Nullable final ResourceIdentifier<TaxCategory> taxCategory,
                                       final Long quantity, @Nullable final CustomFieldsDraft custom,
                                       @Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new AddCustomLineItem(name, slug, money, taxCategory, quantity, custom, externalTaxRate);
    }

    public LocalizedString getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public MonetaryAmount getMoney() {
        return money;
    }

    public String getSlug() {
        return slug;
    }

    @Nullable
    public ResourceIdentifier<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public AddCustomLineItem withCustom(@Nullable final CustomFieldsDraft custom) {
        return new AddCustomLineItem(getName(), getSlug(), getMoney(), getTaxCategory(), getQuantity(), custom, getExternalTaxRate());
    }
}
