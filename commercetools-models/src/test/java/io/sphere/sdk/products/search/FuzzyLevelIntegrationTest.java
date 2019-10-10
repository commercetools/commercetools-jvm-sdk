package io.sphere.sdk.products.search;

import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static io.sphere.sdk.models.LocalizedString.ofEnglish;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Locale.ENGLISH;

@Ignore
public class FuzzyLevelIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void setUp() throws Exception {
        CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts(client());
        ProductFixtures.deleteProductsAndProductTypes(client());
    }

    @Ignore
    @Test
    public void fuzzyLevel() {
        final String searchWord = "abcdfgh";
        final String productName = "abcdefgh";
        withProduct(client(), builder -> builder.name(ofEnglish(productName)), product -> {
            final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                    .withText(ENGLISH, searchWord)
                    .withFuzzy(true);

            assertEventually(() -> {
                softAssert(s -> {
                    s.assertThat(client().executeBlocking(request.withFuzzyLevel(0)))
                            .as("level 0 matches not")
                            .doesNotHave(product());
                    s.assertThat(client().executeBlocking(request.withFuzzyLevel(1)))
                            .as("level 1 matches")
                            .has(product());
                    s.assertThat(client().executeBlocking(request.withFuzzyLevel(2)))
                            .as("level 2 matches")
                            .has(product());
                });
            });
        });
    }

    private Condition<PagedSearchResult<ProductProjection>> product() {
        return new Condition<PagedSearchResult<ProductProjection>>() {
            @Override
            public boolean matches(final PagedSearchResult<ProductProjection> value) {
                return value.getTotal() > 0;
            }
        };
    }
}
