package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

class ProductTypeImpl extends ResourceImpl<ProductType> implements ProductType {

    private final String name;
    @Nullable
    private final String key;
    private final String description;
    private final List<AttributeDefinition> attributes;

    @JsonCreator
    ProductTypeImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                    final String name, @Nullable final String key, final String description, final List<AttributeDefinition> attributes) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.key = key;
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
