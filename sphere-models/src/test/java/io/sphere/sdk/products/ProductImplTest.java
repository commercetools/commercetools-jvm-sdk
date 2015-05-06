package io.sphere.sdk.products;

import io.sphere.sdk.models.DefaultModelBuilder;
import io.sphere.sdk.models.DefaultModelSubclassTest;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import static io.sphere.sdk.test.ReferenceAssert.assertThat;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductImplTest extends DefaultModelSubclassTest<Product> {

    public static final String NAME_1 = "name 1";
    public static final String SLUG_1 = "slug-1";
    public static final String NAME_2 = "name 2";
    public static final String SLUG_2 = "slug-2";
    public static final String PRODUCT_TYPE_ID = "product-type-xyz";
    public static final Reference<ProductType> PRODUCT_TYPE_REFERENCE = ProductType.reference(PRODUCT_TYPE_ID);

    @Override
    public void example1ToStringContainsSubclassAttributes(final String example1String) {
        assertThat(example1String).contains(PRODUCT_TYPE_ID);
    }

    @Override
    protected DefaultModelBuilder<Product> newExample1Builder() {
        final ProductCatalogData masterData = createProductCatalogData(NAME_1, SLUG_1);
        return ProductBuilder.of(PRODUCT_TYPE_REFERENCE, masterData);
    }

    @Override
    protected DefaultModelBuilder<Product> newExample2Builder() {
        final ProductCatalogData masterData = createProductCatalogData(NAME_2, SLUG_2);
        return ProductBuilder.of(PRODUCT_TYPE_REFERENCE, masterData);
    }

    @Override
    public void testSubclassGettersOfExample1(final Product model) {
        assertThat(model.getProductType()).hasId(PRODUCT_TYPE_ID);
    }

    private ProductCatalogData createProductCatalogData(String name, String slug) {
        final ProductVariant masterVariant = ProductVariantBuilder.of(1).build();
        final ProductData currentAndStaged = ProductDataBuilder.of(en(name), en(slug), masterVariant).build();
        return ProductCatalogDataBuilder.ofStaged(currentAndStaged).build();
    }
}
