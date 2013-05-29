package io.sphere.internal.filters;

import com.google.common.base.Strings;
import io.sphere.internal.Defaults;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.filters.expressions.FilterType;

public abstract class FilterExpressionBase implements FilterExpression {
    protected final String attribute;
    protected FilterType filterType;

    protected FilterExpressionBase(String attribute) {
        if (Strings.isNullOrEmpty(attribute))
            throw new IllegalArgumentException("Attribute to filter by can't be empty.");
        this.attribute = attribute;
        this.filterType = Defaults.filterType;
    }

    public abstract FilterExpressionBase setFilterType(FilterType filterType);

    public String getAttributeName() { return attribute; }

    public FilterType getFilterType() { return filterType; }
}
