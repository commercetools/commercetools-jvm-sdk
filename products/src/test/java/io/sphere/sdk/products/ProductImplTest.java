package io.sphere.sdk.products;

import io.sphere.sdk.models.DefaultModelBuilder;
import io.sphere.sdk.models.DefaultModelSubclassTest;
import io.sphere.sdk.producttypes.ProductType;

public class ProductImplTest extends DefaultModelSubclassTest<Product> {
    @Override
    public void toStringContainsSubclassAttributes() {
        //TODO
    }

    @Override
    protected DefaultModelBuilder<Product> newExample1Builder() {
        return ProductBuilder.of(ProductType.reference("product-type-1"));
    }

    @Override
    protected DefaultModelBuilder<Product> newExample2Builder() {
        return ProductBuilder.of(ProductType.reference("product-type-2"));
    }

    @Override
    public void testSubclassGetters() {
        //TODO
    }
}
