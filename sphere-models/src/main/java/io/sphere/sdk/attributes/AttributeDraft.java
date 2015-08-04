package io.sphere.sdk.attributes;


import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.WithKey;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AttributeDraft extends Base {
    private final String name;
    private final JsonNode value;

    private AttributeDraft(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    public static <T> AttributeDraft of(final String name, final T value) {
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
                    final Set<String> newValues = set.stream().map(x -> ((WithKey) x).getKey()).collect(toSet());
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

    public static AttributeDraft of(final String name, final JsonNode value) {
        return new AttributeDraft(name, value);
    }

    public static <T> AttributeDraft of(final String name, final AttributeAccess<T> access, final T value) {
        return of(access.ofName(name), value);
    }

    public static <T> AttributeDraft of(final NamedAttributeAccess<T> namedAccess, final T value) {
        final JsonNode jsonNode = namedAccess.attributeMapper().serialize(value);
        return of(namedAccess.getName(), jsonNode);
    }

    public String getName() {
        return name;
    }

    public JsonNode getValue() {
        return value;
    }
}
