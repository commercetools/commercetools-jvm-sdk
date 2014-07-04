package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

abstract class AttributeDefinitionBuilder<A> extends Base {
    private String name;
    private LocalizedString label;
    boolean isRequired = false;
    private AttributeConstraint attributeConstraint = AttributeConstraint.None;
    boolean isSearchable = true;

    AttributeDefinitionBuilder(final String name, final LocalizedString label) {
        this.name = name;
        this.label = label;
    }

    public A attributeConstraint(final AttributeConstraint attributeConstraint) {
        this.attributeConstraint = attributeConstraint;
        return getThis();
    }

    public A name(final String name) {
        this.name = name;
        return getThis();
    }

    public A label(final LocalizedString label) {
        this.label = label;
        return getThis();
    }

    protected abstract A getThis();

    String getName() {
        return name;
    }

    LocalizedString getLabel() {
        return label;
    }

    boolean isRequired() {
        return isRequired;
    }

    AttributeConstraint getAttributeConstraint() {
        return attributeConstraint;
    }

    boolean isSearchable() {
        return isSearchable;
    }
}
