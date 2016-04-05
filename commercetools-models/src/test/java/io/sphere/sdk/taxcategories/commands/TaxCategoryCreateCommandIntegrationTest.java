package io.sphere.sdk.taxcategories.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.taxcategories.TaxRateDraft;
import io.sphere.sdk.taxcategories.TaxRateDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxCategoryCreateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void createByJson() {
        final TaxCategoryDraft draft = SphereJsonUtils.readObjectFromResource("drafts-tests/taxCategory.json", TaxCategoryDraft.class);
        withTaxCategory(client(), draft, taxCategory -> {
            assertThat(taxCategory.getName()).isEqualTo("a tax category");
            final TaxRateDraft taxRate = TaxRateDraftBuilder.of(taxCategory.getTaxRates().get(0)).build();
            final TaxRateDraft expectedTaxRate = TaxRateDraftBuilder.of("default-tax", 0.19, true, CountryCode.DE).build();
            assertThat(taxRate).isEqualTo(expectedTaxRate);
        });

    }
}
