package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeSetter;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Adds/Removes/Changes a custom attribute.
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setAttribute()}
 */
public class SetAttribute extends StageableProductUpdateAction {
    private final int variantId;
    private final String name;
    private final Optional<JsonNode> value;

    SetAttribute(final int variantId, final String name, final Optional<JsonNode> value, final ProductUpdateScope productUpdateScope) {
        super("setAttribute", productUpdateScope);
        this.variantId = variantId;
        this.name = name;
        this.value = value;
    }

    public int getVariantId() {
        return variantId;
    }

    public String getName() {
        return name;
    }

    public Optional<JsonNode> getValue() {
        return value;
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttribute of(final int variantId, final String name, final Optional<JsonNode> value, final ProductUpdateScope productUpdateScope) {
        return new SetAttribute(variantId, name, value, productUpdateScope);
    }

    /**
     * Action to remove a custom attribute.
     *
     * @param variantId the variant the attribute value should be unset
     * @param name the name of the attribute, consult the product type to find the name
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttribute ofUnsetAttribute(final int variantId, final String name, final ProductUpdateScope productUpdateScope) {
        return of(variantId, name, Optional.<JsonNode>empty(), productUpdateScope);
    }


    /**
     * Action to remove a custom attribute.
     *
     * @param variantId the variant the attribute value should be unset
     * @param attributeSetter object containing the name of the attribute
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static <M, T> SetAttribute ofUnsetAttribute(final int variantId, final AttributeSetter<M, T> attributeSetter, final ProductUpdateScope productUpdateScope) {
        return of(variantId, attributeSetter.getName(), Optional.<JsonNode>empty(), productUpdateScope);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param attribute the name and the value of the attribute to update
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttribute of(final int variantId, final Attribute attribute, final ProductUpdateScope productUpdateScope) {
        return of(variantId, attribute.getName(), Optional.of(attribute.valueAsJson()), productUpdateScope);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param variantId the variant the attribute value should be set or unset
     * @param setter the serializer of the attribute
     * @param value the value to set
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static <M, T> SetAttribute of(final int variantId, final AttributeSetter<M, T> setter, final T value, final ProductUpdateScope productUpdateScope) {
        return of(variantId, Attribute.of(setter, value), productUpdateScope);
    }
}
