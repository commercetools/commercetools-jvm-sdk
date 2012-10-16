package de.commercetools.internal;

/** Helper base class for implementations of {@link de.commercetools.sphere.client.UserInputFilterDefinition}s
 *  that match on given attribute. */
public abstract class UserInputFilterOnAttributeDefinitionBase<T> extends UserInputFilterDefinitionBase<T> {
    /** Backend name of the custom attribute. */
    protected String attribute;
    /** Backend name of the attribute this filter matches on. */
    public String getAttribute() {
        return attribute;
    }

    protected UserInputFilterOnAttributeDefinitionBase(String attribute) { this(attribute, attribute); }
    protected UserInputFilterOnAttributeDefinitionBase(String attribute, String param) { super(param); this.attribute = attribute; }
}
