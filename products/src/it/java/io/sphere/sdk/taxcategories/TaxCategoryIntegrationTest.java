package io.sphere.sdk.taxcategories;

import io.sphere.sdk.Database;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteByIdCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.neovisionaries.i18n.CountryCode.DE;
import static java.util.Arrays.asList;

public class TaxCategoryIntegrationTest extends QueryIntegrationTest<TaxCategory> {
    public static final TaxRate GERMAN_DEFAULT_TAX_RATE = TaxRate.of("GERMAN default tax", 0.19, false, DE);

    public static final List<TaxRate> TAX_RATES = asList(GERMAN_DEFAULT_TAX_RATE);

    @BeforeClass
    public static void wipe() throws Exception {
        Database.wipe(client());
    }

    @Override
    protected ClientRequest<TaxCategory> deleteCommand(final TaxCategory item) {
        return new TaxCategoryDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<TaxCategory> newCreateCommandForName(final String name) {
        return new TaxCategoryCreateCommand(NewTaxCategory.of(name, "description", TAX_RATES));
    }

    @Override
    protected String extractName(final TaxCategory instance) {
        return instance.getName();
    }

    @Override
    protected ClientRequest<PagedQueryResult<TaxCategory>> queryRequestForQueryAll() {
        return new TaxCategoryQuery();
    }

    @Override
    protected ClientRequest<PagedQueryResult<TaxCategory>> queryObjectForName(final String name) {
        return new TaxCategoryQuery().byName(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<TaxCategory>> queryObjectForNames(final List<String> names) {
        return new TaxCategoryQuery().withPredicate(TaxCategoryQuery.model().name().isOneOf(names));
    }

    @Test
    public void demoForDeletion() throws Exception {
        final TaxCategory taxCategory = createTaxCategory();
        final TaxCategory deletedTaxCategory = execute(new TaxCategoryDeleteByIdCommand(taxCategory));
    }

    private TaxCategory createTaxCategory() {
        final TaxRate taxRate = TaxRate.of("GERMAN default tax", 0.19, false, DE);
        final NewTaxCategory newTaxCategory = NewTaxCategory.of("German tax", "Normal-Steuersatz", asList(taxRate));
        final TaxCategory taxCategory = execute(new TaxCategoryCreateCommand(newTaxCategory));
        return taxCategory;
    }
}
