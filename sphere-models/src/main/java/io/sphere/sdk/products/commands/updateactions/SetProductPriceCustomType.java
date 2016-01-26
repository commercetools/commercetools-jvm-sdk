package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a price.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setProductPriceCustomTypeAndsetProductPriceCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public class SetProductPriceCustomType extends SetCustomTypeBase<Product> {
    private final String priceId;

    private SetProductPriceCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String priceId) {
        super("setProductPriceCustomType", typeId, typeKey, fields);
        this.priceId = priceId;
    }

    public static SetProductPriceCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String priceId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, priceId);
    }

    public static SetProductPriceCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String priceId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, priceId);
    }

    public static SetProductPriceCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String priceId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, priceId);
    }

    public static SetProductPriceCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String priceId) {
        return new SetProductPriceCustomType(typeId, null, fields, priceId);
    }

    public static SetProductPriceCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String priceId) {
        return new SetProductPriceCustomType(null, typeKey, fields, priceId);
    }

    public static SetProductPriceCustomType ofRemoveType(final String priceId) {
        return new SetProductPriceCustomType(null, null, null, priceId);
    }

    public String getPriceId() {
        return priceId;
    }
}
