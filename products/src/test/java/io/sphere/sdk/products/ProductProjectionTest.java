package io.sphere.sdk.products;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ProductProjectionTest {
    @Test
    public void transformProductIntoProductProjection() throws Exception {
        final Product product = getProduct();

        final Optional<ProductProjection> staged = product.toProjection(ProductProjectionType.STAGED);
        assertThat(staged).overridingErrorMessage("staged is always present").isPresent();
        assertThat(staged.get().getName()).isEqualTo(product.getMasterData().getStaged().getName());

        final Optional<ProductProjection> current = product.toProjection(ProductProjectionType.CURRENT);
        assertThat(current).overridingErrorMessage("current can be empty").isAbsent();
    }

    private Product getProduct() {
        final ProductType productType = ProductTypeBuilder.of("product-type-id", "product-type-name", "", Collections.emptyList()).get();
        final ProductVariant emptyProductVariant = ProductVariantBuilder.of(1).sku("sku-5000").get();
        final LocalizedString name = LocalizedString.of(ENGLISH, "name");
        final LocalizedString slug = LocalizedString.of(ENGLISH, "slug");
        final ProductData staged = ProductDataBuilder.of(name, slug, emptyProductVariant).build();
        final ProductCatalogData masterData = ProductCatalogDataBuilder.ofStaged(staged).get();

        return ProductBuilder.of(productType, masterData).id("foo-id").build();
    }
}