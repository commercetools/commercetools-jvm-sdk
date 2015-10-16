package io.sphere.sdk.search;

import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.Locale;

public interface SearchDsl<T, C extends SearchDsl<T, C>> extends EntitySearch<T> {

    /**
     * Returns a new object with the new text as search text.
     * @param text the new search text with locale
     * @return a new object with {@code text}
     */
    C withText(final LocalizedStringEntry text);

    /**
     * Returns a new object with the new text as search text.
     * @param locale the new locale
     * @param text the new search text
     * @return a new object with {@code text}
     */
    C withText(final Locale locale, final String text);

    /**
     * Returns an EntitySearch with mofified fuzzyParameter.
     * @param fuzzy a flag to indicate if fuzzy search is enabled (true) or not (false)
     * @return an EntitySearch with the new fuzzy flag setting
     */
    C withFuzzy(final Boolean fuzzy);

    /**
     * Returns a new object with the new limit.
     * @param limit the new limit
     * @return a new object with {@code limit}
     */
    C withLimit(final long limit);

    /**
     * Returns a new object with the new offset.
     * @param offset the new offset
     * @return a new object with {@code offset}
     */
    C withOffset(final long offset);

}
