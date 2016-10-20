package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.products.expansion.ProductVariantExpansionModel;

public interface LineItemExpansionModel<T> {
    ChannelExpansionModel<T> supplyChannel();

    ChannelExpansionModel<T> distributionChannel();

    ItemStateExpansionModel<T> state();

    ItemStateExpansionModel<T> state(int index);

    ProductVariantExpansionModel<T> variant();

    DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity();

    DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity(int index);
}
