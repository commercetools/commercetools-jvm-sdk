package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.DefaultModelImpl;
import org.joda.time.DateTime;

import java.util.List;

public class ProductTypeImpl extends DefaultModelImpl implements ProductType {

    public ProductTypeImpl(final String id, final long version, final DateTime createdAt, final DateTime lastModifiedAt) {
        super(id, version, createdAt, lastModifiedAt);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<AttributeDefinition> getAttributes() {
        return null;
    }
}
