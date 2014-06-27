package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

abstract class AttributeDefinitionBuilder<A, T> implements Builder<T> {
    private String name;
    private LocalizedString label;
    private boolean isRequired = false;
    private AttributeConstraint attributeConstraint = AttributeConstraint.None;
    private boolean isSearchable = true;

    protected AttributeDefinitionBuilder(final String name, final LocalizedString label) {
        this.name = name;
        this.label = label;
    }

    public A name(final String name) {
        this.name = name;
        return getThis();
    }

    public A label(final LocalizedString label) {
        this.label = label;
        return getThis();
    }

    //protected since not every definition allows every setting
    protected A required(final boolean isRequired) {
        this.isRequired = isRequired;
        return getThis();
    }

    //protected since not every definition allows every setting
    protected A attributeConstraint(final AttributeConstraint attributeConstraint) {
        this.attributeConstraint = attributeConstraint;
        return getThis();
    }

    //protected since not every definition allows every setting
    protected A searchable(final boolean isSearchable) {
        this.isSearchable = isSearchable;
        return getThis();
    }

    protected abstract A getThis();

    protected abstract AttributeType getAttributeType();

    protected String getName() {
        return name;
    }

    protected LocalizedString getLabel() {
        return label;
    }

    protected boolean isRequired() {
        return isRequired;
    }

    protected AttributeConstraint getAttributeConstraint() {
        return attributeConstraint;
    }

    protected boolean isSearchable() {
        return isSearchable;
    }
}
