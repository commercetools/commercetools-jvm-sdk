package de.commercetools.internal;

import de.commercetools.sphere.client.UserInputFilterDefinition;

import java.util.Map;

import static de.commercetools.internal.util.QueryStringConstruction.clearParam;

/** Helper base class for implementations of {@link UserInputFilterDefinition}. */
public abstract class UserInputFilterDefinitionBase<T> implements UserInputFilterDefinition<T> {
    /** Name of the query parameter representing this filter. */
    protected String queryParam;

    protected UserInputFilterDefinitionBase(String queryParam) {
        this.queryParam = queryParam;
    }
    public String getQueryParamName() {
        return this.queryParam;
    }

    @Override public final String getClearLink(Map<String,String[]> queryString) {
        return clearParam(queryParam, queryString);
    }
    @Override public final boolean isSet(Map<String,String[]> queryString) {
        return parseValue(queryString) != null;
    }
}
