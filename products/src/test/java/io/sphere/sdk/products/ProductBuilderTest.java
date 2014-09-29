package io.sphere.sdk.products;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import org.junit.Test;

import java.util.Collections;

import static io.sphere.sdk.test.LocalizedStringAssert.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;
import static java.util.Locale.ENGLISH;

public class ProductBuilderTest {
    @Test
    public void demoUsage() throws Exception {
        final ProductType productType = getProductType();
        final ProductVariant emptyProductVariant = ProductVariantBuilder.of(1).sku("sku-5000").get();
        final LocalizedString name = LocalizedString.of(ENGLISH, "name");
        final LocalizedString slug = LocalizedString.of(ENGLISH, "slug");
        final ProductData staged = ProductDataBuilder.of(name, slug, emptyProductVariant).build();
        final ProductCatalogData masterData = ProductCatalogDataBuilder.ofStaged(staged).get();

        final Product product = ProductBuilder.of(productType, masterData).id("foo-id").build();
        assertThat(product.getId()).isEqualTo("foo-id");
        final ProductData productData = product.getMasterData().getStaged();
        assertThat(productData.getName()).isEqualTo(name);
        assertThat(productData.getMasterVariant().getSku()).isPresentAs("sku-5000");
    }

    private ProductType getProductType() {
        return ProductTypeBuilder.of("product-type-id", "product-type-name", "", Collections.emptyList()).get();
    }
}