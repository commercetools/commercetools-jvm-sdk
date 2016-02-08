package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds/Removes/Changes a custom attribute.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setAttribute()}
 *
 * @see SetAttributeInAllVariants
 */
public final class SetAttribute extends UpdateActionImpl<Product> {
    private final Integer variantId;
    private final String name;
    @Nullable
    private final JsonNode value;

    SetAttribute(final Integer variantId, final String name, @Nullable final JsonNode value) {
        super("setAttribute");
        this.variantId = variantId;
        this.name = name;
        this.value = value;
    }

    public Integer getVariantId() {
        return variantId;
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
        return new SetAttribute(variantId, name, value);
    }

    /**
     * Action to remove a custom attribute.
     * @param variantId the variant the attribute value should be unset
     * @param name the name of the attribute, consult the product type to find the name
     * @return update action
     */
    public static SetAttribute ofUnsetAttribute(final Integer variantId, final String name) {
        return of(variantId, name, null);
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
        return of(variantId, NamedAttributeAccess.getName(), null);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param attribute the name and the value of the attribute to update
     * @return update action
     */
    public static SetAttribute of(final Integer variantId, final AttributeDraft attribute) {
        return of(variantId, attribute.getName(), attribute.getValue());
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
        return of(variantId, AttributeDraft.of(setter, value));
    }
}
