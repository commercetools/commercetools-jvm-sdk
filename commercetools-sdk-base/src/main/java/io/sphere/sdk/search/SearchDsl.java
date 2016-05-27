package io.sphere.sdk.search;

import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.Locale;

public interface SearchDsl<T, C extends SearchDsl<T, C>> extends ResourceSearch<T> {

    /**
     * Returns a new object with the new text as search text.
     * @param text the new search text with locale, the locale should be enabled for the commercetools platform project (see the project settings in the Merchant Center)
     * @return a new object with {@code text}
     */
    C withText(final LocalizedStringEntry text);

    /**
     * Returns a new object with the new text as search text.
     * @param locale the new locale, the locale should be enabled for the commercetools platform project (see the project settings in the Merchant Center)
     * @param text the new search text
     * @return a new object with {@code text}
     */
    C withText(final Locale locale, final String text);

    /**
     * Returns an ResourceSearch with modified fuzzy parameter.
     * @param fuzzy a flag to indicate if fuzzy search is enabled (true) or not (false)
     * @return an ResourceSearch with the new fuzzy flag setting
     */
    C withFuzzy(final Boolean fuzzy);

    /**
     * Returns an ResourceSearch with modified fuzzyLevel parameter.
     *
     * {@include.example io.sphere.sdk.products.search.FuzzyLevelIntegrationTest#fuzzyLevel()}
     *
     * @param fuzzyLevel defines level of unsharpness of the search using the Damerau-Levenshtein distance
     * @return an ResourceSearch with the new fuzzyLevel setting
     */
    C withFuzzyLevel(final Integer fuzzyLevel);

    /**
     * Returns a new object with the new limit.
     * @param limit the new limit
     * @return a new object with {@code limit}
     */
    C withLimit(final Long limit);

    /**
     * Returns a new object with the new limit.
     * @param limit the new limit
     * @return a new object with {@code limit}
     */
    default C withLimit(final long limit) {
        return withLimit(Long.valueOf(limit));
    }

    /**
     * Returns a new object with the new offset.
     * @param offset the new offset
     * @return a new object with {@code offset}
     */
    C withOffset(final Long offset);

    /**
     * Returns a new object with the new offset.
     * @param offset the new offset
     * @return a new object with {@code offset}
     */
    default C withOffset(final long offset) {
        return withOffset(Long.valueOf(offset));
    }

}
