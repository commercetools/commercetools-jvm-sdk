package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.SuggestionResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;


final class SuggestQueryImpl extends Base implements SuggestQuery {
    private List<LocalizedStringEntry> searchKeywords;
    @Nullable
    private final Integer limit;
    @Nullable
    private final Boolean staged;

    public SuggestQueryImpl(final List<LocalizedStringEntry> searchKeywords) {
        this(searchKeywords, null, null);
    }

    private SuggestQueryImpl(final List<LocalizedStringEntry> searchKeywords, @Nullable final Integer limit, @Nullable final Boolean staged) {
        this.searchKeywords = searchKeywords;
        this.limit = limit;
        this.staged = staged;
    }

    @Override
    public SuggestQuery withLimit(final Integer limit) {
        return new SuggestQueryImpl(searchKeywords, limit, staged);
    }

    @Override
    public SuggestQuery withStaged(final boolean staged) {
        return new SuggestQueryImpl(searchKeywords, limit, staged);
    }

    @Override
    public SuggestionResult deserialize(final HttpResponse httpResponse) {
        return SphereClientUtils.deserialize(httpResponse, new TypeReference<SuggestionResult>() {
            @Override
            public String toString() {
                return "TypeReference<SuggestionResult>";
            }
        });
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, "/product-projections/suggest?" + queryParametersToString(true));
    }

    private String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        searchKeywords.forEach(entry ->  builder.add("searchKeywords." + entry.getLocale().toLanguageTag(), entry.getValue(), urlEncoded));
        Optional.ofNullable(limit).ifPresent(presentLimit -> builder.add("limit", Integer.toString(presentLimit), urlEncoded));
        Optional.ofNullable(staged).ifPresent(presentStaged -> builder.add("staged", Boolean.toString(presentStaged), urlEncoded));
        return builder.build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("searchKeywords", searchKeywords)
                .append("limit", limit)
                .append("staged", staged)
                //extra
                .append("readablePath", queryParametersToString(false))
                .toString();
    }
}
