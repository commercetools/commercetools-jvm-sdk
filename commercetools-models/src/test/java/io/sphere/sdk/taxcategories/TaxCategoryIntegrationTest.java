package io.sphere.sdk.taxcategories;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.neovisionaries.i18n.CountryCode.DE;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxCategoryIntegrationTest extends IntegrationTest {

    public static final String KEY = "RANDOM_KEY";

    @Before
    @After
    public void setUp() throws Exception {
        client().executeBlocking(TaxCategoryQuery.of().byName("German tax")).getResults()
                .forEach(taxCategory -> client().executeBlocking(TaxCategoryDeleteCommand.of(taxCategory)));
    }

    @Test
    public void testQueryByKey(){
        final TaxCategory taxCategory = createTaxCategory();
        final PagedQueryResult<TaxCategory> taxCategoryPagedQueryResult = client().executeBlocking(TaxCategoryQuery.of().byKey(KEY));
        assertThat(taxCategoryPagedQueryResult.getResults()).containsExactly(taxCategory);
        client().executeBlocking(TaxCategoryDeleteCommand.of(taxCategory));
    }

    private TaxCategory createTaxCategory() {
        final TaxRateDraft taxRate = TaxRateDraft.of("GERMAN default tax", 0.19, false, DE);
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraftBuilder.of("German tax", singletonList(taxRate), "Normal-Steuersatz").key(KEY).build();
        final TaxCategory taxCategory = client().executeBlocking(TaxCategoryCreateCommand.of(taxCategoryDraft));
        return taxCategory;
    }
}
