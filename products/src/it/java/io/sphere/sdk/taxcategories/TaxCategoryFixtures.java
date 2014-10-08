package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteByIdCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;

import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static io.sphere.sdk.test.SphereTestUtils.*;

public final class TaxCategoryFixtures {
    private TaxCategoryFixtures() {
    }

    public static void withTaxCategory(final TestClient client, final Consumer<TaxCategory> user) {
        withTaxCategory(client, randomString(), user);
    }

    public static void withTaxCategory(final TestClient client, final String name, final Consumer<TaxCategory> user) {
        final NewTaxCategory de19 = NewTaxCategory.of(name, asList(TaxRateBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        final PagedQueryResult<TaxCategory> results = client.execute(new TaxCategoryQuery().byName(name));
        results.getResults().forEach(tc -> client.execute(new TaxCategoryDeleteByIdCommand(tc)));
        final TaxCategory taxCategory = client.execute(new TaxCategoryCreateCommand(de19));
        user.accept(taxCategory);
        client.execute(new TaxCategoryDeleteByIdCommand(taxCategory));
    }
}
