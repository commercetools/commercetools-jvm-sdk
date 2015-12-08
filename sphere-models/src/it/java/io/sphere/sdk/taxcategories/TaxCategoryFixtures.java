package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQueryModel;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static java.util.Arrays.asList;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Objects.requireNonNull;

public final class TaxCategoryFixtures {

    public static final String STANDARD_TAX_CATEGORY = "Standard tax category";

    private TaxCategoryFixtures() {
    }

    public static void withTaxCategory(final TestClient client, final Consumer<TaxCategory> user) {
        final QueryPredicate<TaxCategory> predicate = TaxCategoryQueryModel.of().name().is(STANDARD_TAX_CATEGORY);
        final List<TaxCategory> results = client.execute(TaxCategoryQuery.of().withPredicates(predicate)).getResults();
        final TaxCategory taxCategory;
        if (results.isEmpty()) {
            final List<TaxRate> taxRates = asList(TaxRate.of("5% US", 0.05, false, US), TaxRate.of("19% MwSt", 0.19, true, DE));
            taxCategory = client.execute(TaxCategoryCreateCommand.of(TaxCategoryDraft.of(STANDARD_TAX_CATEGORY, taxRates)));
        } else {
            taxCategory = results.get(0);
        }
        user.accept(taxCategory);
    }

    public static void withTransientTaxCategory(final TestClient client, final Consumer<TaxCategory> user) {
        withTaxCategory(client, randomString(), user);
    }

    public static TaxCategory defaultTaxCategory(final TestClient client) {
        final String name = "sdk-default-tax-cat-1";
        final TaxCategoryDraft categoryDraft = TaxCategoryDraft.of(name, asList(TaxRate.of("xyz", 0.20, true, DE)));
        return client.execute(TaxCategoryQuery.of().byName(name)).head().orElseGet(() -> client.execute(TaxCategoryCreateCommand.of(categoryDraft)));
    }

    public static void withTaxCategory(final TestClient client, final String name, final Consumer<TaxCategory> user) {
        final TaxCategoryDraft de19 = TaxCategoryDraft.of(name, asList(TaxRateBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        withTaxCategory(client, de19, user);
    }

    public static void withTaxCategory(final TestClient client, final TaxCategoryDraft draft, final Consumer<TaxCategory> user) {
        requireNonNull(draft, "draft should not be null");
        final PagedQueryResult<TaxCategory> results = client.execute(TaxCategoryQuery.of().byName(draft.getName()));
        results.getResults().forEach(tc -> client.execute(TaxCategoryDeleteCommand.of(tc)));
        final TaxCategory taxCategory = client.execute(TaxCategoryCreateCommand.of(draft));
        user.accept(taxCategory);
        client.execute(TaxCategoryDeleteCommand.of(taxCategory));
    }

    public static void withUpdateableTaxCategory(final TestClient client, final UnaryOperator<TaxCategory> testApplicationFunction) {
        final TaxCategoryDraft draft = TaxCategoryDraft.of(randomKey(), asList(TaxRateBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        final PagedQueryResult<TaxCategory> results = client.execute(TaxCategoryQuery.of().byName(draft.getName()));
        results.getResults().forEach(tc -> client.execute(TaxCategoryDeleteCommand.of(tc)));
        final TaxCategory taxCategory = client.execute(TaxCategoryCreateCommand.of(draft));
        final TaxCategory updated = testApplicationFunction.apply(taxCategory);
        client.execute(TaxCategoryDeleteCommand.of(updated));
    }
}
