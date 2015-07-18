package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.Optional;

public abstract class CartLikeExpansionModel<T> extends ExpansionModel<T> {
    protected CartLikeExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    protected CartLikeExpansionModel() {
        super();
    }

    public ExpansionPath<T> customerGroup() {
        return expansionPath("customerGroup");
    }

    public DiscountCodeInfoExpansionModel<T> discountCodes() {
        return discountCodes("*");
    }

    public DiscountCodeInfoExpansionModel<T> discountCodes(final int index) {
        return discountCodes("" + index);
    }

    public LineItemExpansionModel<T> lineItems() {
        return new LineItemExpansionModel<>(pathExpressionOption(), "lineItems[*]");
    }

    public LineItemExpansionModel<T> lineItems(final int index) {
        return new LineItemExpansionModel<>(pathExpressionOption(), "lineItems[" + index + "]");
    }

    private DiscountCodeInfoExpansionModel<T> discountCodes(final String s) {
        return new DiscountCodeInfoExpansionModel<>(pathExpressionOption(), "discountCodes[" + s + "]");
    }
}
