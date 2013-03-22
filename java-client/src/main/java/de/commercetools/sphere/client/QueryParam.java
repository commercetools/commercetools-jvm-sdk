package io.sphere.client;

import net.jcip.annotations.Immutable;

/** Single HTTP query parameter. */
@Immutable
public class QueryParam {
    private final String name;
    private final String value;

    public QueryParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }
    public String getValue() { return value; }
}
