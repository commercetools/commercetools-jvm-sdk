package io.sphere.sdk.http;

import java.util.LinkedList;
import java.util.List;

import static io.sphere.sdk.http.UrlUtils.urlEncode;
import static java.util.stream.Collectors.joining;

public final class UrlQueryBuilder extends Base {
    final List<String> elements = new LinkedList<>();

    private UrlQueryBuilder() {
    }

    public UrlQueryBuilder add(final String key, final String value) {
        elements.add(key + "=" + value);
        return this;
    }

    public UrlQueryBuilder add(final String key, final String value, final boolean urlEncoded) {
        return urlEncoded ? addEncoded(key, value) : add(key, value);
    }

    public UrlQueryBuilder addEncoded(final String key, final String value) {
        return add(key, urlEncode(value));
    }

    private String buildPath() {
        return elements.stream().collect(joining("&"));
    }

    public String build() {
        return buildPath();
    }

    public String toStringWithOptionalQuestionMark() {
        final String withoutQuestionMark = buildPath();
        final boolean isEmpty = "".equals(withoutQuestionMark);
        return isEmpty ? "" : "?" + withoutQuestionMark;
    }

    public static UrlQueryBuilder of() {
        return new UrlQueryBuilder();
    }
}
