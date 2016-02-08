package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds / Removes / Changes a custom attribute in all variants at the same time (it can be helpful to set attribute values that are constrained with SameForAll).
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setAttributeInAllVariants()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.SetAttribute
 */
public final class SetAttributeInAllVariants extends UpdateActionImpl<Product> {
    private final String name;
    @Nullable
    private final JsonNode value;

    SetAttributeInAllVariants(final String name, final @Nullable JsonNode value) {
        super("setAttributeInAllVariants");
        this.name = name;
        this.value = value;
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
     * @param name the name of the attribute, consult the product type to find the name
     * @param value embedded in an optional the new value of the attribute or an empty Optional to remove the value from the attribute
     * @return update action
     */
    public static SetAttributeInAllVariants of(final String name, final JsonNode value) {
        return new SetAttributeInAllVariants(name, value);
    }

    /**
     * Action to remove a custom attribute.
     *
     * @param name the name of the attribute, consult the product type to find the name
     * @return update action
     */
    public static SetAttributeInAllVariants ofUnsetAttribute(final String name) {
        return of(name, null);
    }


    /**
     * Action to remove a custom attribute.
     *
     * @param NamedAttributeAccess object containing the name of the attribute
     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttributeInAllVariants ofUnsetAttribute(final NamedAttributeAccess<T> NamedAttributeAccess) {
        return of(NamedAttributeAccess.getName(), null);
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param attribute the name and the value of the attribute to update
     * @return update action
     */
    public static SetAttributeInAllVariants of(final AttributeDraft attribute) {
        return of(attribute.getName(), attribute.getValue());
    }

    /**
     * Action to add/change a custom attribute.
     *
     * @param setter the serializer of the attribute
     * @param value the value to set
     * @param <T> type of the attribute
     * @return update action
     */
    public static <T> SetAttributeInAllVariants of(final NamedAttributeAccess<T> setter, final T value) {
        return of(AttributeDraft.of(setter, value));
    }
}
