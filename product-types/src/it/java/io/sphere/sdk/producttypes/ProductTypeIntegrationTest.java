package io.sphere.sdk.producttypes;

import example.TShirtNewProductType;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.attributes.*;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.requests.ClientRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeIntegrationTest extends QueryIntegrationTest<ProductType> {
    public static final List<LocalizedEnumValue> LOCALIZED_ENUM_VALUES = Arrays.asList(LocalizedEnumValue.of("key1", en("value1")), LocalizedEnumValue.of("key2", en("value2")));
    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MultiLine;
    public static final LocalizedString LABEL = en("label");
    public static final List<PlainEnumValue> PLAIN_ENUM_VALUES = Arrays.asList(PlainEnumValue.of("key1", "value1"), PlainEnumValue.of("key2", "value2"));
    public static final NewProductType tshirt = new TShirtNewProductType();
    public static final String distractorName = "distractor";

    @Override
    protected ClientRequest<ProductType> deleteCommand(Versioned item) {
        return new ProductTypeDeleteByIdCommand(item);
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
        return ProductType.query();
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForName(String name) {
        return ProductType.query().byName(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForNames(List<String> names) {
        return ProductType.query().withPredicate(ProductTypeQueryModel.get().name().isOneOf(names));
    }

    @Test
    public void createTextAttribute() throws Exception {
        executeTest(TextType.class, TextAttributeDefinitionBuilder.of("text-attribute", LABEL, TEXT_INPUT_HINT).
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
        executeTest(LocalizedTextType.class, LocalizedTextAttributeDefinitionBuilder.of("localized-text-attribute", LABEL, TEXT_INPUT_HINT).
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
        executeTest(EnumType.class, EnumAttributeDefinitionBuilder.of("enum-attribute", LABEL, PLAIN_ENUM_VALUES).
                build(), attributeDefinition -> {
            final EnumType attributeType = (EnumType) attributeDefinition.getAttributeType();
            assertThat(attributeType.getValues()).isEqualTo(PLAIN_ENUM_VALUES);
        });
    }

    @Test
    public void createLocalizedEnumAttribute() throws Exception {
        executeTest(LocalizedEnumType.class, LocalizedEnumAttributeDefinitionBuilder.of("lenum-attribute", LABEL, LOCALIZED_ENUM_VALUES).
                build());
    }

    @Test
    public void createNumberAttribute() throws Exception {
        executeTest(NumberType.class, NumberAttributeDefinitionBuilder.of("number-attribute", LABEL).
                build());
    }

    @Test
    public void createMoneyAttribute() throws Exception {
        executeTest(MoneyType.class, MoneyAttributeDefinitionBuilder.of("money-attribute", LABEL).
                build());
    }

    @Test
    public void createDateAttribute() throws Exception {
        executeTest(DateType.class, DateAttributeDefinitionBuilder.of("date-attribute", LABEL).
                build());
    }

    @Test
    public void createTimeAttribute() throws Exception {
        executeTest(TimeType.class, TimeAttributeDefinitionBuilder.of("time-attribute", LABEL).
                build());
    }

    @Test
    public void createDateTimeAttribute() throws Exception {
        executeTest(DateTimeType.class, DateTimeAttributeDefinitionBuilder.of("datetime-attribute", LABEL).
                build());
    }

    @Test
    public void createBooleanAttribute() throws Exception {
        executeTest(BooleanType.class, BooleanAttributeDefinitionBuilder.of("boolean-attribute", LABEL).
                build());
    }

    @Test
    public void createSetOfBooleanAttribute() throws Exception {
        testSetAttribute("set-of-boolean-attribute", new BooleanType());
    }

    @Test
    public void createSetOfTextAttribute() throws Exception {
        testSetAttribute("set-of-text-attribute", new TextType());
    }

    @Test
    public void createSetOfLocalizedTextAttribute() throws Exception {
        testSetAttribute("set-of-localized-text-attribute", new LocalizedTextType());
    }

    @Test
    public void createSetOfEnumAttribute() throws Exception {
        testSetAttribute("set-of-enum-attribute", new EnumType(PLAIN_ENUM_VALUES));
    }

    @Test
    public void createSetOfLocalizedEnumAttribute() throws Exception {
        executeTest(SetType.class, SetAttributeDefinitionBuilder.of("set-of-localized-enum-attribute", LABEL, new LocalizedEnumType(LOCALIZED_ENUM_VALUES)).
                build(), attributeDefinitionFromServer -> {
            final SetAttributeDefinition setAttributeDefinition = (SetAttributeDefinition) attributeDefinitionFromServer;
            final SetType setType = setAttributeDefinition.getAttributeType();
            final LocalizedEnumType elementType = (LocalizedEnumType) setType.getElementType();
            assertThat(elementType.getValues()).isEqualTo(LOCALIZED_ENUM_VALUES);
        });
    }

    @Test
    public void createSetOfNumberAttribute() throws Exception {
        testSetAttribute("set-of-number-attribute", new NumberType());
    }

    @Test
    public void createSetOfMoneyAttribute() throws Exception {
        testSetAttribute("set-of-money-attribute", new MoneyType());
    }

    @Test
    public void createSetOfDateAttribute() throws Exception {
        testSetAttribute("set-of-date-attribute", new DateType());
    }

    @Test
    public void createSetOfTimeAttribute() throws Exception {
        testSetAttribute("set-of-time-attribute", new TimeType());
    }

    @Test
    public void createSetOfDateTimeAttribute() throws Exception {
        testSetAttribute("set-of-datetime-attribute", new DateTimeType());
    }

    @Test
    public void queryByName() throws Exception {
        withTShirtProductType(type -> {
            ProductType productType = client().execute(ProductType.query().byName("t-shirt")).head().get();
            Optional<EnumAttributeDefinition> sizeAttribute = AttributeDefinition.findByName(productType.getAttributes(), "size", EnumAttributeDefinition.class);
            final List<PlainEnumValue> possibleSizeValues = sizeAttribute.
                    map(attrib -> attrib.getAttributeType().getValues()).
                    orElse(Collections.<PlainEnumValue>emptyList());
            final List<PlainEnumValue> expected =
                    Arrays.asList(PlainEnumValue.of("S", "S"), PlainEnumValue.of("M", "M"), PlainEnumValue.of("X", "X"));
            assertThat(possibleSizeValues).isEqualTo(expected);
        });
    }

    @Test
    public void queryByAttributeName() throws Exception {
        Predicate<ProductTypeQueryModel<ProductType>> hasSizeAttribute = ProductTypeQueryModel.get().attributes().name().is("size");
        final QueryDsl<ProductType, ProductTypeQueryModel<ProductType>> query = ProductType.query().withPredicate(hasSizeAttribute);
        PagedQueryResult<ProductType> result = client().execute(query);
        final int sizeAttributesWithoutTShirtExample = result.getTotal();
        withTShirtProductType(type -> assertThat(client().execute(query).getTotal()).isEqualTo(sizeAttributesWithoutTShirtExample + 1));
    }

    @Test
    public void queryByAttributeType() throws Exception {
        final String attributeTypeName = "enum";
        final Predicate<ProductTypeQueryModel<ProductType>> predicate = ProductTypeQueryModel.get().attributes().type().name().is(attributeTypeName);
        final Query<ProductType> query = ProductType.query().withPredicate(predicate);
        Supplier<Integer> numberOfProducts = () -> client().execute(query).getTotal();
        final int numberOfTypesWithoutDistractor = numberOfProducts.get();
        withDistractorProductType(x -> {
            final int numberOfTypesWithDistractor = numberOfProducts.get();
            withTShirtProductType(y -> {
                final PagedQueryResult<ProductType> pagedQueryResult = client().execute(query);
                final Integer count = pagedQueryResult.getTotal();
                assertThat(count).isEqualTo(numberOfTypesWithDistractor + 1).isEqualTo(numberOfTypesWithoutDistractor + 1);
                final java.util.function.Predicate<ProductType> containsEnumAttr = productType -> productType.getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeTypeName));
                assertThat(pagedQueryResult.getResults().stream().allMatch(containsEnumAttr));
            });
        });

    }

    private void withDistractorProductType(final Consumer<ProductType> consumer) {
        cleanUpByName(distractorName);
        final ProductType productType = client().execute(new ProductTypeCreateCommand(NewProductType.of(distractorName, "desc")));
        consumer.accept(productType);
        cleanUpByName(distractorName);
    }

    private void withTShirtProductType(final Consumer<ProductType> consumer) {
        cleanUpByName(tshirt.getName());
        final ProductType productType = client().execute(new ProductTypeCreateCommand(tshirt));
        assertThat(productType.getName()).isEqualTo(tshirt.getName());
        consumer.accept(productType);
        cleanUpByName(tshirt.getName());
    }

    private void testSetAttribute(final String attributeName, final AttributeType attributeType) {
        executeTest(SetType.class, SetAttributeDefinitionBuilder.of(attributeName, LABEL, attributeType).
                build(), attrDef -> {
            final SetType receivedType = (SetType) attrDef.getAttributeType();
            assertThat(receivedType.getElementType()).isInstanceOf(attributeType.getClass());
        });
    }

    private void executeTest(final Class<? extends AttributeType> attributeTypeClass, final AttributeDefinition attributeDefinition, final Consumer<AttributeDefinition> furtherAttributeDefinitionAssertions) {
        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = attributeDefinition.getName();

        cleanUpByName(productTypeName);

        final List<AttributeDefinition> attributes = Arrays.asList(attributeDefinition);

        final ProductTypeCreateCommand command = new ProductTypeCreateCommand(NewProductType.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = client().execute(command);
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
