package io.sphere.sdk.products.search;

import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class ScopedPriceSearchIntegrationTest extends IntegrationTest {
    public static final String PRODUCT_TYPE_KEY = ScopedPriceSearchIntegrationTest.class.getSimpleName();
    private static ProductType productType;

    @AfterClass
    public static void delete() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        productType = null;
    }

    @BeforeClass
    public static void createData() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, emptyList());
        productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @Test
    public void filterByCurrentValueCentAmountAndCountry() {
        withProductOfPrices(asList(PriceDraft.of(EURO_20), PriceDraft.of(EURO_30).withCountry(DE)), product -> {
            final ProductProjectionSearch baseSphereRequest = ProductProjectionSearch.ofStaged()
                    .withPriceSelection(PriceSelection.of(EUR).withPriceCountry(DE))
                    .plusQueryFilters(m -> m.id().is(product.getId()));
            final ProductProjectionSearch positiveSearchRequest = baseSphereRequest
                    .plusQueryFilters(m -> m.allVariants().scopedPrice().currentValue()
                            .centAmount().isGreaterThanOrEqualTo(3000L));
            final ProductProjectionSearch negativeSearchRequest = baseSphereRequest
                    .plusQueryFilters(m -> m.allVariants().scopedPrice().currentValue()
                            .centAmount().isGreaterThanOrEqualTo(3001L));
            assertEventually(() -> {
                assertThat(client().executeBlocking(negativeSearchRequest).getTotal()).isEqualTo(0);
                final PagedSearchResult<ProductProjection> result = client().executeBlocking(positiveSearchRequest);
                assertThat(result.getResults()).extracting(ResourceView::getId)
                        .as("product included").contains(product.getId());
                final ProductVariant masterVariant = result.getResults().get(0).getMasterVariant();
                final Price price = masterVariant.getPrice();
                assertThat(price).as("price").isNotNull();
                assertThat(price.getValue()).isEqualTo(EURO_30);
                assertThat(masterVariant.getScopedPrice()).isNotNull();
                assertThat(masterVariant.getScopedPrice().getValue()).as("scopedPrice").isEqualTo(EURO_30);
            });
        });
    }

    private void withProductOfPrices(final List<PriceDraft> priceDrafts, final Consumer<Product> productConsumer) {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .prices(priceDrafts)
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType,  randomSlug(),  randomSlug(), masterVariant)
                .build();
        ProductFixtures.withProduct(client(), () -> productDraft, productConsumer);
    }
}
