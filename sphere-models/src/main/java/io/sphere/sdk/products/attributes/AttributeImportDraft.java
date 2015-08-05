package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;

public class AttributeImportDraft extends Base {
    private AttributeImportDraft(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    public static AttributeImportDraft of(final String name, final JsonNode jsonNode) {
        return new AttributeImportDraft(name, jsonNode);
    }

    public static <T> AttributeImportDraft of(final String name, final T value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return of(name, jsonNode);
    }

    private final String name;

    @JsonSerialize
    private final JsonNode value;

    public String getName() {
        return name;
    }
}
