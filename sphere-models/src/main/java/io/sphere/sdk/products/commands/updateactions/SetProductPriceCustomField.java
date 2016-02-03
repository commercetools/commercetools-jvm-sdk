package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a price.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setProductPriceCustomTypeAndsetProductPriceCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetProductPriceCustomField extends SetCustomFieldBase<Product> {
    private final String priceId;

    private SetProductPriceCustomField(final String name, final JsonNode value, final String priceId) {
        super("setProductPriceCustomField", name, value);
        this.priceId = priceId;
    }

    public static SetProductPriceCustomField ofJson(final String name, final JsonNode value, final String priceId) {
        return new SetProductPriceCustomField(name, value, priceId);
    }

    public static SetProductPriceCustomField ofObject(final String name, final Object value, final String priceId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, priceId);
    }

    public static SetProductPriceCustomField ofUnset(final String name, final String priceId) {
        return ofJson(name, null, priceId);
    }

    public String getPriceId() {
        return priceId;
    }
}
