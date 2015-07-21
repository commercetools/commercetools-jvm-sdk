package io.sphere.sdk.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    <T> T getValue(final AttributeAccess<T> access);

    static Attribute of(final String name, final JsonNode jsonNode) {
        return new AttributeImpl(name, jsonNode);
    }

    static <T> Attribute of(final String name, final AttributeAccess<T> access, final T value) {
        return of(access.ofName(name), value);
    }

    static <T> Attribute of(final NamedAttributeAccess<T> namedAttributeAccess, final T value) {
        final String name = namedAttributeAccess.getName();
        //here is not the attributeMapper used to keep LocalizedEnum values which
        //are transformed to just the key so the attribute could not be read anymore
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        if (value instanceof Reference && jsonNode instanceof ObjectNode) {
            final Reference<?> reference = (Reference<?>) value;
            if (reference.getObj() != null) {
                ((ObjectNode) jsonNode).replace("obj", SphereJsonUtils.toJsonNode(reference.getObj()));
            }
        }
        return of(name, jsonNode);
    }
}
