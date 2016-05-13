package io.sphere.sdk.taxcategories;

import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static com.neovisionaries.i18n.CountryCode.DE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class TaxCategoryIntegrationTest extends IntegrationTest {

    @After
    @Before
    public void setUp() throws Exception {
        client().executeBlocking(TaxCategoryQuery.of().byName("German tax")).getResults()
                .forEach(taxCategory -> client().executeBlocking(TaxCategoryDeleteCommand.of(taxCategory)));
    }

    @Test
    public void demoForDeletion() throws Exception {
        final TaxCategory taxCategory = createTaxCategory();
        final TaxCategory deletedTaxCategory = client().executeBlocking(TaxCategoryDeleteCommand.of(taxCategory));
    }

    private TaxCategory createTaxCategory() {
        final TaxRateDraft taxRate = TaxRateDraft.of("GERMAN default tax", 0.19, false, DE);
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraft.of("German tax", singletonList(taxRate), "Normal-Steuersatz");
        final TaxCategory taxCategory = client().executeBlocking(TaxCategoryCreateCommand.of(taxCategoryDraft));
        return taxCategory;
    }
}
