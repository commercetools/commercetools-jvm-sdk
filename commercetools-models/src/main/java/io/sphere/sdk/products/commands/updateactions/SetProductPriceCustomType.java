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
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setProductPriceCustomTypeAndsetProductPriceCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetProductPriceCustomType extends SetCustomTypeBase<Product> {
    private final String priceId;
    @Nullable
    final private Boolean staged;

    private SetProductPriceCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String priceId, @Nullable final Boolean staged) {
        super("setProductPriceCustomType", typeId, typeKey, fields);
        this.priceId = priceId;
        this.staged = staged;
    }

    public static SetProductPriceCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String priceId) {
        return ofTypeKeyAndObjects(typeKey, fields, priceId, null);
    }

    public static SetProductPriceCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String priceId, @Nullable final Boolean staged) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, priceId, staged);
    }

    public static SetProductPriceCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String priceId) {
        return ofTypeIdAndObjects(typeId, fields, priceId, null);
    }

    public static SetProductPriceCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String priceId, @Nullable final Boolean staged) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, priceId, staged);
    }

    public static SetProductPriceCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String priceId) {
        return ofTypeIdAndObjects(typeId, fieldName, value, priceId, null);
    }

    public static SetProductPriceCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String priceId, @Nullable final Boolean staged) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, priceId, staged);
    }

    public static SetProductPriceCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String priceId) {
        return ofTypeIdAndJson(typeId, fields, priceId, null);
    }

    public static SetProductPriceCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String priceId, @Nullable final Boolean staged) {
        return new SetProductPriceCustomType(typeId, null, fields, priceId, staged);
    }

    public static SetProductPriceCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String priceId) {
        return ofTypeKeyAndJson(typeKey, fields, priceId, null);
    }

    public static SetProductPriceCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String priceId, @Nullable final Boolean staged) {
        return new SetProductPriceCustomType(null, typeKey, fields, priceId, staged);
    }

    public static SetProductPriceCustomType ofRemoveType(final String priceId) {
        return ofRemoveType(priceId, null);
    }

    public static SetProductPriceCustomType ofRemoveType(final String priceId, @Nullable final Boolean staged) {
        return new SetProductPriceCustomType(null, null, null, priceId, staged);
    }

    public String getPriceId() {
        return priceId;
    }

    @Nullable
    public Boolean getStaged() {
        return staged;
    }
}
