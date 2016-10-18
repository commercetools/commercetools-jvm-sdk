package io.sphere.sdk.products;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

public class ProductDraftBuilderTest {
    @Test
    public void ofWithAllVariants() {
        final LocalizedString name = randomSlug();
        final LocalizedString slug = randomSlug();
        final List<ProductVariantDraft> allVariants = IntStream.range(0, 9)
                .mapToObj(i -> ProductVariantDraftBuilder.of().sku("sku-" + i).build())
                .collect(toList());
        final Reference<ProductType> productType = ProductType.referenceOfId("product-type-id");
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, slug, allVariants).build();
        assertThat(productDraft.getName()).isEqualTo(name);
        assertThat(productDraft.getSlug()).isEqualTo(slug);
        assertThat(productDraft.getProductType()).isEqualTo(productType);
        assertThat(productDraft.getMasterVariant().getSku()).isEqualTo("sku-0");
        assertThat(productDraft.getVariants().get(3).getSku()).isEqualTo("sku-4");
    }
}