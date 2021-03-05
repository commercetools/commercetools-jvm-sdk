package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQueryModel;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

public final class TaxCategoryFixtures {

    public static final String STANDARD_TAX_CATEGORY = "Standard tax category";

    private TaxCategoryFixtures() {
    }

    public static void withTaxCategory(final BlockingSphereClient client, final Consumer<TaxCategory> user) {
        final QueryPredicate<TaxCategory> predicate = TaxCategoryQueryModel.of().name().is(STANDARD_TAX_CATEGORY);
        final List<TaxCategory> results = client.executeBlocking(TaxCategoryQuery.of().withPredicates(predicate)).getResults();
        final TaxCategory taxCategory;
        if (results.isEmpty()) {
            final List<TaxRateDraft> taxRates = asList(TaxRateDraft.of("5% US", 0.05, false, US), TaxRateDraft.of("19% MwSt", 0.19, true, DE));
            taxCategory = client.executeBlocking(TaxCategoryCreateCommand.of(TaxCategoryDraft.of(STANDARD_TAX_CATEGORY, taxRates)));
        } else {
            taxCategory = results.get(0);
        }
        user.accept(taxCategory);
    }

    public static void withTransientTaxCategory(final BlockingSphereClient client, final Consumer<TaxCategory> user) {
        withTaxCategory(client, randomString(), user);
    }

    public static TaxCategory defaultTaxCategory(final BlockingSphereClient client) {
        final String name = "sdk-default-tax-cat-1";
        final TaxCategoryDraft categoryDraft = TaxCategoryDraft.of(name, singletonList(TaxRateDraft.of("xyz", 0.20, true, DE)));
        return client.executeBlocking(TaxCategoryQuery.of().byName(name)).head().orElseGet(() -> {
            try {
                return client.executeBlocking(TaxCategoryCreateCommand.of(categoryDraft));
            } catch (ErrorResponseException e) {
                return client.executeBlocking(TaxCategoryQuery.of().byName(name)).head().orElseThrow(() -> e);
            }
        });
    }

    public static void withTaxCategory(final BlockingSphereClient client, final String name, final Consumer<TaxCategory> user) {
        final TaxCategoryDraft de19 = TaxCategoryDraft.of(name, singletonList(TaxRateDraftBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        withTaxCategory(client, de19, user);
    }

    public static void withTaxCategory(final BlockingSphereClient client, final TaxCategoryDraft draft, final Consumer<TaxCategory> user) {
        requireNonNull(draft, "draft should not be null");
        final PagedQueryResult<TaxCategory> results = client.executeBlocking(TaxCategoryQuery.of().byName(draft.getName()));
        results.getResults().forEach(tc -> client.executeBlocking(TaxCategoryDeleteCommand.of(tc)));
        final TaxCategory taxCategory = client.executeBlocking(TaxCategoryCreateCommand.of(draft));
        user.accept(taxCategory);
        client.executeBlocking(TaxCategoryDeleteCommand.of(taxCategory));
    }

    public static void withUpdateableTaxCategory(final BlockingSphereClient client, final UnaryOperator<TaxCategory> testApplicationFunction) {
        final TaxCategoryDraft draft = TaxCategoryDraft.of(randomKey(), singletonList(TaxRateDraftBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        final PagedQueryResult<TaxCategory> results = client.executeBlocking(TaxCategoryQuery.of().byName(draft.getName()));
        results.getResults().forEach(tc -> client.executeBlocking(TaxCategoryDeleteCommand.of(tc)));
        final TaxCategory taxCategory = client.executeBlocking(TaxCategoryCreateCommand.of(draft));
        final TaxCategory updated = testApplicationFunction.apply(taxCategory);
        client.executeBlocking(TaxCategoryDeleteCommand.of(updated));
    }
}
