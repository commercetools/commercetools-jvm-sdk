package de.commercetools.internal;

/** Definition of a facet that matches on a custom attribute. */
public abstract class FacetOnAttributeDefinitionBase extends FacetDefinitionBase {
    /** Backend name of the custom attribute. */
    protected String attribute;

    /** Creates a new instance of facet definition where the query parameter is the backend attribute name. */
    protected FacetOnAttributeDefinitionBase(String attribute) { this(attribute, attribute); }
    /** Creates a new instance of facet definition with custom query parameter name. */
    protected FacetOnAttributeDefinitionBase(String attribute, String queryParam) { super(queryParam); this.attribute = attribute; }
}