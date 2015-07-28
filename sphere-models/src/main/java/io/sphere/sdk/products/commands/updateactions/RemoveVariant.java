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
    private final Integer id;

    private RemoveVariant(final Integer id, final ProductUpdateScope productUpdateScope) {
        super("removeVariant", productUpdateScope);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static RemoveVariant of(final ProductVariant variant, final ProductUpdateScope productUpdateScope) {
        return of(variant.getId(), productUpdateScope);
    }

    public static RemoveVariant of(final Integer id, final ProductUpdateScope productUpdateScope) {
        return new RemoveVariant(id, productUpdateScope);
    }
}
