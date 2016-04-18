package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.SuggestionResult;

import java.util.List;

import static java.util.Collections.singletonList;


/**
 * Query search keywords to implement a basic auto-complete functionality.
 *
 * {@include.example io.sphere.sdk.products.queries.SuggestQueryIntegrationTest#suggestionForMultipleLanguages()}
 */
public interface SuggestQuery extends SphereRequest<SuggestionResult> {

    static SuggestQuery of(final List<LocalizedStringEntry> searchKeywords) {
        return new SuggestQueryImpl(searchKeywords);
    }

    static SuggestQuery of(final LocalizedStringEntry searchKeyword) {
        return of(singletonList(searchKeyword));
    }

    SuggestQuery withLimit(final Integer limit);

    SuggestQuery withStaged(final boolean staged);

    SuggestQuery withFuzzy(final boolean fuzzy);
}
