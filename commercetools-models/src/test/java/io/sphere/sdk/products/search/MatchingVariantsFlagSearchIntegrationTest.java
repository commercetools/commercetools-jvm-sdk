package io.sphere.sdk.products.search;

import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductTypeDraftDsl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withProductType;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class MatchingVariantsFlagSearchIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Ignore
    @Test
    public void disableMatchingVariantsFlag() {
        final String attributeName = randomKey();
        withProductType(client(), () -> productTypeDraft(attributeName), productType -> {
            final List<ProductVariantDraft> allVariants = Stream.of("1", "2", "3", "4")
                    .map(s -> ProductVariantDraftBuilder.of().attributes(AttributeDraft.of(attributeName, s)).build())
                    .collect(Collectors.toList());
            final ProductDraftBuilder productDraftBuilder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), allVariants);
            withProduct(client(), productDraftBuilder, product -> {
                final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                        .withMarkingMatchingVariants(true)
                        .plusQueryFilters(m -> m.id().is(product.getId()))
                        .plusQueryFilters(m -> m.allVariants().attribute().ofString(attributeName).is("3"));
                assertEventually(() -> {
                    final Optional<ProductProjection> productProjectionOptional = client().executeBlocking(searchRequest).head();
                    assertThat(productProjectionOptional).isPresent();
                    final ProductProjection productProjection = productProjectionOptional.get();
                    assertThat(productProjection.getMasterVariant().isMatchingVariant()).isFalse();
                    final ProductVariant variantWithAttribute2 = productProjection.getVariants().get(0);
                    assertThat(variantWithAttribute2.isMatchingVariant()).isFalse();
                    final ProductVariant positiveVariant = productProjection.getVariants().get(1);
                    assertThat(positiveVariant.getAttribute(attributeName).getValueAsString()).isEqualTo("3");
                    assertThat(positiveVariant.isMatchingVariant()).isTrue();
                });
                assertEventually(() -> {
                    final ProductProjectionSearch withoutFlagSearchRequest = searchRequest.withMarkingMatchingVariants(false);
                    final Optional<ProductProjection> productProjectionOptional = client().executeBlocking(withoutFlagSearchRequest).head();
                    assertThat(productProjectionOptional).isPresent();
                    assertThat(productProjectionOptional.get().getMasterVariant().isMatchingVariant()).isNull();
                    assertThat(productProjectionOptional.get().getVariants().get(0).isMatchingVariant()).isNull();
                    final ProductVariant positiveVariant = productProjectionOptional.get().getVariants().get(1);
                    assertThat(positiveVariant.getAttribute(attributeName).getValueAsString()).isEqualTo("3");
                    assertThat(positiveVariant.isMatchingVariant()).isNull();
                });


            });
        });
    }

    private ProductTypeDraft productTypeDraft(final String attributeName) {
        return ProductTypeDraftDsl.of(randomKey(), randomKey(), randomKey(), singletonList(attributeDefinition(attributeName)));
    }

    private AttributeDefinitionDraft attributeDefinition(final String attributeName) {
        AttributeDefinition attributeDefinition = AttributeDefinitionBuilder.of(attributeName, randomSlug(), StringAttributeType.of()).build();
        return AttributeDefinitionDraftBuilder.of(attributeDefinition).build();
    }
}
