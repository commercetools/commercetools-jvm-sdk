package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Set;

final class CustomLineItemImportDraftImpl implements CustomLineItemImportDraft {
    private final String id;
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    private final Long quantity;
    @Nullable
    private final Set<ItemState> state;
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final TaxRate taxRate;

    @Nullable
    private final CustomFieldsDraft custom;

    @Nullable private final ItemShippingDetailsDraft shippingDetails;

    public CustomLineItemImportDraftImpl(final String id, final LocalizedString name, final MonetaryAmount money, final String slug, final Long quantity, @Nullable final Set<ItemState> state, final Reference<TaxCategory> taxCategory, @Nullable final TaxRate taxRate) {
        this(id, name, money, slug, quantity, state, taxCategory, taxRate, null, null);
    }

    @JsonCreator
    public CustomLineItemImportDraftImpl(final String id, final LocalizedString name, final MonetaryAmount money, final String slug, final Long quantity, @Nullable final Set<ItemState> state, final Reference<TaxCategory> taxCategory, @Nullable final TaxRate taxRate, @Nullable final CustomFieldsDraft custom, @Nullable  final ItemShippingDetailsDraft shippingDetails) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.quantity = quantity;
        this.state = state;
        this.taxCategory = taxCategory;
        this.taxRate = taxRate;
        this.custom = custom;
        this.shippingDetails = shippingDetails;
    }

    public String getId() {
        return id;
    }

    @Override
    public MonetaryAmount getMoney() {
        return money;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    @Nullable
    public Set<ItemState> getState() {
        return state;
    }

    @Override
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    @Nullable
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Nullable
    @Override
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    @Override
    public ItemShippingDetailsDraft getShippingDetails() {
        return shippingDetails;
    }
}
