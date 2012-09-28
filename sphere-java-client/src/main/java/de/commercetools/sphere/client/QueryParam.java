package de.commercetools.sphere.client;

import net.jcip.annotations.Immutable;

/** HTTP query parameter to be added to a query to a Sphere API endpoint. */
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
