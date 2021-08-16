package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.WithKey;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Input object to create a product attribute.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddVariant
 * @see io.sphere.sdk.products.ProductVariantDraft
 */
@JsonDeserialize(as = AttributeDraftImpl.class)
public interface AttributeDraft {
    String getName();

    JsonNode getValue();

    static <T> AttributeDraft of(final String name, final T value) {
        final AttributeDraft result;
        if (value instanceof LocalizedEnumValue) {
            result = of(name, ((LocalizedEnumValue) value).getKey());
        } else if (value instanceof EnumValue) {
            result = of(name, ((EnumValue) value).getKey());
        } else if (value instanceof Set) {
            final Set<?> set = (Set<?>) value;
            if (!set.isEmpty()) {
                final Object setValue = set.stream().findAny().get();
                if (setValue instanceof LocalizedEnumValue || setValue instanceof EnumValue) {
                    //WithKey is a interface the enum like implement
                    final Set<String> newValues = set.stream().map(x -> ((WithKey) x).getKey()).collect(Collectors.toCollection(LinkedHashSet::new));
                    result = of(name, newValues);
                } else {
                    result = of(name, SphereJsonUtils.toJsonNode(value));
                }
            } else {
                result = of(name, SphereJsonUtils.toJsonNode(value));
            }
        } else {
            result = of(name, SphereJsonUtils.toJsonNode(value));
        }
        return result;
    }

    static AttributeDraft of(final String name, final JsonNode value) {
        return new AttributeDraftImpl(name, value);
    }

    static <T> AttributeDraft of(final String name, final AttributeAccess<T> access, final T value) {
        return of(access.ofName(name), value);
    }

    static <T> AttributeDraft of(final NamedAttributeAccess<T> namedAccess, final T value) {
        final JsonNode jsonNode = namedAccess.attributeMapper().serialize(value);
        return of(namedAccess.getName(), jsonNode);
    }
}
