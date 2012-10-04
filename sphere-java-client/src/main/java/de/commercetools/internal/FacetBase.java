package de.commercetools.internal;

import com.google.common.base.Strings;
import de.commercetools.sphere.client.Facet;

public abstract class FacetBase implements Facet {
    protected String attribute;

    protected FacetBase(String attribute) {
        if (Strings.isNullOrEmpty(attribute))
            throw new IllegalArgumentException("Please specify an attribute to facet on.");
        this.attribute = attribute;
    }

    public String getAttributeName() {
        return attribute;
    }
}
