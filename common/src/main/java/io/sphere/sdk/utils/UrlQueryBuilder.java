package io.sphere.sdk.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

import static io.sphere.sdk.utils.UrlUtils.urlEncode;

public class UrlQueryBuilder {
    final List<String> elements = Lists.newLinkedList();

    public UrlQueryBuilder() {
    }

    public UrlQueryBuilder add(final String key, final String value) {
        elements.add(key + "=" + value);
        return this;
    }

    public UrlQueryBuilder addEncoded(final String key, final String value) {
        return add(key, urlEncode(value));
    }

    @Override
    public String toString() {
        return Joiner.on("&").join(elements);
    }
}
