package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;

import javax.annotation.Nullable;

/**
 * Adds/Removes/Changes a custom attribute.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setAttributeByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setAttributeBySku()}
 *
 * @see SetAttributeInAllVariants
 */
public final class SetAttribute extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String name;
    @Nullable
    private final JsonNode value;

    SetAttribute(@Nullable final Integer variantId, @Nullable final String sku, final String name, @Nullable final JsonNode value, @Nullable final Boolean staged) {
        super("setAttribute", staged);
        this.variantId = variantId;
        this.sku = sku;
        this.name = name;
        this.value = value;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @return update action
     */
    public static SetAttribute of(final Integer variantId, final String name, @Nullable final JsonNode value) {
        return of(variantId, name, value, null);
    }

    public static SetAttribute of(final Integer variantId, final String name, @Nullable final JsonNode value, @Nullable final Boolean staged) {
        return ofVariantId(variantId, name, value, staged);
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @return update action
     */
    public static SetAttribute ofVariantId(final Integer variantId, final String name, @Nullable final JsonNode value) {
        return ofVariantId(variantId, name, value, null);
    }

    public static SetAttribute ofVariantId(final Integer variantId, final String name, @Nullable final JsonNode value, @Nullable final Boolean staged) {
        return new SetAttribute(variantId, null, name, value, staged);
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @return update action
     */
    public static SetAttribute ofVariantId(final Integer variantId, final String name, @Nullable final Object value) {
        return ofVariantId(variantId, name, value, null);
    }

    public static SetAttribute ofVariantId(final Integer variantId, final String name, @Nullable final Object value, @Nullable final Boolean staged) {
        return new SetAttribute(variantId, null, name, SphereJsonUtils.toJsonNode(value), staged);
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param sku
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @return update action
     */
    public static SetAttribute ofSku(final String sku, final String name, @Nullable final JsonNode value) {
        return ofSku(sku, name, value, null);
    }

    public static SetAttribute ofSku(final String sku, final String name, @Nullable final JsonNode value, @Nullable final Boolean staged) {
        return new SetAttribute(null, sku, name, value, staged);
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param sku
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @return update action
     */
    public static SetAttribute ofSku(final String sku, final String name, @Nullable final Object value) {
        return ofSku(sku, name, value, null);
    }

    public static SetAttribute ofSku(final String sku, final String name, @Nullable final Object value, @Nullable final Boolean staged) {
        return new SetAttribute(null, sku, name, SphereJsonUtils.toJsonNode(value), staged);
    }

    /**
     * Action to remove a custom attribute.
     * @param variantId the variant the attribute value should be unset
     * @param name the name of the attribute, consult the product type to find the name
     * @return update action
     */
    public static SetAttribute ofUnsetAttribute(final Integer variantId, final String name) {
        return ofUnsetAttribute(variantId, name, null);
    }

    public static SetAttribute ofUnsetAttribute(final Integer variantId, final String name, @Nullable final Boolean staged) {
        return ofVariantId(variantId, name, null, staged);
    }

    /**
     * Action to remove a custom attribute.
     * @param variantId the variant the attribute value should be unset
     * @param name the name of the attribute, consult the product type to find the name
     * @return update action
     */
    public static SetAttribute ofUnsetAttributeForVariantId(final Integer variantId, final String name) {
        return ofUnsetAttributeForVariantId(variantId, name, null);
    }

    public static SetAttribute ofUnsetAttributeForVariantId(final Integer variantId, final String name, @Nullable final Boolean staged) {
        return ofVariantId(variantId, name, null, staged);
    }

    /**
     * Action to remove a custom attribute.
     * @param sku
     * @param name the name of the attribute, consult the product type to find the name
     * @return update action
     */
    public static SetAttribute ofUnsetAttributeForSku(final String sku, final String name) {
        return ofUnsetAttributeForSku(sku, name, null);
    }

    public static SetAttribute ofUnsetAttributeForSku(final String sku, final String name, @Nullable final Boolean staged) {
        return ofSku(sku, name, null, staged);
    }

    /**
     * Action to remove a custom attribute.
     *
     * @param variantId the variant the attribute value should be unset
     * @param NamedAttributeAccess object containing the name of the attribute

     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttribute ofUnsetAttribute(final Integer variantId, final NamedAttributeAccess<T> NamedAttributeAccess) {
        return ofUnsetAttribute(variantId, NamedAttributeAccess, null);
    }

    public static <T> SetAttribute ofUnsetAttribute(final Integer variantId, final NamedAttributeAccess<T> NamedAttributeAccess, @Nullable final Boolean staged) {
        return ofVariantId(variantId, NamedAttributeAccess.getName(), null, staged);
    }

    /**
     * Action to remove a custom attribute.
     *
     * @param variantId the variant the attribute value should be unset
     * @param NamedAttributeAccess object containing the name of the attribute

     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttribute ofUnsetAttributeForVariantId(final Integer variantId, final NamedAttributeAccess<T> NamedAttributeAccess) {
        return ofUnsetAttributeForVariantId(variantId, NamedAttributeAccess, null);
    }

    public static <T> SetAttribute ofUnsetAttributeForVariantId(final Integer variantId, final NamedAttributeAccess<T> NamedAttributeAccess, @Nullable final Boolean staged) {
        return ofVariantId(variantId, NamedAttributeAccess.getName(), null, staged);
    }

    /**
     * Action to remove a custom attribute.
     *
     * @param sku
     * @param NamedAttributeAccess object containing the name of the attribute

     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttribute ofUnsetAttributeForSku(final String sku, final NamedAttributeAccess<T> NamedAttributeAccess) {
        return ofUnsetAttributeForSku(sku, NamedAttributeAccess, null);
    }

    public static <T> SetAttribute ofUnsetAttributeForSku(final String sku, final NamedAttributeAccess<T> NamedAttributeAccess, @Nullable final Boolean staged) {
        return ofSku(sku, NamedAttributeAccess.getName(), null, staged);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param attribute the name and the value of the attribute to update
     * @return update action
     */
    public static SetAttribute of(final Integer variantId, final AttributeDraft attribute) {
        return of(variantId, attribute, null);
    }

    public static SetAttribute of(final Integer variantId, final AttributeDraft attribute, @Nullable final Boolean staged) {
        return ofVariantId(variantId, attribute.getName(), attribute.getValue(), staged);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param attribute the name and the value of the attribute to update
     * @return update action
     */
    public static SetAttribute ofVariantId(final Integer variantId, final AttributeDraft attribute) {
        return ofVariantId(variantId, attribute, null);
    }

    public static SetAttribute ofVariantId(final Integer variantId, final AttributeDraft attribute, @Nullable final Boolean staged) {
        return ofVariantId(variantId, attribute.getName(), attribute.getValue(), staged);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param sku
     * @param attribute the name and the value of the attribute to update
     * @return update action
     */
    public static SetAttribute ofSku(final String sku, final AttributeDraft attribute) {
        return ofSku(sku, attribute, null);
    }

    public static SetAttribute ofSku(final String sku, final AttributeDraft attribute, @Nullable final Boolean staged) {
        return ofSku(sku, attribute.getName(), attribute.getValue(), staged);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param setter the serializer of the attribute
     * @param value the value to set
     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttribute of(final Integer variantId, final NamedAttributeAccess<T> setter, final T value) {
        return of(variantId, setter, value, null);
    }

    public static <T> SetAttribute of(final Integer variantId, final NamedAttributeAccess<T> setter, final T value, @Nullable final Boolean staged) {
        return ofVariantId(variantId, AttributeDraft.of(setter, value), staged);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param setter the serializer of the attribute
     * @param value the value to set
     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttribute ofVariantId(final Integer variantId, final NamedAttributeAccess<T> setter, final T value) {
        return ofVariantId(variantId, setter, value, null);
    }

    public static <T> SetAttribute ofVariantId(final Integer variantId, final NamedAttributeAccess<T> setter, final T value, @Nullable final Boolean staged) {
        return ofVariantId(variantId, AttributeDraft.of(setter, value), staged);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param sku
     * @param setter the serializer of the attribute
     * @param value  the value to set
     * @param <T>    type of the attribute
     * @return update action
     */
    public static <T> SetAttribute ofSku(final String sku, final NamedAttributeAccess<T> setter, final T value) {
        return ofSku(sku, setter, value, null);
    }

    public static <T> SetAttribute ofSku(final String sku, final NamedAttributeAccess<T> setter, final T value, @Nullable final Boolean staged) {
        return ofSku(sku, AttributeDraft.of(setter, value), staged);
    }
}
