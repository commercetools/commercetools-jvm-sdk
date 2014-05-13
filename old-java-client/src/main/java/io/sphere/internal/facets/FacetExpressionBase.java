package io.sphere.internal.facets;

import com.google.common.base.Strings;
import io.sphere.client.facets.expressions.FacetExpression;

public abstract class FacetExpressionBase implements FacetExpression {
    protected String attribute;

    protected FacetExpressionBase(String attribute) {
        if (Strings.isNullOrEmpty(attribute))
            throw new IllegalArgumentException("Attribute to facet on can't be empty.");
        this.attribute = attribute;
    }

    public String getAttributeName() { return attribute; }
}
