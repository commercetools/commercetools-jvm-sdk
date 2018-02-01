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
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptyList;

final class LineItemImpl extends LineItemImplBase {

    @JsonCreator
    LineItemImpl(final CustomFields custom,
                 final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity,
                 @Nullable final Reference<Channel> distributionChannel, final String id,
                 final LineItemMode lineItemMode, final LocalizedString name, final Price price,
                 final LineItemPriceMode priceMode, final String productId,
                 @Nullable final LocalizedString productSlug,
                 @Nullable final Reference<ProductType> productType, final Long quantity,
                 @Nullable final ItemShippingDetails shippingDetails,
                 final Set<ItemState> state, @Nullable final Reference<Channel> supplyChannel,
                 @Nullable final TaxRate taxRate, @Nullable final TaxedItemPrice taxedPrice,
                 final MonetaryAmount totalPrice, final ObjectNode variant) {
        super(custom, discountedPricePerQuantity, distributionChannel, id, lineItemMode, name, price, priceMode, productId, productSlug,
                productType, quantity, shippingDetails, state, supplyChannel, taxRate, taxedPrice, totalPrice, asVariant(variant, productId));

    }

    private static ProductVariant asVariant(final ObjectNode variant, final String productId) {
        if (variant == null) {
            return null;
        }
        Objects.requireNonNull(productId);
        variant.put("productId", productId);
        return SphereJsonUtils.readObject(variant, ProductVariant.class);
    }
}
