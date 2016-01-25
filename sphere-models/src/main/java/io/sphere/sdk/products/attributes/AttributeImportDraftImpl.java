package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.models.Base;

final class AttributeImportDraftImpl extends Base implements AttributeImportDraft {
    private final String name;

    AttributeImportDraftImpl(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    @JsonSerialize
    private final JsonNode value;

    @Override
    public String getName() {
        return name;
    }
}
