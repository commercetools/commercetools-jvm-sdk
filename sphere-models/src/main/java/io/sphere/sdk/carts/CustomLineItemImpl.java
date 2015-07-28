package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Set;

final class CustomLineItemImpl extends LineItemLikeImpl implements CustomLineItem {
    private final LocalizedStrings name;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final TaxRate taxRate;

    @JsonCreator
    CustomLineItemImpl(final String id, final LocalizedStrings name, final MonetaryAmount money,
                       final String slug, final Long quantity, final Set<ItemState> state,
                       final Reference<TaxCategory> taxCategory, final TaxRate taxRate,
                       @Nullable final DiscountedLineItemPrice discountedPrice) {
        super(id, state, quantity, discountedPrice);
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.taxCategory = taxCategory;
        this.taxRate = taxRate;
    }

    @Override
    public LocalizedStrings getName() {
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
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    @Nullable
    public TaxRate getTaxRate() {
        return taxRate;
    }
}
