package de.commercetools.internal;

import com.google.common.collect.ImmutableList;
import de.commercetools.sphere.client.MultiSelectFilter;
import de.commercetools.sphere.client.QueryParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.QueryStringConstruction.*;
import static de.commercetools.internal.util.SearchUtil.list;
import static de.commercetools.internal.util.SearchUtil.toList;

/** Helper base class for implementations of {@link MultiSelectFilter}s. */
public abstract class MultiSelectFilterBase<T> implements MultiSelectFilter<T> {
    /** Name of the application-level query parameter for this filter. */
    protected String queryParam;
    /** Backend name of the custom attribute. */
    protected String attribute;
    /** The attribute on which this filter matches. */
    public String getAttributeName() {
        return attribute;
    }
    private final ImmutableList<T> values;
    /** {@inheritDoc} */
    public List<T> getValues() {
        return values;
    }
    /** If true, only one value can be selected by user at a time. */
    protected boolean isSingleSelect = false;

    public MultiSelectFilterBase(String attribute, T value, T... values) {
        this(attribute, list(value, values));
    }
    public MultiSelectFilterBase(String attribute, Collection<T> values) {
        this.attribute = attribute; this.queryParam = attribute; this.values = toList(values);
    }
    public MultiSelectFilterBase(String attribute, String queryParam, T value, T... values) {
        this(attribute, queryParam, list(value, values));
    }
    public MultiSelectFilterBase(String attribute, String queryParam, Collection<T> values) {
        this.attribute = attribute; this.queryParam = queryParam; this.values = toList(values);
    }

    /** Returns the values that the user selected for this filter (passed in application's URL).
     *  Used in implementations of {@link de.commercetools.sphere.client.Filter#parse}. */
    protected abstract List<T> parseValues(Map<String,String[]> queryString);

    /** {@inheritDoc} */
    public abstract List<QueryParam> getUrlParams(T value);
    /** {@inheritDoc} */
    @Override public final String getSelectLink(T value, Map<String, String[]> queryParams) {
        if (isSingleSelect) {
            // If single select, remove all existing query params for this filter.
            Map<String, String[]> queryString = queryParams;
            for (T v: getValues()) {
                queryString = clearParams(queryString, getUrlParams(v));
            }
            return toQueryString(addURLParams(queryString, getUrlParams(value)));
        } else {
            return toQueryString(addURLParams(queryParams, getUrlParams(value)));
        }
    }
    /** {@inheritDoc} */
    @Override public final String getUnselectLink(T value, Map<String, String[]> queryParams) {
        return toQueryString(removeURLParams(queryParams, getUrlParams(value)));
    }
    /** {@inheritDoc} */
    @Override public final boolean isSelected(T value, Map<String, String[]> queryParams) {
        return containsAllURLParams(queryParams, getUrlParams(value));
    }
}
