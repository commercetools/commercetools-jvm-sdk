package de.commercetools.internal;

import com.google.common.base.Strings;
import de.commercetools.sphere.client.FilterExpression;
import de.commercetools.sphere.client.FilterType;

public abstract class FilterExpressionBase implements FilterExpression {
    protected String attribute;
    protected FilterType filterType;

    protected FilterExpressionBase(String attribute, FilterType filterType) {
        if (Strings.isNullOrEmpty(attribute))
            throw new IllegalArgumentException("Please specify an attribute to filter on.");
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
