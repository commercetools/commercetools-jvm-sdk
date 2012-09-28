package de.commercetools.internal;

import de.commercetools.sphere.client.Filter;
import de.commercetools.sphere.client.FilterType;

public abstract class FilterBase implements Filter {
    protected String attribute;
    protected FilterType filterType;

    protected FilterBase(String attribute, FilterType filterType) {
        this.attribute = attribute;
        this.filterType = filterType;
    }

    public String getAttributeName() {
        return attribute;
    }
    public FilterType getFilterType() {
        return filterType;
    }
}
