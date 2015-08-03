package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

/**
 * Removes a variant from a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddVariant
 */
public class RemoveVariant extends UpdateAction<Product> {
    private final Integer id;

    private RemoveVariant(final Integer id) {
        super("removeVariant");
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static RemoveVariant of(final ProductVariant variant) {
        return of(variant.getId());
    }

    public static RemoveVariant of(final Integer id) {
        return new RemoveVariant(id);
    }
}
