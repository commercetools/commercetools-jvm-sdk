package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static org.assertj.core.api.Assertions.*;

public class TaxCategoryByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withTaxCategory(client(), taxCategory -> {
            final TaxCategory loadedTaxCategory = client().executeBlocking(TaxCategoryByIdGet.of(taxCategory.getId()));
            assertThat(loadedTaxCategory).isEqualTo(taxCategory);
        });
    }
}