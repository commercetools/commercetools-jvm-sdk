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
import java.util.Set;

import static java.util.Collections.emptyList;

final class LineItemImpl extends LineItemImplBase {


    private final ProductVariant variant;


    @JsonCreator
    LineItemImpl(final String id, final String productId, final LocalizedString name,
                 final JsonNode variant, final Price price, final Long quantity,
                 final Set<ItemState> state, @Nullable final TaxRate taxRate,
                 @Nullable final Reference<Channel> supplyChannel, final DiscountedLineItemPrice discountedPrice,
                 @Nullable final LocalizedString productSlug, @Nullable final Reference<Channel> distributionChannel,
                 @Nullable final CustomFields custom, final MonetaryAmount totalPrice,
                 final List<DiscountedLineItemPriceForQuantity> discountedPricePerQuantity,
                 @Nullable final TaxedItemPrice taxedPrice, final LineItemPriceMode priceMode,
                 @Nullable final Reference<ProductType> productType) {
        super(custom, discountedPricePerQuantity, distributionChannel, id, name, price, priceMode, productId, productSlug,
                productType, quantity, state, supplyChannel, taxRate, taxedPrice, totalPrice, null);

        if (variant instanceof ObjectNode) {
            ((ObjectNode) variant).put("productId", productId);
        }

        this.variant = SphereJsonUtils.readObject(variant, ProductVariant.class);

    }


    @Override
    public ProductVariant getVariant() {
        return variant;
    }


}
