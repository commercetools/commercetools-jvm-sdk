package io.sphere.sdk.taxcategories;

import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteByIdCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static com.neovisionaries.i18n.CountryCode.DE;
import static java.util.Arrays.asList;

public class TaxCategoryIntegrationTest extends IntegrationTest {

    @Test
    public void demoForDeletion() throws Exception {
        final TaxCategory taxCategory = createTaxCategory();
        final TaxCategory deletedTaxCategory = execute(TaxCategoryDeleteByIdCommand.of(taxCategory));
    }

    private TaxCategory createTaxCategory() {
        final TaxRate taxRate = TaxRate.of("GERMAN default tax", 0.19, false, DE);
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraft.of("German tax", "Normal-Steuersatz", asList(taxRate));
        final TaxCategory taxCategory = execute(TaxCategoryCreateCommand.of(taxCategoryDraft));
        return taxCategory;
    }
}
