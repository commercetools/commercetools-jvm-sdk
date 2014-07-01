package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

/**
 *
 * @param <A> the builder subclass
 */
public abstract class AttributeDefinitionBuilder<A> {
    private String name;
    private LocalizedString label;
    boolean isRequired = false;
    private AttributeConstraint attributeConstraint = AttributeConstraint.None;
    boolean isSearchable = true;

    AttributeDefinitionBuilder(final String name, final LocalizedString label) {
        this.name = name;
        this.label = label;
    }

    public static BasicAttributeDefinitionBuilder<TextAttributeDefinition> text(final String name,
                                                                                final LocalizedString label,
                                                                                final TextInputHint textInputHint) {
        return new BasicAttributeDefinitionBuilder<TextAttributeDefinition>(name, label) {
            @Override
            protected BasicAttributeDefinitionBuilder<TextAttributeDefinition> getThis() {
                return this;
            }

            @Override
            public TextAttributeDefinition build() {
                return new TextAttributeDefinition(new TextType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
            }
        };
    }

    public static BasicAttributeDefinitionBuilder<LocalizedTextAttributeDefinition> localizedText(final String name,
                                                                                                  final LocalizedString label,
                                                                                                  final TextInputHint textInputHint) {
        return new BasicAttributeDefinitionBuilder<LocalizedTextAttributeDefinition>(name, label) {

            @Override
            protected BasicAttributeDefinitionBuilder<LocalizedTextAttributeDefinition> getThis() {
                return this;
            }

            @Override
            public LocalizedTextAttributeDefinition build() {
                return new LocalizedTextAttributeDefinition(new LocalizedTextType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
            }
        };
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
