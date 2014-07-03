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

import static io.sphere.sdk.test.SphereTestUtils.en;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeIntegrationTest extends QueryIntegrationTest<ProductType> {
    public static final List<LocalizedEnumValue> LOCALIZED_ENUM_VALUES = Arrays.asList(LocalizedEnumValue.of("key1", en("value1")), LocalizedEnumValue.of("key2", en("value2")));
    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MultiLine;
    public static final LocalizedString LABEL = en("label");
    public static final List<PlainEnumValue> PLAIN_ENUM_VALUES = Arrays.asList(PlainEnumValue.of("key1", "value1"), PlainEnumValue.of("key2", "value2"));

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

    @Test
    public void createTextAttribute() throws Exception {
        executeTest(TextType.class, AttributeDefinitionBuilder.
                ofText("text-attribute", LABEL, TEXT_INPUT_HINT).
                attributeConstraint(AttributeConstraint.CombinationUnique).
                searchable(false).
                required(true).
                build(), attributeDefinitionFromServer -> {
            assertThat(attributeDefinitionFromServer.getIsRequired()).isTrue();
            assertThat(attributeDefinitionFromServer.getAttributeConstraint()).isEqualTo(AttributeConstraint.CombinationUnique);
            assertThat(attributeDefinitionFromServer.getIsSearchable()).isFalse();
            assertThat(((TextAttributeDefinition) attributeDefinitionFromServer).getTextInputHint()).isEqualTo(TEXT_INPUT_HINT);
        });
    }

    @Test
    public void createLocalizedTextAttribute() throws Exception {
        executeTest(LocalizedTextType.class, AttributeDefinitionBuilder.ofLocalizedText("localized-text-attribute", LABEL, TEXT_INPUT_HINT).
                attributeConstraint(AttributeConstraint.CombinationUnique).
                searchable(false).
                required(true).
                build(), attributeDefinition -> {
            assertThat(attributeDefinition.getIsRequired()).isTrue();
            assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.CombinationUnique);
            assertThat(attributeDefinition.getIsSearchable()).isFalse();
            assertThat(((LocalizedTextAttributeDefinition) attributeDefinition).getTextInputHint()).isEqualTo(TEXT_INPUT_HINT);
        });
    }

    @Test
    public void createEnumAttribute() throws Exception {
        executeTest(EnumType.class, AttributeDefinitionBuilder.
                ofEnum("enum-attribute", LABEL, PLAIN_ENUM_VALUES).
                build(), attributeDefinition -> {
            final EnumType attributeType = (EnumType) attributeDefinition.getAttributeType();
            assertThat(attributeType.getValues()).isEqualTo(PLAIN_ENUM_VALUES);
        });
    }

    @Test
    public void createLocalizedEnumAttribute() throws Exception {
        executeTest(LocalizedEnumType.class, AttributeDefinitionBuilder.
                ofLocalizedEnum("lenum-attribute", LABEL, LOCALIZED_ENUM_VALUES).
                build());
    }

    @Test
    public void createNumberAttribute() throws Exception {
        executeTest(NumberType.class, AttributeDefinitionBuilder.
                ofNumber("number-attribute", LABEL).
                build());
    }

    @Test
    public void createMoneyAttribute() throws Exception {
        executeTest(MoneyType.class, AttributeDefinitionBuilder.
                ofMoney("money-attribute", LABEL).
                build());
    }

    @Test
    public void createDateAttribute() throws Exception {
        executeTest(DateType.class, AttributeDefinitionBuilder.
                ofDate("date-attribute", LABEL).
                build());
    }

    @Test
    public void createTimeAttribute() throws Exception {
        executeTest(TimeType.class, AttributeDefinitionBuilder.
                ofTime("time-attribute", LABEL).
                build());
    }

    @Test
    public void createDateTimeAttribute() throws Exception {
        executeTest(DateTimeType.class, AttributeDefinitionBuilder.
                ofDateTime("datetime-attribute", LABEL).
                build());
    }

    @Test
    public void createBooleanAttribute() throws Exception {
        executeTest(BooleanType.class, AttributeDefinitionBuilder.
                ofBoolean("boolean-attribute", LABEL).
                build());
    }

    @Test
    public void createSetOfTextAttribute() throws Exception {
        executeTest(SetType.class, AttributeDefinitionBuilder.
                ofSet("set-of-text-attribute", LABEL, new TextType()).
                build());
    }

    @Test
    public void createSetOfLocalizedEnumAttribute() throws Exception {
        executeTest(SetType.class, AttributeDefinitionBuilder.
                ofSet("set-of-localized-enum-attribute", LABEL, new LocalizedEnumType(LOCALIZED_ENUM_VALUES)).
                build(), attributeDefinitionFromServer -> {
            final SetAttributeDefinition setAttributeDefinition = (SetAttributeDefinition) attributeDefinitionFromServer;
            final SetType setType = setAttributeDefinition.getAttributeType();
            final LocalizedEnumType elementType = (LocalizedEnumType) setType.getElementType();
            assertThat(elementType.getValues()).isEqualTo(LOCALIZED_ENUM_VALUES);
        });
    }

    private void executeTest(final Class<? extends AttributeType> attributeTypeClass, final AttributeDefinition attributeDefinition, final Consumer<AttributeDefinition> furtherAttributeDefinitionAssertions) {
        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = attributeDefinition.getName();

        cleanUpByName(productTypeName);

        final List<AttributeDefinition> attributes = Arrays.asList(attributeDefinition);

        final ProductTypeCreateCommand command = new ProductTypeCreateCommand(NewProductType.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = client.execute(command);
        assertThat(productType.getName()).isEqualTo(productTypeName);
        assertThat(productType.getDescription()).isEqualTo(productTypeDescription);
        assertThat(productType.getAttributes()).hasSize(1);
        final AttributeDefinition fetchedAttributeDefinition = productType.getAttributes().get(0);

        assertThat(fetchedAttributeDefinition.getName()).isEqualTo(attributeName);
        assertThat(fetchedAttributeDefinition.getLabel()).isEqualTo(en("label"));
        assertThat(fetchedAttributeDefinition.getAttributeType()).isInstanceOf(attributeTypeClass);

        furtherAttributeDefinitionAssertions.accept(fetchedAttributeDefinition);

        cleanUpByName(productTypeName);
    }

    private void executeTest(final Class<? extends AttributeType> attributeTypeClass, final AttributeDefinition attributeDefinition) {
        executeTest(attributeTypeClass, attributeDefinition, x -> {
        });
    }
}
