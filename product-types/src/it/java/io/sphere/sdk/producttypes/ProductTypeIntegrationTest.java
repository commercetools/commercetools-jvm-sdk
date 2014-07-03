package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.attributes.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.requests.ClientRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeIntegrationTest extends QueryIntegrationTest<ProductType> {


    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MultiLine;
    public static final LocalizedString LABEL = en("label");

    @Override
    protected ClientRequest<ProductType> deleteCommand(Versioned item) {
        return new ProductTypeDeleteCommand(item);
    }

    @Override
    protected ClientRequest<ProductType> newCreateCommandForName(String name) {
        return new ProductTypeCreateCommand(NewProductType.of(name, "desc"));
    }

    @Override
    protected String extractName(ProductType instance) {
        return instance.getName();
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryRequestForQueryAll() {
        return ProductTypes.query();
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForName(String name) {
        return ProductTypes.query().byName(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForNames(List<String> names) {
        return ProductTypes.query().withPredicate(ProductTypeQueryModel.get().name().isOneOf(names));
    }

    static abstract class AttributeTestCase {
        private final String attributeName;
        private final Class<? extends AttributeType> attributeTypeClass;

        protected AttributeTestCase(final String attributeName, final Class<? extends AttributeType> attributeTypeClass) {
            this.attributeName = attributeName;
            this.attributeTypeClass = attributeTypeClass;
        }

        public final String attributeName() {
            return attributeName;
        }

        abstract AttributeDefinition getAttributeDefinition();

        public final Class<? extends AttributeType> getExpectedAttributeTypeClass() {
            return attributeTypeClass;
        }

        abstract void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition);
    }

    Consumer<AttributeTestCase> attributeTestFunction = conf -> {
        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = conf.attributeName();

        cleanUpByName(productTypeName);


        final AttributeDefinition textAttribute = conf.getAttributeDefinition();
        final List<AttributeDefinition> attributes = Arrays.asList(textAttribute);

        final ProductTypeCreateCommand command = new ProductTypeCreateCommand(NewProductType.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = client.execute(command);
        assertThat(productType.getName()).isEqualTo(productTypeName);
        assertThat(productType.getDescription()).isEqualTo(productTypeDescription);
        assertThat(productType.getAttributes()).hasSize(1);
        final AttributeDefinition attributeDefinition = productType.getAttributes().get(0);

        assertThat(attributeDefinition.getName()).isEqualTo(attributeName);
        assertThat(attributeDefinition.getLabel()).isEqualTo(en("label"));
        assertThat(attributeDefinition.getAttributeType()).isInstanceOf(conf.getExpectedAttributeTypeClass());

        conf.furtherAttributeDefinitionAssertions(attributeDefinition);

        cleanUpByName(productTypeName);
    };

    @Test
    public void createTextAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("text-attribute", TextType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofText(attributeName(), LABEL, TEXT_INPUT_HINT).
                        attributeConstraint(AttributeConstraint.CombinationUnique).
                        searchable(false).
                        required(true).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {
                assertThat(attributeDefinition.getIsRequired()).isTrue();
                assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.CombinationUnique);
                assertThat(attributeDefinition.getIsSearchable()).isFalse();
                assertThat(((TextAttributeDefinition) attributeDefinition).getTextInputHint()).isEqualTo(TEXT_INPUT_HINT);
            }

        });
    }

    @Test
    public void createLocalizedTextAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("localized-text-attribute", LocalizedTextType.class) {

            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.ofLocalizedText(attributeName(), LABEL, TEXT_INPUT_HINT).
                        attributeConstraint(AttributeConstraint.CombinationUnique).
                        searchable(false).
                        required(true).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {
                assertThat(attributeDefinition.getIsRequired()).isTrue();
                assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.CombinationUnique);
                assertThat(attributeDefinition.getIsSearchable()).isFalse();
                assertThat(((LocalizedTextAttributeDefinition) attributeDefinition).getTextInputHint()).isEqualTo(TEXT_INPUT_HINT);
            }
        });
    }

    @Test
    public void createEnumAttribute() throws Exception {
        final List<PlainEnumValue> values = Arrays.asList(PlainEnumValue.of("key1", "value1"), PlainEnumValue.of("key2", "value2"));
        attributeTestFunction.accept(new AttributeTestCase("enum-attribute", EnumType.class) {

            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofEnum(attributeName(), LABEL, values).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {
                final EnumType attributeType = (EnumType) attributeDefinition.getAttributeType();
                assertThat(attributeType.getValues()).isEqualTo(values);
            }
        });
    }

    @Test
    public void createLocalizedEnumAttribute() throws Exception {
        final List<LocalizedEnumValue> values = Arrays.asList(LocalizedEnumValue.of("key1", en("value1")), LocalizedEnumValue.of("key2", en("value2")));
        attributeTestFunction.accept(new AttributeTestCase("lenum-attribute", LocalizedEnumType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofLocalizedEnum(attributeName(), LABEL, values).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {

            }
        });
    }

    @Test
    public void createNumberAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("number-attribute", NumberType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofNumber(attributeName(), LABEL).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {

            }
        });
    }

    @Test
    public void createMoneyAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("money-attribute", MoneyType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofMoney(attributeName(), LABEL).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {

            }
        });
    }

    @Test
    public void createDateAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("date-attribute", DateType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofDate(attributeName(), LABEL).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {

            }
        });
    }

    @Test
    public void createTimeAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("time-attribute", TimeType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofTime(attributeName(), LABEL).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {

            }
        });
    }

    @Test
    public void createDateTimeAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase("datetime-attribute", DateTimeType.class) {
            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        ofDateTime(attributeName(), LABEL).
                        build();
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {

            }
        });
    }
}
