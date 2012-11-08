package de.commercetools.internal.filters;

import com.google.common.base.Strings;
import de.commercetools.internal.Defaults;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.filters.expressions.FilterType;

public abstract class FilterExpressionBase implements FilterExpression {
    protected final String attribute;
    protected FilterType filterType;

    protected FilterExpressionBase(String attribute) {
        if (Strings.isNullOrEmpty(attribute))
            throw new IllegalArgumentException("Please specify an attribute to filter on.");
        this.attribute = attribute;
        this.filterType = Defaults.filterType;
    }

    public abstract FilterExpressionBase setFilterType(FilterType filterType);

    public String getAttributeName() { return attribute; }

    public FilterType getFilterType() { return filterType; }
}
