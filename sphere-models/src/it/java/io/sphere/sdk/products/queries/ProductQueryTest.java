package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.VariantIdentifier;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.*;
import static org.assertj.core.api.Assertions.*;

public class ProductQueryTest extends IntegrationTest {
    @Test
    public void variantIdentifierIsAvailable() throws Exception {
        withProduct(client(), product -> {
            final VariantIdentifier identifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            assertThat(identifier).isEqualTo(VariantIdentifier.of(product.getId(), 1));
        });
    }

    @Test
    public void canExpandItsCategories() throws Exception {
        withProductInCategory(client(), (product, category) -> {
            final Query<Product> query = ProductQuery.of()
                    .withPredicate(ProductQuery.model().id().is(product.getId()))
                    .withExpansionPath(ProductQuery.expansionPath().masterData().staged().categories());
            assertThat(execute(query).head().get().getMasterData().getStaged().getCategories().stream().anyMatch(reference -> reference.getObj().isPresent()))
            .isTrue();
        });
    }
}