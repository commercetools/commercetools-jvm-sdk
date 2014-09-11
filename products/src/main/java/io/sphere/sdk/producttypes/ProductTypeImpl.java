package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.attributes.AttributeDefinition;

import java.time.Instant;
import java.util.List;

class ProductTypeImpl extends DefaultModelImpl<ProductType> implements ProductType {

    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    @JsonCreator
    ProductTypeImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt,
                    final String name, final String description, final List<AttributeDefinition> attributes) {
        super(id, version, createdAt, lastModifiedAt);
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
}
