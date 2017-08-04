package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommand;
import io.sphere.sdk.taxcategories.commands.updateactions.SetKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxCategoryByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withTaxCategory(client(), taxCategory -> {
            final String key = randomKey();
            final TaxCategory updatedTaxCategory = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategory, SetKey.of(key)));
            final TaxCategory loadedTaxCategory = client().executeBlocking(TaxCategoryByKeyGet.of(updatedTaxCategory.getKey()));
            assertThat(loadedTaxCategory).isEqualTo(updatedTaxCategory);
        });
    }
}