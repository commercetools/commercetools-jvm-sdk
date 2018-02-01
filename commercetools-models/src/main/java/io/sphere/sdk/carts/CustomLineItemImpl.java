package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;

final class CustomLineItemImpl extends LineItemLikeImpl implements CustomLineItem {
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final TaxRate taxRate;
    @Nullable
    private final CustomFields custom;
    private final MonetaryAmount totalPrice;
    private final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity;
    @Nullable
    private final TaxedItemPrice taxedPrice;
    @Nullable private final ItemShippingDetails shippingDetails;


    @JsonCreator
    CustomLineItemImpl(final String id, final LocalizedString name, final MonetaryAmount money,
                       final String slug, final Long quantity, final Set<ItemState> state,
                       final Reference<TaxCategory> taxCategory, @Nullable final TaxRate taxRate,
                       @Nullable final DiscountedLineItemPrice discountedPrice, @Nullable final CustomFields custom,
                       final MonetaryAmount totalPrice,
                       final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity,
                       @Nullable final TaxedItemPrice taxedPrice,
                        @Nullable  final ItemShippingDetails shippingDetails  ) {
        super(id, state, quantity, discountedPrice);
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.taxCategory = taxCategory;
        this.taxRate = taxRate;
        this.custom = custom;
        this.totalPrice = totalPrice;
        this.taxedPrice = taxedPrice;
        this.shippingDetails = shippingDetails;
        this.discountedPricePerQuantity = discountedPricePerQuantity != null ? discountedPricePerQuantity : emptyList();
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
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    @Nullable
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    @Override
    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    @Override
    public List<DiscountedLineItemPriceForQuantity> getDiscountedPricePerQuantity() {
        return discountedPricePerQuantity;
    }

    @Nullable
    @Override
    public ItemShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    @Override
    @Nullable
    public TaxedItemPrice getTaxedPrice() {
        return taxedPrice;
    }
}
