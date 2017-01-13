package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.products.expansion.ProductVariantExpansionModel;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

import java.util.List;

final class LineItemExpansionModelImpl<T> extends ExpansionModelImpl<T> implements LineItemExpansionModel<T> {
    LineItemExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ChannelExpansionModel<T> supplyChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "supplyChannel");
    }

    @Override
    public ChannelExpansionModel<T> distributionChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "distributionChannel");
    }

    @Override
    public ItemStateExpansionModel<T> state() {
        return state("*");
    }

    @Override
    public ItemStateExpansionModel<T> state(final int index) {
        return state("" + index);
    }

    @Override
    public ProductVariantExpansionModel<T> variant() {
        return ProductVariantExpansionModel.of(buildPathExpression(), "variant");
    }

    @Override
    public DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity() {
        return discountedPricePerQuantity("*");
    }

    @Override
    public DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity(final int index) {
        return discountedPricePerQuantity("" + index);
    }

    @Override
    public ProductTypeExpansionModel<T> productType() {
        return ProductTypeExpansionModel.of(buildPathExpression(), "productType");
    }

    private ItemStateExpansionModel<T> state(final String s) {
        return new ItemStateExpansionModelImpl<>(pathExpression(), "state[" + s + "]");
    }

    private DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity(final String s) {
        return new DiscountedLineItemPricePerQuantityExpansionModelImpl<>(pathExpression(), "discountedPricePerQuantity[" + s + "]");
    }
}
