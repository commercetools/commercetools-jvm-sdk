package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

public final class AttributeDraftBuilder extends Base implements Builder<AttributeDraft> {
    private String name;
    private JsonNode value;

    private AttributeDraftBuilder(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public AttributeDraft build() {
        return new AttributeDraftImpl(name, value);
    }

    public static AttributeDraftBuilder of(final AttributeDraft template) {
        return new AttributeDraftBuilder(template.getName(), template.getValue());
    }

    public static AttributeDraftBuilder of(final Attribute template) {
        return new AttributeDraftBuilder(template.getName(), template.getValueAsJsonNode());
    }
}
