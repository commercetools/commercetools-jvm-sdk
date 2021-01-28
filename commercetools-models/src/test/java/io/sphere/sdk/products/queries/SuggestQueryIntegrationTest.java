package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.search.ProductProjectionSearchIntegrationTest;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.search.tokenizer.WhiteSpaceSuggestTokenizer;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.withSuggestProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;

public class SuggestQueryIntegrationTest extends IntegrationTest {
    protected static Product product;
    protected static ProductType productType;

    private static final String TEST_CLASS_NAME = SuggestQueryIntegrationTest.class.getSimpleName();
    private static final String PRODUCT_TYPE_NAME = TEST_CLASS_NAME + "-2";//change the postfix to create new products for lazy initialized tests

    protected static final String SKU1 = PRODUCT_TYPE_NAME + "-sku1";

    @BeforeClass
    public static void setupProducts() {
        productType = client().executeBlocking(ProductTypeQuery.of().byName(randomKey())).head().orElseGet(() -> createProductType());


        final Query<Product> query = ProductQuery.of()
                .withPredicates(product -> product.masterData().staged().masterVariant().sku().isIn(asList(SKU1)));
        final List<Product> products = client().executeBlocking(query).getResults();

        final Function<String, Optional<Product>> findBySku =
                sku -> products.stream().filter(p -> sku.equals(p.getMasterData().getStaged().getMasterVariant().getSku())).findFirst();

        product = findBySku.apply(SKU1).orElseGet(() -> createTestProduct(productType, SKU1));
    }

    private static ProductType createProductType() {
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), PRODUCT_TYPE_NAME, "", Collections.emptyList());
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        return client().executeBlocking(productTypeCreateCommand);
    }

    private static Product createTestProduct(final ProductType productType, final String sku) {
        final SearchKeywords searchKeywords = SearchKeywords.of(
                Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"), SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer", CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
        );
        final ProductDraftBuilder productDraftBuilder = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().sku(sku).build())
                .searchKeywords(searchKeywords);

        return client().executeBlocking(ProductCreateCommand.of(productDraftBuilder.build()));
    }

    @Test
    public void execution() {
        final SearchKeywords searchKeywords = SearchKeywords.of(
                Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                        SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                        CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
        );
        assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

        final SuggestQuery suggestQuery = SuggestQuery.of(LocalizedStringEntry.of(Locale.ENGLISH, "knife"))
                .withStaged(true);

        assertEventually(Duration.ofSeconds(120), Duration.ofMillis(100), () -> {
            final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

            assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH))
                    .matches(suggestionsList -> suggestionsList.stream()
                            .anyMatch(suggestion -> suggestion.getText().equals("Swiss Army Knife")));
        });
    }

    @Test
    public void executionWithCustomTokenizer() {
        final SearchKeywords searchKeywords = SearchKeywords.of(
                Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                        SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                        CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
        );
        assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

        final SuggestQuery suggestQuery = SuggestQuery.of(LocalizedStringEntry.of(Locale.GERMAN, "offiz"))
                .withStaged(true);

        assertEventually(Duration.ofSeconds(120), Duration.ofMillis(100), () -> {
            final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

            assertThat(suggestionResult.getSuggestionsForLocale(Locale.GERMAN))
                    .matches(suggestionsList -> suggestionsList.stream()
                            .anyMatch(suggestion -> suggestion.getText().equals("Schweizer Messer")));
        });
    }

    @Test
    public void suggestionForMultipleLanguages() {
        final SearchKeywords searchKeywords = SearchKeywords.of(
                Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                        SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                        CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
        );
        assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

        final List<LocalizedStringEntry> keywords = asList(
                LocalizedStringEntry.of(Locale.GERMAN, "offiz"),
                LocalizedStringEntry.of(Locale.ENGLISH, "multi")
        );
        final SuggestQuery suggestQuery = SuggestQuery.of(keywords)
                .withStaged(true);

        assertEventually(Duration.ofSeconds(120), Duration.ofMillis(100), () -> {
            final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

            assertThat(suggestionResult.getSuggestionsForLocale(Locale.GERMAN))
                    .matches(suggestionsList -> suggestionsList.stream()
                            .anyMatch(suggestion -> suggestion.getText().equals("Schweizer Messer")));
            assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH))
                    .matches(suggestionsList -> suggestionsList.stream()
                            .anyMatch(suggestion -> suggestion.getText().equals("Multi tool")));
        });
    }

    @Test
    public void fuzzy() {
        final SearchKeywords searchKeywords = SearchKeywords.of(
                Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                        SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                        CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
        );
        assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

        final SuggestQuery suggestQuery = SuggestQuery.of(LocalizedStringEntry.of(Locale.ENGLISH, "knive"))
                .withStaged(true).withFuzzy(true);

        assertEventually(Duration.ofSeconds(120), Duration.ofMillis(100), () -> {
            final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

            assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH))
                    .matches(suggestionsList -> suggestionsList.stream()
                            .anyMatch(suggestion -> suggestion.getText().equals("Swiss Army Knife")));
        });
    }
}
