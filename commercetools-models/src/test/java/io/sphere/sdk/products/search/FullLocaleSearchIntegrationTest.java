package io.sphere.sdk.products.search;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class FullLocaleSearchIntegrationTest extends IntegrationTest {
    @Ignore
    @Test
    public void searchByFullLocale() {
        final Locale austria = Locale.forLanguageTag("de-AT");
        final String marille = "Marille";
        final LocalizedString apricot = LocalizedString
                .of(GERMAN, "Aprikose")
                .plus(austria, marille)
                .plus(ENGLISH, "apricot");
        withProduct(client(), productBuilder -> productBuilder.name(apricot), product -> {
            final ProductProjectionSearch austrianSearchRequest = ProductProjectionSearch.ofStaged()
                    .withText(austria, marille)
                    .plusQueryFilters(productModel -> productModel.id().is(product.getId()));
            final ProductProjectionSearch germanSearchRequest = austrianSearchRequest.withText(GERMAN, marille);
            assertEventually(() -> {
                final List<ProductProjection> results = client().executeBlocking(austrianSearchRequest).getResults();
                assertThat(results).isNotEmpty();
                assertThat(results.get(0).getId()).isEqualTo(product.getId());

                assertThat(client().executeBlocking(germanSearchRequest).head())
                        .as("word is for austria defined and should not be found for just 'de'")
                        .isEmpty();
            });
        });
    }
}
