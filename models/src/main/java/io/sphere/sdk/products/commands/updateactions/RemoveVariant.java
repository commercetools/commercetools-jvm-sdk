package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.ProductUpdateScope;
import io.sphere.sdk.products.ProductVariant;

/**
 * Removes a variant from a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddVariant
 */
public class RemoveVariant extends StageableProductUpdateAction {
    private final int id;

    private RemoveVariant(final int id, final ProductUpdateScope productUpdateScope) {
        super("removeVariant", productUpdateScope);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RemoveVariant of(final ProductVariant variant, final ProductUpdateScope productUpdateScope) {
        return of(variant.getId(), productUpdateScope);
    }

    public static RemoveVariant of(final int id, final ProductUpdateScope productUpdateScope) {
        return new RemoveVariant(id, productUpdateScope);
    }
}
