package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Adds / Removes / Changes a custom attribute in all variants at the same time (it can be helpful to set attribute values that are constrained with SameForAll).
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setAttributeInAllVariants()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.SetAttribute
 */
public class SetAttributeInAllVariants extends StageableProductUpdateAction {
    private final String name;
    private final Optional<JsonNode> value;

    SetAttributeInAllVariants(final String name, final Optional<JsonNode> value, final ProductUpdateScope productUpdateScope) {
        super("setAttributeInAllVariants", productUpdateScope);
        this.name = name;
        this.value = value;
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
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttributeInAllVariants of(final String name, final Optional<JsonNode> value, final ProductUpdateScope productUpdateScope) {
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
        return of(name, Optional.<JsonNode>empty(), productUpdateScope);
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
        return of(NamedAttributeAccess.getName(), Optional.<JsonNode>empty(), productUpdateScope);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param attribute the name and the value of the attribute to update
     * @param productUpdateScope the scope where the attribute should be updated
     * @return update action
     */
    public static SetAttributeInAllVariants of(final Attribute attribute, final ProductUpdateScope productUpdateScope) {
        return of(attribute.getName(), Optional.of(attribute.valueAsJson()), productUpdateScope);
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
        return of(Attribute.of(setter, value), productUpdateScope);
    }
}
