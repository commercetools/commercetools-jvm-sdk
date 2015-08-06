package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import java.time.ZonedDateTime;
import java.util.List;

class ProductTypeImpl extends ResourceImpl<ProductType> implements ProductType {

    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    @JsonCreator
    ProductTypeImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
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
