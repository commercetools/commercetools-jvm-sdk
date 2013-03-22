package io.sphere.internal.filters;

/** Helper base class for implementations of {@link io.sphere.client.filters.UserInputFilter}s
 *  that match on given attribute. */
public abstract class UserInputAttributeFilterBase<T> extends UserInputFilterBase<T> {
    /** Backend name of the custom attribute. */
    protected String attribute;
    /** Backend name of the attribute this filter matches on. */
    public String getAttribute() {
        return attribute;
    }

    protected UserInputAttributeFilterBase(String attribute) { super(attribute); this.attribute = attribute; }
}
