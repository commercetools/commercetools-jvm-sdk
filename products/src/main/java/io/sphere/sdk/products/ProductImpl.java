package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import org.joda.time.DateTime;

public class ProductImpl extends DefaultModelImpl implements Product {
    private final Reference<ProductType> productType;

    @JsonCreator
    ProductImpl(final String id, final long version, final DateTime createdAt, final DateTime lastModifiedAt, final Reference<ProductType> productType) {
        super(id, version, createdAt, lastModifiedAt);
        this.productType = productType;
    }

    @Override
    public Reference<ProductType> getProductType() {
        return productType;
    }
}
