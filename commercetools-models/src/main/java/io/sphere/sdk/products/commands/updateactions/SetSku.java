package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds, changes or removes a SKU on a product variant.
 *
 * A SKU can only be changed or removed from a variant through this operation
 * if there is no inventory entry associated with that SKU.
 * This change is staged and needs to be published.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setSku()}
 *
 *
 * Since version 1.0.0-RC5 this class executes the action {@code setSku} which is staged,
 * previous to this version it was {@code setSKU} (upper case 'K' and 'U')
 * which updates in staged and current and has been moved to the deprecated class {@link LegacySetSku}.
 * See also <a href="https://docs.commercetools.com/http-api-projects-products.html#set-sku" target="_blank">the HTTP API doc of SetSku.</a>
 */
public final class SetSku extends StagedProductUpdateActionImpl<Product> {
    private final Integer variantId;
    @Nullable
    private final String sku;

    private SetSku(final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        super("setSku", staged);
        this.variantId = variantId;
        this.sku = sku;
    }

    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static SetSku of(final Integer variantId, @Nullable final String sku) {
        return of(variantId, sku, null);
    }

    public static SetSku of(final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        return new SetSku(variantId, sku, staged);
    }
}
