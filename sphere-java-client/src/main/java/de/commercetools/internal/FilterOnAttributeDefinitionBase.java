package de.commercetools.internal;

/** Definition of a filter that matches on a custom attribute. */
public abstract class FilterOnAttributeDefinitionBase extends FilterDefinitionBase {
    /** Backend name of the custom attribute. */
    protected String attribute;

    /** Creates a new instance of filter definition where the query parameter is the backend attribute name. */
    protected FilterOnAttributeDefinitionBase(String attribute) { this(attribute, attribute); }
    /** Creates a new instance of filter definition with custom query parameter name. */
    protected FilterOnAttributeDefinitionBase(String attribute, String param) { super(param); }
}
