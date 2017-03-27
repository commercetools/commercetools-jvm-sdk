package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

import javax.annotation.Nullable;

/**
 * Updates a custom field in a price.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setProductPriceCustomTypeAndsetProductPriceCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetProductPriceCustomField extends SetCustomFieldBase<Product> {
    private final String priceId;
    @Nullable
    private final Boolean staged;

    private SetProductPriceCustomField(final String name, final JsonNode value, final String priceId, final Boolean staged) {
        super("setProductPriceCustomField", name, value);
        this.priceId = priceId;
        this.staged = staged;
    }

    public static SetProductPriceCustomField ofJson(final String name, final JsonNode value, final String priceId) {
        return ofJson(name, value, priceId, null);
    }

    public static SetProductPriceCustomField ofJson(final String name, final JsonNode value, final String priceId, @Nullable final Boolean staged) {
        return new SetProductPriceCustomField(name, value, priceId, staged);
    }

    public static SetProductPriceCustomField ofObject(final String name, final Object value, final String priceId) {
        return ofObject(name, value, priceId, null);
    }

    public static SetProductPriceCustomField ofObject(final String name, final Object value, final String priceId, @Nullable final Boolean staged) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, priceId, staged);
    }

    public static SetProductPriceCustomField ofUnset(final String name, final String priceId) {
        return ofUnset(name, priceId, null);
    }

    public static SetProductPriceCustomField ofUnset(final String name, final String priceId, @Nullable final Boolean staged) {
        return ofJson(name, null, priceId, staged);
    }

    public String getPriceId() {
        return priceId;
    }

    @Nullable
    public Boolean getStaged() {
        return staged;
    }
}
