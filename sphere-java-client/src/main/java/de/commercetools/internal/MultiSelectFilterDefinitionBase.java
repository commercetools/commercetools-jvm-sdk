package de.commercetools.internal;

import com.google.common.collect.ImmutableList;
import de.commercetools.sphere.client.MultiSelectFilterDefinition;
import de.commercetools.sphere.client.QueryParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.QueryStringConstruction.addURLParams;
import static de.commercetools.internal.util.QueryStringConstruction.containsAllURLParams;
import static de.commercetools.internal.util.QueryStringConstruction.removeURLParams;
import static de.commercetools.internal.util.SearchUtil.list;
import static de.commercetools.internal.util.SearchUtil.toList;

public abstract class MultiSelectFilterDefinitionBase<T> implements MultiSelectFilterDefinition<T> {
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

    public MultiSelectFilterDefinitionBase(String attribute, T value, T... values) {
        this(attribute, list(value, values));
    }
    public MultiSelectFilterDefinitionBase(String attribute, Collection<T> values) {
        this.attribute = attribute; this.queryParam = attribute; this.values = toList(values);
    }
    public MultiSelectFilterDefinitionBase(String attribute, String queryParam, T value, T... values) {
        this(attribute, queryParam, list(value, values));
    }
    public MultiSelectFilterDefinitionBase(String attribute, String queryParam, Collection<T> values) {
        this.attribute = attribute; this.queryParam = queryParam; this.values = toList(values);
    }

    /** {@inheritDoc} */
    public abstract List<QueryParam> getUrlParams(T value);
    /** {@inheritDoc} */
    @Override public final String getSelectLink(T value, Map<String, String[]> queryParams) {
        return addURLParams(queryParams, getUrlParams(value));
    }
    /** {@inheritDoc} */
    @Override public final String getUnselectLink(T value, Map<String, String[]> queryParams) {
        return removeURLParams(queryParams, getUrlParams(value));
    }
    /** {@inheritDoc} */
    @Override public final boolean isSelected(T value, Map<String, String[]> queryParams) {
        return containsAllURLParams(queryParams, getUrlParams(value));
    }
}
