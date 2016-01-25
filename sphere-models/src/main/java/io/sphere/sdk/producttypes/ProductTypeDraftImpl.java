package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import javax.annotation.Nullable;
import java.util.List;

final class ProductTypeDraftImpl extends Base implements ProductTypeDraft {
    @Nullable
    private final String key;
    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    @JsonCreator
    ProductTypeDraftImpl(@Nullable final String key, final String name, final String description, final List<AttributeDefinition> attributes) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }
}
