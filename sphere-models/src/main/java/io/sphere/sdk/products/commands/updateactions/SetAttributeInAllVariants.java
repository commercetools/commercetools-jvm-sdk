package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.ProductUpdateScope;

import javax.annotation.Nullable;

/**
 * Adds / Removes / Changes a custom attribute in all variants at the same time (it can be helpful to set attribute values that are constrained with SameForAll).
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setAttributeInAllVariants()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.SetAttribute
 */
public class SetAttributeInAllVariants extends StageableProductUpdateAction {
    private final String name;
    @Nullable
    private final JsonNode value;

    SetAttributeInAllVariants(final String name, final @Nullable JsonNode value, final ProductUpdateScope productUpdateScope) {
        super("setAttributeInAllVariants", productUpdateScope);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public JsonNode getValue() {
        return value;
    }

    /**
     * Action to add/remove/change a custom attribute.
     *
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttributeInAllVariants of(final String name, final JsonNode value, final ProductUpdateScope productUpdateScope) {
        return new SetAttributeInAllVariants(name, value, productUpdateScope);
    }

    /**
     * Action to remove a custom attribute.
     *
     * @param name the name of the attribute, consult the product type to find the name
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttributeInAllVariants ofUnsetAttribute(final String name, final ProductUpdateScope productUpdateScope) {
        return of(name, null, productUpdateScope);
    }


    /**
     * Action to remove a custom attribute.
     *
     * @param NamedAttributeAccess object containing the name of the attribute
     * @param productUpdateScope the scope where the attribute should be updated
     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttributeInAllVariants ofUnsetAttribute(final NamedAttributeAccess<T> NamedAttributeAccess, final ProductUpdateScope productUpdateScope) {
        return of(NamedAttributeAccess.getName(), null, productUpdateScope);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param attribute the name and the value of the attribute to update
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttributeInAllVariants of(final AttributeDraft attribute, final ProductUpdateScope productUpdateScope) {
        return of(attribute.getName(), attribute.getValue(), productUpdateScope);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param setter the serializer of the attribute
     * @param value the value to set
     * @param productUpdateScope the scope where the attribute should be updated
     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttributeInAllVariants of(final NamedAttributeAccess<T> setter, final T value, final ProductUpdateScope productUpdateScope) {
        return of(AttributeDraft.of(setter, value), productUpdateScope);
    }
}
