package io.sphere.sdk.producttypes;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQueryModel;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.attributes.*;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.*;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public final class ProductTypeAttributesTest extends IntegrationTest {
    public static final List<LocalizedEnumValue> LOCALIZED_ENUM_VALUES = asList(LocalizedEnumValue.of("key1", en("value1")), LocalizedEnumValue.of("key2", en("value2")));
    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MULTI_LINE;
    public static final LocalizedStrings LABEL = en("label");
    public static final List<PlainEnumValue> PLAIN_ENUM_VALUES = asList(PlainEnumValue.of("key1", "value1"), PlainEnumValue.of("key2", "value2"));
    public static final ProductTypeDraft tshirt = new TShirtProductTypeDraftSupplier("t-shirt").get();
    public static final String distractorName = "distractor";

    @Test
    public void booleanAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofBoolean(), AttributeAccess.ofBooleanSet(), asSet(true, false),
                BooleanType.of(), AttributeDefinitionBuilder.of("boolean-attribute", LABEL, BooleanType.of()).build());
    }

    @Test
    public void nestedAttribute() throws Exception {
        final AttributeGetterSetter<Double> sizeAttr = AttributeAccess.ofDouble().ofName("size-nested");
        final AttributeGetterSetter<String> brandAttr = AttributeAccess.ofText().ofName("brand-nested");

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of("test-sub-attribute", "nested attribute test",
                asList(
                        AttributeDefinitionBuilder.of(sizeAttr.getName(), LocalizedStrings.of(Locale.ENGLISH, "Size"), NumberType.of()).build(),
                        AttributeDefinitionBuilder.of(brandAttr.getName(), LocalizedStrings.of(Locale.ENGLISH, "Brand"), TextType.of()).build()));

        withProductType(client(), () -> productTypeDraft, nestedProductType -> {
            final AttributeContainer adidas = AttributeContainer.of(
                    asList(Attribute.of(sizeAttr, 12D), Attribute.of(brandAttr, "Adidas")));
            final AttributeContainer nike = AttributeContainer.of(
                    asList(Attribute.of(sizeAttr, 11.5D), Attribute.of(brandAttr, "Nike")));

            final AttributeType type = NestedType.of(nestedProductType);

            testSingleAndSet(AttributeAccess.ofNested(), AttributeAccess.ofNestedSet(), asSet(adidas, nike),
                    type, AttributeDefinitionBuilder.of("nested-attribute", LABEL, type).searchable(false).build());
        });
    }

    @Test
    public void textAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofText(), AttributeAccess.ofTextSet(), asSet("hello", "world"),
                TextType.of(), AttributeDefinitionBuilder.of("text-attribute", LABEL, TextType.of()).inputHint(TEXT_INPUT_HINT).build());
    }

    @Test
    public void localizedStringsAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofLocalizedStrings(), AttributeAccess.ofLocalizedStringsSet(),
                asSet(LocalizedStrings.of(ENGLISH, "hello"), LocalizedStrings.of(ENGLISH, "world")),
                LocalizedStringsType.of(),
                AttributeDefinitionBuilder.of("localized-text-attribute", LABEL, LocalizedStringsType.of()).inputHint(TEXT_INPUT_HINT).build());
    }

    @Test
    public void enumAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofPlainEnumValue(), AttributeAccess.ofPlainEnumValueSet(),
                new HashSet<>(PLAIN_ENUM_VALUES),
                EnumType.of(PLAIN_ENUM_VALUES),
                AttributeDefinitionBuilder.of("enum-attribute", LABEL, EnumType.of(PLAIN_ENUM_VALUES)).build());
    }

    @Test
    public void localizedEnumAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofLocalizedEnumValue(), AttributeAccess.ofLocalizedEnumValueSet(),
                new HashSet<>(LOCALIZED_ENUM_VALUES),
                LocalizedEnumType.of(LOCALIZED_ENUM_VALUES),
                AttributeDefinitionBuilder.of("lenum-attribute", LABEL, LocalizedEnumType.of(LOCALIZED_ENUM_VALUES)).build());
    }

    @Test
    public void numberAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofDouble(), AttributeAccess.ofDoubleSet(),
                asSet(1.0, 1.5, 2.0),
                NumberType.of(),
                AttributeDefinitionBuilder.of("number-attribute", LABEL, NumberType.of()).build());
    }

    @Test
    public void moneyAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofMoney(), AttributeAccess.ofMoneySet(),
                asSet(MoneyImpl.of(355, EUR), MoneyImpl.of(98774, EUR)),
                MoneyType.of(),
                AttributeDefinitionBuilder.of("money-attribute", LABEL, MoneyType.of()).build());
    }

    @Test
    public void dateAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofDate(), AttributeAccess.ofDateSet(),
                asSet(LocalDate.now(), LocalDate.now().plus(5, ChronoUnit.DAYS)),
                DateType.of(),
                AttributeDefinitionBuilder.of("date-attribute", LABEL, DateType.of()).build());
    }

    @Test
    public void timeAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofTime(), AttributeAccess.ofTimeSet(),
                asSet(LocalTime.now(), LocalTime.now().plus(3, ChronoUnit.MINUTES)),
                TimeType.of(),
                AttributeDefinitionBuilder.of("time-attribute", LABEL, TimeType.of()).build());
    }

    @Test
    public void dateTimeAttribute() throws Exception {
        final ZonedDateTime now = SphereTestUtils.now().withZoneSameInstant(ZoneOffset.UTC);
        testSingleAndSet(AttributeAccess.ofDateTime(), AttributeAccess.ofDateTimeSet(),
                asSet(now, now.plus(3, ChronoUnit.DAYS)),
                DateTimeType.of(),
                AttributeDefinitionBuilder.of("datetime-attribute", LABEL, DateTimeType.of()).build());
    }

    @Test
    public void productReferenceAttribute() throws Exception {
        withEmptyProductType(client(), "productReferenceAttribute-testcase", productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, LABEL, SphereTestUtils.randomSlug(), masterVariant).build();
            final Product product = execute(ProductCreateCommand.of(productDraft));
            testSingleAndSet(AttributeAccess.ofProductReference(), AttributeAccess.ofProductReferenceSet(),
                    asSet(product.toReference().filled(Optional.<Product>empty())),
                    ReferenceType.ofProduct(),
                    AttributeDefinitionBuilder.of("productReference", LABEL, ReferenceType.ofProduct()).build());
        });
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
        executeTest(SetType.class, AttributeDefinitionBuilder
                .of("set-of-localized-enum-attribute", LABEL, SetType.of(LocalizedEnumType.of(LOCALIZED_ENUM_VALUES)))
                .searchable(false)
                .build(), attributeDefinitionFromServer -> {
            final SetType setType = (SetType) attributeDefinitionFromServer.getAttributeType();
            final LocalizedEnumType elementType = (LocalizedEnumType) setType.getElementType();

            assertThat(elementType.getValues()).isEqualTo(LOCALIZED_ENUM_VALUES);
        });
    }

    @Test
    public void queryByName() throws Exception {
        withTShirtProductType(type -> {
            ProductType productType = execute(ProductTypeQuery.of().byName("t-shirt")).head().get();
            Optional<AttributeDefinition> sizeAttribute = productType.getAttribute("size");
            final List<PlainEnumValue> possibleSizeValues = sizeAttribute.
                    map(attrib -> ((EnumType) attrib.getAttributeType()).getValues()).
                    orElse(Collections.<PlainEnumValue>emptyList());
            final List<PlainEnumValue> expected =
                    asList(PlainEnumValue.of("S", "S"), PlainEnumValue.of("M", "M"), PlainEnumValue.of("X", "X"));
            assertThat(possibleSizeValues).isEqualTo(expected);
        });
    }

    @Test
    public void queryByAttributeType() throws Exception {
        final String attributeTypeName = "enum";
        final Query<ProductType> queryForEnum = ProductTypeQuery.of().withPredicate(hasAttributeType(attributeTypeName));
        withDistractorProductType(x -> {//contains no enum attribute, so it should not be included in the result
            withTShirtProductType(y -> {
                final java.util.function.Predicate<ProductType> containsEnumAttr = productType -> productType.getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeTypeName));
                final List<ProductType> productTypes = execute(queryForEnum).getResults();
                assertThat(productTypes.stream().allMatch(containsEnumAttr));
            });
        });
    }

    @Test
    public void createTextAttribute() throws Exception {
        executeTest(TextType.class, AttributeDefinitionBuilder.of("text-attribute", LABEL, TextType.of()).
                inputHint(TEXT_INPUT_HINT).
                attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).
                searchable(false).
                required(true).
                build(), attributeDefinitionFromServer -> {
            assertThat(attributeDefinitionFromServer.getIsRequired()).isTrue();
            assertThat(attributeDefinitionFromServer.getAttributeConstraint()).isEqualTo(AttributeConstraint.COMBINATION_UNIQUE);
            assertThat(attributeDefinitionFromServer.getIsSearchable()).isFalse();
            assertThat(attributeDefinitionFromServer.getInputHint()).isEqualTo(TEXT_INPUT_HINT);
        });
    }

    @Test
    public void createLocalizedStringsAttribute() throws Exception {
        executeTest(LocalizedStringsType.class, AttributeDefinitionBuilder.of("localized-text-attribute", LABEL, LocalizedStringsType.of()).
                inputHint(TEXT_INPUT_HINT).
                attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).
                searchable(false).
                required(true).
                build(), attributeDefinition -> {
            assertThat(attributeDefinition.getIsRequired()).isTrue();
            assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.COMBINATION_UNIQUE);
            assertThat(attributeDefinition.getIsSearchable()).isFalse();
            assertThat(attributeDefinition.getInputHint()).isEqualTo(TEXT_INPUT_HINT);
        });
    }

    @Test
    public void enumTypeContainsThePossibleValues() throws Exception {
        executeTest(EnumType.class, AttributeDefinitionBuilder.of("enum-attribute", LABEL, EnumType.of(PLAIN_ENUM_VALUES)).
                build(), attributeDefinition -> {
            final EnumType attributeType = (EnumType) attributeDefinition.getAttributeType();
            assertThat(attributeType.getValues()).isEqualTo(PLAIN_ENUM_VALUES);
        });
    }

    private QueryPredicate<ProductType> hasAttributeType(final String attributeTypeName) {
        return ProductTypeQueryModel.of().attributes().type().name().is(attributeTypeName);
    }

    private void withDistractorProductType(final Consumer<ProductType> consumer) {
        withProductType(client(), () -> ProductTypeDraft.of(distractorName, "desc", Collections.emptyList()), consumer);
    }

    private void withTShirtProductType(final Consumer<ProductType> consumer) {
        withProductType(client(), () -> tshirt, consumer);
    }

    private void testSetAttribute(final String attributeName, final AttributeType attributeType) {
        executeTest(SetType.class, AttributeDefinitionBuilder.of(attributeName, LABEL, SetType.of(attributeType))
                .searchable(false)
                .build(), attrDef -> {
            final SetType receivedType = (SetType) attrDef.getAttributeType();
            assertThat(receivedType.getElementType()).isInstanceOf(attributeType.getClass());
        });
    }

    private void testReferenceAttribute(final String attributeName, final ReferenceType referenceType) {
        executeTest(ReferenceType.class, AttributeDefinitionBuilder.of(attributeName, LABEL, referenceType).
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

        final ProductTypeCreateCommand command = ProductTypeCreateCommand.of(ProductTypeDraft.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = execute(command);
        assertThat(productType.getName()).isEqualTo(productTypeName);
        assertThat(productType.getDescription()).isEqualTo(productTypeDescription);
        assertThat(productType.getAttributes()).hasSize(1);
        final AttributeDefinition fetchedAttributeDefinition = productType.getAttributes().get(0);

        assertThat(fetchedAttributeDefinition.getName()).isEqualTo(attributeName);
        assertThat(fetchedAttributeDefinition.getLabel()).isEqualTo(en("label"));
        assertThat(fetchedAttributeDefinition.getAttributeType()).isInstanceOf(attributeTypeClass);


        furtherAttributeDefinitionAssertions.accept(fetchedAttributeDefinition);

        final AttributeGetterSetter<X> attributeGetterSetter = access.ofName(attributeName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().attributes(attributeGetterSetter.valueOf(exampleValue)).build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, LocalizedStrings.of(ENGLISH, "product to test attributes"), SphereTestUtils.randomSlug(), masterVariant).build();
        final Product product = execute(ProductCreateCommand.of(productDraft));
        final X actualAttributeValue = product.getMasterData().getStaged().getMasterVariant().getAttribute(attributeGetterSetter).get();

        assertThat(exampleValue).isEqualTo(actualAttributeValue);

        final Boolean found = product.getMasterData().getStaged().getMasterVariant().getAttribute(attributeName).get()
                .<Boolean>collect(fetchedAttributeDefinition)
                .ifIs(access, x -> true)
                .getValue().orElse(false);
        assertThat(found).overridingErrorMessage("the attribute type should be recognized").isTrue();

        execute(ProductDeleteCommand.of(product));
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

        final ProductTypeCreateCommand command = ProductTypeCreateCommand.of(ProductTypeDraft.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = execute(command);
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

    private <X> void testSetAttribute(final AttributeAccess<Set<X>> access, final String attributeName, final Set<X> value, final AttributeType attributeType) {
        final AttributeDefinition setAttributeDefinition =
                AttributeDefinitionBuilder.of(attributeName, LABEL, SetType.of(attributeType)).searchable(false).build();
        test(access, value, SetType.class, setAttributeDefinition, attrDef -> {
            final SetType receivedType = (SetType) attrDef.getAttributeType();
            assertThat(receivedType.getElementType()).isInstanceOf(attributeType.getClass());
        });
    }

    private <X> void testSingleAndSet(final AttributeAccess<X> singleAccess, final AttributeAccess<Set<X>> setAccess, final Set<X> demoSet, final AttributeType attributeType, final AttributeDefinition singleDefinition) {
        final X oneExample = demoSet.iterator().next();
        test(singleAccess, oneExample, attributeType.getClass(), singleDefinition);
        testSetAttribute(setAccess, "set-of-" + singleDefinition.getName(), demoSet, attributeType);
    }

    protected void cleanUpByName(final String name) {
        cleanUpByName(asList(name));
    }

    protected void cleanUpByName(final List<String> names) {
        execute(ProductTypeQuery.of().withPredicate(ProductTypeQueryModel.of().name().isIn(names))).getResults().forEach(item -> ProductFixtures.deleteProductsAndProductType(client(), item));
    }
}
