package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQueryModel;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import net.jcip.annotations.NotThreadSafe;
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
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public  class ProductTypeAttributesIntegrationTest extends IntegrationTest {
    public static final List<LocalizedEnumValue> LOCALIZED_ENUM_VALUES = asList(LocalizedEnumValue.of("key1", en("value1")), LocalizedEnumValue.of("key2", en("value2")));
    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MULTI_LINE;
    public static final LocalizedString LABEL = en("label");
    public static final List<EnumValue> PLAIN_ENUM_VALUES = asList(EnumValue.of("key1", "value1"), EnumValue.of("key2", "value2"));
    public static final ProductTypeDraft tshirt = new TShirtProductTypeDraftSupplier("t-shirt").get();
    public static final String distractorName = "distractor";

    @Test
    public void booleanAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofBoolean(), AttributeAccess.ofBooleanSet(), asSet(true, false),
                BooleanAttributeType.of(), AttributeDefinitionBuilder.of("boolean-attribute", LABEL, BooleanAttributeType.of()).build());
    }

    @Test
    public void nestedAttribute() throws Exception {
        final NamedAttributeAccess<Double> sizeAttr = AttributeAccess.ofDouble().ofName("size-nested");
        final NamedAttributeAccess<String> brandAttr = AttributeAccess.ofString().ofName("brand-nested");

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), "test-sub-attribute", "nested attribute test", asList(
                AttributeDefinitionBuilder.of(sizeAttr.getName(), LocalizedString.of(Locale.ENGLISH, "Size"), NumberAttributeType.of()).build(),
                AttributeDefinitionBuilder.of(brandAttr.getName(), LocalizedString.of(Locale.ENGLISH, "Brand"), StringAttributeType.of()).build()));

        withProductType(client(), () -> productTypeDraft, nestedProductType -> {
            final AttributeContainer adidas = AttributeContainer.of(
                    asList(Attribute.of(sizeAttr, 12D), Attribute.of(brandAttr, "Adidas")));
            final AttributeContainer nike = AttributeContainer.of(
                    asList(Attribute.of(sizeAttr, 11.5D), Attribute.of(brandAttr, "Nike")));

            final AttributeType type = NestedAttributeType.of(nestedProductType);

            testSingleAndSet(AttributeAccess.ofNested(), AttributeAccess.ofNestedSet(), asSet(adidas, nike),
                    type, AttributeDefinitionBuilder.of("nested-attribute", LABEL, type).searchable(false).build());
        });
    }

    @Test
    public void textAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofString(), AttributeAccess.ofStringSet(), asSet("hello", "world"),
                StringAttributeType.of(), AttributeDefinitionBuilder.of("text-attribute", LABEL, StringAttributeType.of()).inputHint(TEXT_INPUT_HINT).build());
    }

    @Test
    public void localizedStringAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofLocalizedString(), AttributeAccess.ofLocalizedStringSet(),
                asSet(LocalizedString.of(ENGLISH, "hello"), LocalizedString.of(ENGLISH, "world")),
                LocalizedStringAttributeType.of(),
                AttributeDefinitionBuilder.of("localized-text-attribute", LABEL, LocalizedStringAttributeType.of()).inputHint(TEXT_INPUT_HINT).build());
    }

    @Test
    public void enumAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofEnumValue(), AttributeAccess.ofEnumValueSet(),
                new HashSet<>(PLAIN_ENUM_VALUES),
                EnumAttributeType.of(PLAIN_ENUM_VALUES),
                AttributeDefinitionBuilder.of("enum-attribute", LABEL, EnumAttributeType.of(PLAIN_ENUM_VALUES)).build());
    }

    @Test
    public void localizedEnumAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofLocalizedEnumValue(), AttributeAccess.ofLocalizedEnumValueSet(),
                new HashSet<>(LOCALIZED_ENUM_VALUES),
                LocalizedEnumAttributeType.of(LOCALIZED_ENUM_VALUES),
                AttributeDefinitionBuilder.of("lenum-attribute", LABEL, LocalizedEnumAttributeType.of(LOCALIZED_ENUM_VALUES)).build());
    }

    @Test
    public void numberAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofDouble(), AttributeAccess.ofDoubleSet(),
                asSet(1.0, 1.5, 2.0),
                NumberAttributeType.of(),
                AttributeDefinitionBuilder.of("number-attribute", LABEL, NumberAttributeType.of()).build());
    }

    @Test
    public void integerAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofInteger(), AttributeAccess.ofIntegerSet(),
                asSet(1, 2),
                NumberAttributeType.of(),
                AttributeDefinitionBuilder.of("number-attribute", LABEL, NumberAttributeType.of()).build());
    }

    @Test
    public void longAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofLong(), AttributeAccess.ofLongSet(),
                asSet(1L, 2L),
                NumberAttributeType.of(),
                AttributeDefinitionBuilder.of("number-attribute", LABEL, NumberAttributeType.of()).build());
    }

    @Test
    public void moneyAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofMoney(), AttributeAccess.ofMoneySet(),
                asSet(MoneyImpl.of(355, EUR), MoneyImpl.of(98774, EUR)),
                MoneyAttributeType.of(),
                AttributeDefinitionBuilder.of("money-attribute", LABEL, MoneyAttributeType.of()).build());
    }

    @Test
    public void dateAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofDate(), AttributeAccess.ofDateSet(),
                asSet(LocalDate.now(), LocalDate.now().plus(5, ChronoUnit.DAYS)),
                DateAttributeType.of(),
                AttributeDefinitionBuilder.of("date-attribute", LABEL, DateAttributeType.of()).build());
    }

    @Test
    public void timeAttribute() throws Exception {
        testSingleAndSet(AttributeAccess.ofTime(), AttributeAccess.ofTimeSet(),
                asSet(LocalTime.now(), LocalTime.now().plus(3, ChronoUnit.MINUTES)),
                TimeAttributeType.of(),
                AttributeDefinitionBuilder.of("time-attribute", LABEL, TimeAttributeType.of()).build());
    }

    @Test
    public void dateTimeAttribute() throws Exception {
        final ZonedDateTime now = SphereTestUtils.now().withZoneSameInstant(ZoneOffset.UTC);
        testSingleAndSet(AttributeAccess.ofDateTime(), AttributeAccess.ofDateTimeSet(),
                asSet(now, now.plus(3, ChronoUnit.DAYS)),
                DateTimeAttributeType.of(),
                AttributeDefinitionBuilder.of("datetime-attribute", LABEL, DateTimeAttributeType.of()).build());
    }

    @Test
    public void productReferenceAttribute() throws Exception {
        withEmptyProductType(client(), "productReferenceAttribute-testcase", productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, LABEL, SphereTestUtils.randomSlug(), masterVariant).build();
            final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
            testSingleAndSet(AttributeAccess.ofProductReference(), AttributeAccess.ofProductReferenceSet(),
                    asSet(product.toReference().filled(null)),
                    ReferenceAttributeType.ofProduct(),
                    AttributeDefinitionBuilder.of("productReference", LABEL, ReferenceAttributeType.ofProduct()).build());
        });
    }

    @Test
    public void flatReferenceAttributes() throws Exception {
        testReferenceAttribute("product-type-reference", ReferenceAttributeType.ofProductType());
        testReferenceAttribute("product-reference", ReferenceAttributeType.ofProduct());
        testReferenceAttribute("category-reference", ReferenceAttributeType.ofCategory());
        testReferenceAttribute("channel-reference", ReferenceAttributeType.ofChannel());
    }

    @Test
    public void setReferenceAttributes() throws Exception {
        testSetAttribute("set-of-product-type-reference", ReferenceAttributeType.ofProductType());
        testSetAttribute("set-of-product-reference", ReferenceAttributeType.ofProduct());
        testSetAttribute("set-of-category-reference", ReferenceAttributeType.ofCategory());
        testSetAttribute("set-of-channel-reference", ReferenceAttributeType.ofChannel());
    }

    @Test
    public void createSetOfLocalizedEnumAttribute() throws Exception {
        executeTest(SetAttributeType.class, AttributeDefinitionBuilder
                .of("set-of-localized-enum-attribute", LABEL, SetAttributeType.of(LocalizedEnumAttributeType.of(LOCALIZED_ENUM_VALUES)))
                .searchable(false)
                .build(), attributeDefinitionFromServer -> {
            final SetAttributeType setType = (SetAttributeType) attributeDefinitionFromServer.getAttributeType();
            final LocalizedEnumAttributeType elementType = (LocalizedEnumAttributeType) setType.getElementType();

            assertThat(elementType.getValues()).isEqualTo(LOCALIZED_ENUM_VALUES);
        });
    }

    @Test
    public void queryByName() throws Exception {
        withTShirtProductType(type -> {
            final ProductType productType = client().executeBlocking(ProductTypeQuery.of().byName("t-shirt")).head().get();
            final Optional<AttributeDefinition> sizeAttribute = productType.findAttribute("size");
            final List<EnumValue> possibleSizeValues = sizeAttribute.
                    map(attrib -> ((EnumAttributeType) attrib.getAttributeType()).getValues()).
                    orElse(Collections.<EnumValue>emptyList());
            final List<EnumValue> expected =
                    asList(EnumValue.of("S", "S"), EnumValue.of("M", "M"), EnumValue.of("X", "X"));
            assertThat(possibleSizeValues).isEqualTo(expected);
        });
    }

    @Test
    public void queryByAttributeType() throws Exception {
        final String attributeTypeName = "enum";
        final Query<ProductType> queryForEnum = ProductTypeQuery.of().withPredicates(hasAttributeType(attributeTypeName));
        withDistractorProductType(x -> {//contains no enum attribute, so it should not be included in the result
            withTShirtProductType(y -> {
                final java.util.function.Predicate<ProductType> containsEnumAttr = productType -> productType.getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeTypeName));
                final List<ProductType> productTypes = client().executeBlocking(queryForEnum).getResults();
                assertThat(productTypes.stream().allMatch(containsEnumAttr));
            });
        });
    }

    @Test
    public void createTextAttribute() throws Exception {
        executeTest(StringAttributeType.class, AttributeDefinitionBuilder.of("text-attribute", LABEL, StringAttributeType.of()).
                inputHint(TEXT_INPUT_HINT).
                attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).
                searchable(false).
                required(true).
                build(), attributeDefinitionFromServer -> {
            assertThat(attributeDefinitionFromServer.isRequired()).isTrue();
            assertThat(attributeDefinitionFromServer.getAttributeConstraint()).isEqualTo(AttributeConstraint.COMBINATION_UNIQUE);
            assertThat(attributeDefinitionFromServer.isSearchable()).isFalse();
            assertThat(attributeDefinitionFromServer.getInputHint()).isEqualTo(TEXT_INPUT_HINT);
        });
    }

    @Test
    public void createLocalizedStringAttribute() throws Exception {
        executeTest(LocalizedStringAttributeType.class, AttributeDefinitionBuilder.of("localized-text-attribute", LABEL, LocalizedStringAttributeType.of()).
                inputHint(TEXT_INPUT_HINT).
                attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).
                searchable(false).
                required(true).
                build(), attributeDefinition -> {
            assertThat(attributeDefinition.isRequired()).isTrue();
            assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.COMBINATION_UNIQUE);
            assertThat(attributeDefinition.isSearchable()).isFalse();
            assertThat(attributeDefinition.getInputHint()).isEqualTo(TEXT_INPUT_HINT);
        });
    }

    @Test
    public void enumTypeContainsThePossibleValues() throws Exception {
        executeTest(EnumAttributeType.class, AttributeDefinitionBuilder.of("enum-attribute", LABEL, EnumAttributeType.of(PLAIN_ENUM_VALUES)).
                build(), attributeDefinition -> {
            final EnumAttributeType attributeType = (EnumAttributeType) attributeDefinition.getAttributeType();
            assertThat(attributeType.getValues()).isEqualTo(PLAIN_ENUM_VALUES);
        });
    }

    private QueryPredicate<ProductType> hasAttributeType(final String attributeTypeName) {
        return ProductTypeQueryModel.of().attributes().type().name().is(attributeTypeName);
    }

    private void withDistractorProductType(final Consumer<ProductType> consumer) {
        withProductType(client(), () -> ProductTypeDraft.of(randomKey(), distractorName, "desc", Collections.emptyList()), consumer);
    }

    private void withTShirtProductType(final Consumer<ProductType> consumer) {
        withProductType(client(), () -> tshirt, consumer);
    }

    private void testSetAttribute(final String attributeName, final AttributeType attributeType) {
        executeTest(SetAttributeType.class, AttributeDefinitionBuilder.of(attributeName, LABEL, SetAttributeType.of(attributeType))
                .searchable(false)
                .build(), attrDef -> {
            final SetAttributeType receivedType = (SetAttributeType) attrDef.getAttributeType();
            assertThat(receivedType.getElementType()).isInstanceOf(attributeType.getClass());
        });
    }

    private void testReferenceAttribute(final String attributeName, final ReferenceAttributeType referenceType) {
        executeTest(ReferenceAttributeType.class, AttributeDefinitionBuilder.of(attributeName, LABEL, referenceType).
                build(), attrDef -> {
            final ReferenceAttributeType receivedType = (ReferenceAttributeType) attrDef.getAttributeType();
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

        final ProductTypeCreateCommand command = ProductTypeCreateCommand.of(ProductTypeDraft.of(randomKey(), productTypeName, productTypeDescription, attributes));
        final ProductType productType = client().executeBlocking(command);
        assertThat(productType.getName()).isEqualTo(productTypeName);
        assertThat(productType.getDescription()).isEqualTo(productTypeDescription);
        assertThat(productType.getAttributes()).hasSize(1);
        final AttributeDefinition fetchedAttributeDefinition = productType.getAttributes().get(0);

        assertThat(fetchedAttributeDefinition.getName()).isEqualTo(attributeName);
        assertThat(fetchedAttributeDefinition.getLabel()).isEqualTo(en("label"));
        assertThat(fetchedAttributeDefinition.getAttributeType()).isInstanceOf(attributeTypeClass);


        furtherAttributeDefinitionAssertions.accept(fetchedAttributeDefinition);

        final NamedAttributeAccess<X> namedAttributeAccess = access.ofName(attributeName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().attributes(namedAttributeAccess.draftOf(exampleValue)).build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, LocalizedString.of(ENGLISH, "product to test attributes"), SphereTestUtils.randomSlug(), masterVariant).build();
        final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
        final X actualAttributeValue = product.getMasterData().getStaged().getMasterVariant().findAttribute(namedAttributeAccess).get();

        assertThat(exampleValue).isEqualTo(actualAttributeValue);

        final Boolean found = AttributeExtraction.<Boolean>of(fetchedAttributeDefinition, product.getMasterData().getStaged().getMasterVariant().getAttribute(attributeName))
                .ifIs(access, x -> true)
                .findValue().orElse(false);
        assertThat(found).overridingErrorMessage("the attribute type should be recognized").isTrue();

        client().executeBlocking(ProductDeleteCommand.of(product));
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

        final ProductTypeCreateCommand command = ProductTypeCreateCommand.of(ProductTypeDraft.of(randomKey(), productTypeName, productTypeDescription, attributes));
        final ProductType productType = client().executeBlocking(command);
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
                AttributeDefinitionBuilder.of(attributeName, LABEL, SetAttributeType.of(attributeType)).searchable(false).build();
        test(access, value, SetAttributeType.class, setAttributeDefinition, attrDef -> {
            final SetAttributeType receivedType = (SetAttributeType) attrDef.getAttributeType();
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
        client().executeBlocking(ProductTypeQuery.of().withPredicates(ProductTypeQueryModel.of().name().isIn(names))).getResults().forEach(item -> ProductFixtures.deleteProductsProductTypeAndProductDiscounts(client(), item));
    }
}
