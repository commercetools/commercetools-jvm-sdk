import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.requests.ClientRequest;
import io.sphere.sdk.taxcategories.*;

import java.util.Arrays;
import java.util.List;

import static com.neovisionaries.i18n.CountryCode.DE;

public class TaxCategoryIntegrationTest extends QueryIntegrationTest<TaxCategory> {
    public static final TaxRate GERMAN_DEFAULT_TAX_RATE = TaxRateBuilder.of("GERMAN default tax", 0.19, false, DE).build();
    public static final List<TaxRate> TAX_RATES = Arrays.asList(GERMAN_DEFAULT_TAX_RATE);


    @Override
    protected ClientRequest<TaxCategory> deleteCommand(final Versioned item) {
        return new TaxCategoryDeleteCommand(item);
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
        return TaxCategory.query();
    }

    @Override
    protected ClientRequest<PagedQueryResult<TaxCategory>> queryObjectForName(final String name) {
        return TaxCategory.query().byName(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<TaxCategory>> queryObjectForNames(final List<String> names) {
        return TaxCategory.query().withPredicate(TaxCategoryQueryModel.get().name().isOneOf(names));
    }
}
