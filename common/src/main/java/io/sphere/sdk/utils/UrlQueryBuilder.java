package io.sphere.sdk.utils;

import java.util.LinkedList;
import java.util.List;

import static io.sphere.sdk.utils.UrlUtils.urlEncode;
import static java.util.stream.Collectors.joining;

public class UrlQueryBuilder {
    final List<String> elements = new LinkedList<>();

    public UrlQueryBuilder() {
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

    @Override
    public String toString() {
        return elements.stream().map(x -> x.toString()).collect(joining("&"));
    }
}
