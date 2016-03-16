package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;

import java.util.List;

/**
 * Sets the prices of a product variant. The same validation rules as for addPrice apply. All previous price information is lost and even if some prices did not change, all the prices will have new ids.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setPrices()}
 *
 * @see io.sphere.sdk.products.ProductVariant#getPrices()
 */
public final class SetPrices extends UpdateActionImpl<Product> {
    private final Integer variantId;
    private final List<PriceDraft> prices;

    private SetPrices(final Integer variantId, final List<PriceDraft> prices) {
        super("setPrices");
        this.variantId = variantId;
        this.prices = prices;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public List<PriceDraft> getPrices() {
        return prices;
    }

    public static SetPrices of(final Integer variantId, final List<PriceDraft> prices) {
        return new SetPrices(variantId, prices);
    }
}
