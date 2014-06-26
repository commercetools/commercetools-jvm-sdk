package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.DefaultModelImpl;
import org.joda.time.DateTime;

import java.util.List;

public class ProductTypeImpl extends DefaultModelImpl implements ProductType {

    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    ProductTypeImpl(final String id, final long version, final DateTime createdAt, final DateTime lastModifiedAt,
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

    public static TypeReference<ProductTypeImpl> typeReference() {
        return new TypeReference<ProductTypeImpl>(){
            @Override
            public String toString() {
                return "TypeReference<ProductTypeImpl>";
            }
        };
    }
}
