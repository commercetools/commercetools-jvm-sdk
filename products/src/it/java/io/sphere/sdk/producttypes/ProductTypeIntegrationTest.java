package io.sphere.sdk.producttypes;

import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.suppliers.TShirtNewProductTypeSupplier;
import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.http.ClientRequest;
import org.junit.Test;

import java.util.*;
import java.util.function.*;

import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeIntegrationTest extends QueryIntegrationTest<ProductType> {
    public static final List<LocalizedEnumValue> LOCALIZED_ENUM_VALUES = asList(LocalizedEnumValue.of("key1", en("value1")), LocalizedEnumValue.of("key2", en("value2")));
    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MultiLine;
    public static final LocalizedString LABEL = en("label");
    public static final List<PlainEnumValue> PLAIN_ENUM_VALUES = asList(PlainEnumValue.of("key1", "value1"), PlainEnumValue.of("key2", "value2"));
    public static final NewProductType tshirt = new TShirtNewProductTypeSupplier("t-shirt").get();
    public static final String distractorName = "distractor";

    @Override
    protected ClientRequest<ProductType> deleteCommand(final ProductType item) {
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
        return new ProductTypeQuery();
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForName(String name) {
        return new ProductTypeQuery().byName(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForNames(List<String> names) {
        return new ProductTypeQuery().withPredicate(ProductTypeQuery.model().name().isOneOf(names));
    }

    @Test
    public void booleanAttribute() throws Exception {
        test(AttributeAccess.ofBoolean(), true, BooleanType.class,
                BooleanAttributeDefinitionBuilder.of("boolean-attribute", LABEL).build());
    }

    @Test
    public void booleanSetAttribute() throws Exception {
        testSetAttribute(AttributeAccess.ofBooleanSet(), "set-of-boolean-attribute", asSet(true, false), new BooleanType());
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
    public void flatReferenceAttributes() throws Exception {
        testReferenceAttribute("product-type-reference", ReferenceType.ofProductType());
        testReferenceAttribute("product-reference", ReferenceType.ofProduct());
        testReferenceAttribute("category-reference", ReferenceType.ofCategory());
        testReferenceAttribute("channel-reference", ReferenceType.ofChannel());
    }

    @Test
    public void setReferenceAttributes() throws Exception {
        testSetAttribute("set-of-product-type-reference", ReferenceType.ofProductType());
        testSetAttribute("set-of-product-reference", ReferenceType.ofProduct());
        testSetAttribute("set-of-category-reference", ReferenceType.ofCategory());
        testSetAttribute("set-of-channel-reference", ReferenceType.ofChannel());
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
            ProductType productType = client().execute(new ProductTypeQuery().byName("t-shirt")).head().get();
            Optional<EnumAttributeDefinition> sizeAttribute = productType.getAttribute("size", EnumAttributeDefinition.class);
            final List<PlainEnumValue> possibleSizeValues = sizeAttribute.
                    map(attrib -> attrib.getAttributeType().getValues()).
                    orElse(Collections.<PlainEnumValue>emptyList());
            final List<PlainEnumValue> expected =
                    asList(PlainEnumValue.of("S", "S"), PlainEnumValue.of("M", "M"), PlainEnumValue.of("X", "X"));
            assertThat(possibleSizeValues).isEqualTo(expected);
        });
    }

    @Test
    public void queryByAttributeType() throws Exception {
        final String attributeTypeName = "enum";
        final Query<ProductType> queryForEnum = new ProductTypeQuery().withPredicate(hasAttributeType(attributeTypeName));
        withDistractorProductType(x -> {//contains no enum attribute, so it should not be included in the result
            withTShirtProductType(y -> {
                final java.util.function.Predicate<ProductType> containsEnumAttr = productType -> productType.getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeTypeName));
                final List<ProductType> productTypes = client().execute(queryForEnum).getResults();
                assertThat(productTypes.stream().allMatch(containsEnumAttr));
            });
        });
    }

    private Predicate<ProductType> hasAttributeType(final String attributeTypeName) {
        return ProductTypeQuery.model().attributes().type().name().is(attributeTypeName);
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

    private void testReferenceAttribute(final String attributeName, final ReferenceType referenceType) {
        executeTest(ReferenceType.class, ReferenceAttributeDefinitionBuilder.of(attributeName, LABEL, referenceType).
                build(), attrDef -> {
            final ReferenceType receivedType = (ReferenceType) attrDef.getAttributeType();
            assertThat(receivedType.getReferenceTypeId()).isEqualTo(referenceType.getReferenceTypeId());
        });
    }


    private <X> void test(final AttributeAccess<X> access, final X exampleValue,
                          final Class<? extends AttributeType> attributeTypeClass,
                          final AttributeDefinition attributeDefinition,
                          final Consumer<AttributeDefinition> furtherAttributeDefinitionAssertions) {

        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = attributeDefinition.getName();

        cleanUpByName(productTypeName);

        final List<AttributeDefinition> attributes = asList(attributeDefinition);

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

        final AttributeGetterSetter<Product, X> attributeGetterSetter = access.getterSetter(attributeName);
        final NewProductVariant masterVariant = NewProductVariantBuilder.of().attributes(attributeGetterSetter.valueOf(exampleValue)).build();
        final NewProduct newProduct = NewProductBuilder.of(productType, LocalizedString.of(ENGLISH, "product to test attributes"), randomSlug(), masterVariant).build();
        final Product product = client().execute(new ProductCreateCommand(newProduct));
        final X actual = product.getMasterData().getStaged().getMasterVariant().getAttribute(attributeGetterSetter).get();
        assertThat(actual).isEqualTo(exampleValue);

        final Boolean found = product.getMasterData().getStaged().getMasterVariant().getAttribute(attributeName).get()
                .<Boolean>collect(fetchedAttributeDefinition)
                .ifIs(access, x -> true)
                .getValue().orElse(false);
        assertThat(found).overridingErrorMessage("the attribute type should be recognized").isTrue();

        client().execute(new ProductDeleteByIdCommand(product));
        cleanUpByName(productTypeName);

    }

    private <X> void test(final AttributeAccess<X> access, final X value,
                          final Class<? extends AttributeType> attributeTypeClass,
                          final AttributeDefinition attributeDefinition) {
        test(access, value, attributeTypeClass, attributeDefinition, x -> {});
    }

    private void executeTest(final Class<? extends AttributeType> attributeTypeClass, final AttributeDefinition attributeDefinition, final Consumer<AttributeDefinition> furtherAttributeDefinitionAssertions) {
        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = attributeDefinition.getName();

        cleanUpByName(productTypeName);

        final List<AttributeDefinition> attributes = asList(attributeDefinition);

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

    private <X> void testSetAttribute(final AttributeAccess<Set<X>> access, final String attributeName, final Set<X> value, final AttributeType attributeType) {
        final SetAttributeDefinition setAttributeDefinition = SetAttributeDefinitionBuilder.of(attributeName, LABEL, attributeType).
                build();
        test(access, value, SetType.class, setAttributeDefinition, attrDef -> {
            final SetType receivedType = (SetType) attrDef.getAttributeType();
            assertThat(receivedType.getElementType()).isInstanceOf(attributeType.getClass());
        });
    }
}
