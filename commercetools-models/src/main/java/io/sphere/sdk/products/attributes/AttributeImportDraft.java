package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.json.SphereJsonUtils;

@JsonDeserialize(as = AttributeImportDraftImpl.class)
public interface AttributeImportDraft {
    String getName();

    static AttributeImportDraft of(final String name, final JsonNode jsonNode) {
        return new AttributeImportDraftImpl(name, jsonNode);
    }

    static <T> AttributeImportDraft of(final String name, final T value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return of(name, jsonNode);
    }
}
