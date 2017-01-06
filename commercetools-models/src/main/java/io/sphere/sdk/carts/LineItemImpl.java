package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;

final class LineItemImpl extends LineItemLikeImpl implements LineItem {

    private final String productId;
    private final LocalizedString name;
    @Nullable
    private final LocalizedString productSlug;
    private final ProductVariant variant;
    private final Price price;
    @Nullable
    private final TaxRate taxRate;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final Reference<Channel> distributionChannel;
    @Nullable
    private final CustomFields custom;
    private final MonetaryAmount totalPrice;
    private final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity;
    @Nullable
    private final TaxedItemPrice taxedPrice;
    private final LineItemPriceMode priceMode;

    @JsonCreator
    LineItemImpl(final String id, final String productId, final LocalizedString name,
                 final JsonNode variant, final Price price, final Long quantity,
                 final Set<ItemState> state, @Nullable final TaxRate taxRate,
                 @Nullable final Reference<Channel> supplyChannel, final DiscountedLineItemPrice discountedPrice,
                 @Nullable final LocalizedString productSlug, @Nullable final Reference<Channel> distributionChannel,
                 @Nullable final CustomFields custom, final MonetaryAmount totalPrice,
                 final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity,
                 @Nullable final TaxedItemPrice taxedPrice, final LineItemPriceMode priceMode) {
        super(id, state, quantity, discountedPrice);
        this.productId = productId;
        this.name = name;
        this.taxedPrice = taxedPrice;
        if (variant instanceof ObjectNode) {
            ((ObjectNode) variant).put("productId", productId);
        }
        this.variant = SphereJsonUtils.readObject(variant, ProductVariant.class);
        this.price = price;
        this.taxRate = taxRate;
        this.supplyChannel = supplyChannel;
        this.productSlug = productSlug;
        this.distributionChannel = distributionChannel;
        this.custom = custom;
        this.totalPrice = totalPrice;
        this.discountedPricePerQuantity = discountedPricePerQuantity != null ? discountedPricePerQuantity : emptyList();
        this.priceMode = priceMode;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public ProductVariant getVariant() {
        return variant;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    @Nullable
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Nullable
    @Override
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    @Nullable
    @Override
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    @Override
    @Nullable
    public LocalizedString getProductSlug() {
        return productSlug;
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

    @Override
    @Nullable
    public TaxedItemPrice getTaxedPrice() {
        return taxedPrice;
    }

    @Override
    public LineItemPriceMode getPriceMode() {
        return priceMode;
    }
}
