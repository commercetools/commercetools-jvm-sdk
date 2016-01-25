package io.sphere.sdk.products.attributes;


import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.Base;

final class AttributeDraftImpl extends Base implements AttributeDraft {
    private final String name;
    private final JsonNode value;

    AttributeDraftImpl(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JsonNode getValue() {
        return value;
    }
}
