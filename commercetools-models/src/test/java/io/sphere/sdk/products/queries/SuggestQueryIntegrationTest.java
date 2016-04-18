package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.SuggestionResult;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.search.tokenizer.WhiteSpaceSuggestTokenizer;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.products.ProductFixtures.withSuggestProduct;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;

public class SuggestQueryIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withSuggestProduct(client(), product -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(
                    Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                            SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                    Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                            CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
            );
            assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

            final SuggestQuery suggestQuery = SuggestQuery.of(LocalizedStringEntry.of(Locale.ENGLISH, "knife"))
                    .withStaged(true);

            assertEventually(() -> {
                final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

                assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH))
                        .matches(suggestionsList -> suggestionsList.stream()
                                .anyMatch(suggestion -> suggestion.getText().equals("Swiss Army Knife")));
            });
        });
    }

    @Test
    public void executionWithCustomTokenizer() {
        withSuggestProduct(client(), product -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(
                    Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                            SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                    Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                            CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
            );
            assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

            final SuggestQuery suggestQuery = SuggestQuery.of(LocalizedStringEntry.of(Locale.GERMAN, "offiz"))
                    .withStaged(true);

            assertEventually(() -> {
                final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

                assertThat(suggestionResult.getSuggestionsForLocale(Locale.GERMAN))
                        .matches(suggestionsList -> suggestionsList.stream()
                                .anyMatch(suggestion -> suggestion.getText().equals("Schweizer Messer")));
            });
        });
    }

    @Test
    public void suggestionForMultipleLanguages() {
        withSuggestProduct(client(), product -> {
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

            assertEventually(() -> {
                final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

                assertThat(suggestionResult.getSuggestionsForLocale(Locale.GERMAN))
                        .matches(suggestionsList -> suggestionsList.stream()
                                .anyMatch(suggestion -> suggestion.getText().equals("Schweizer Messer")));
                assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH))
                        .matches(suggestionsList -> suggestionsList.stream()
                                .anyMatch(suggestion -> suggestion.getText().equals("Multi tool")));
            });
        });
    }

    @Test
    public void fuzzy() {
        withSuggestProduct(client(), product -> {
            final SearchKeywords searchKeywords = SearchKeywords.of(
                    Locale.ENGLISH, asList(SearchKeyword.of("Multi tool"),
                            SearchKeyword.of("Swiss Army Knife", WhiteSpaceSuggestTokenizer.of())),
                    Locale.GERMAN, singletonList(SearchKeyword.of("Schweizer Messer",
                            CustomSuggestTokenizer.of(asList("schweizer messer", "offiziersmesser", "sackmesser"))))
            );
            assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);

            final SuggestQuery suggestQuery = SuggestQuery.of(LocalizedStringEntry.of(Locale.ENGLISH, "knive"))
                    .withStaged(true).withFuzzy(true);

            assertEventually(() -> {
                final SuggestionResult suggestionResult = client().executeBlocking(suggestQuery);

                assertThat(suggestionResult.getSuggestionsForLocale(Locale.ENGLISH))
                        .matches(suggestionsList -> suggestionsList.stream()
                                .anyMatch(suggestion -> suggestion.getText().equals("Swiss Army Knife")));
            });
        });
    }
}