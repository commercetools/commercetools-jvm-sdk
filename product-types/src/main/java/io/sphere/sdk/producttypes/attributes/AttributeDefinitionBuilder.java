package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.EntityQueryBuilder;

import java.util.List;

/**
 *
 * @param <A> the builder subclass
 */
public abstract class AttributeDefinitionBuilder<A> extends Base {
    private String name;
    private LocalizedString label;
    boolean isRequired = false;
    private AttributeConstraint attributeConstraint = AttributeConstraint.None;
    boolean isSearchable = true;

    AttributeDefinitionBuilder(final String name, final LocalizedString label) {
        this.name = name;
        this.label = label;
    }

    public static BaseBuilder<TextAttributeDefinition> ofText(final String name, final LocalizedString label,
                                                              final TextInputHint textInputHint) {
        return new TextAttributeDefinitionBuilder(name, label, textInputHint);
    }

    public static BaseBuilder<LocalizedTextAttributeDefinition> ofLocalizedText(final String name, final LocalizedString label,
                                                                                final TextInputHint textInputHint) {
        return new LocalizedTextAttributeDefinitionBuilder(name, label, textInputHint);
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

    public static BaseBuilder<EnumAttributeDefinition> ofEnum(final String name, final LocalizedString label, final List<PlainEnumValue> values) {
        return new EnumAttributeDefinitionBuilder(name, label, values);
    }

    public static BaseBuilder<LocalizedEnumAttributeDefinition> ofLocalizedEnum(final String name, final LocalizedString label, final List<LocalizedEnumValue> values) {
        return new LocalizedEnumAttributeDefinitionBuilder(name, label, values);
    }

    public static BaseBuilder<NumberAttributeDefinition> ofNumber(final String name, final LocalizedString label) {
        return new NumberAttributeDefinitionBuilder(name, label);
    }

    public static BaseBuilder<MoneyAttributeDefinition> ofMoney(final String name, final LocalizedString label) {
        return new MoneyAttributeDefinitionBuilder(name, label);
    }

    public static BaseBuilder<DateAttributeDefinition> ofDate(final String name, final LocalizedString label) {
        return new DateAttributeDefinitionBuilder(name, label);
    }

    public static BaseBuilder<TimeAttributeDefinition> ofTime(final String name, final LocalizedString label) {
        return new TimeAttributeDefinitionBuilder(name, label);
    }

    public static BaseBuilder<DateTimeAttributeDefinition> ofDateTime(final String name, final LocalizedString label) {
        return new DateTimeAttributeDefinitionBuilder(name, label);
    }
}
