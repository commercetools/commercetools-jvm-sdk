package io.sphere.sdk.products;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.math.BigDecimal.valueOf;
import static java.util.Locale.FRENCH;
import static java.util.stream.Collectors.toList;

/**
 * Generates some products to perform integration search, as shows the following table:
 *
 *
 *                  | Product 1                     | Product 2
 * ---------------------------------------------------------------------------------
 * boolean          | true                          | false
 * text             | foo                           | bar
 * locText          | en: foo                       | en: bar
 * locText2         | en: foo, de: German foo       |
 * enum             | two                           | three
 * locEnum          | en: two                       | en: three
 * number           | 5                             | 10
 * money            | 500 EUR                       | 1000 USD
 * date             | 2001-09-11                    | 2002-10-12
 * time             | 22:05:09.203                  | 23:06:10.204
 * dateTime         | 2001-09-11T22:05:09.203+00:00 | 2002-10-12T23:06:10.204+00:00
 * reference        | productA                      | productB
 * boolean Set      | true, false                   | true
 * text Set         | foo, bar                      | foo
 * locText Set      | en: foo, en: bar              | en: foo
 * enum Set         | two, three                    | two
 * locEnum Set      | en: two, en: three            | en: two
 * number Set       | 5, 10                         | 5
 * money Set        | 500 EUR, 1000 USD             | 500 EUR
 * date Set         | 2001-09-11, 2002-10-12        | 2001-09-11
 * time Set         | 22:05:09.203, 23:06:10.204    | 22:05:09.203
 * dateTime Set     | 2001-..T22:.., 2002-..T23:..  | 2001-..T22:..
 * reference Set    | productA, productB            | productA
 *
 */
public class ProductsScenario1Fixtures {
    public static final String PRODUCT_TYPE_NAME = "ProductSearchTypeIT3";
    public static final String SKU1 = PRODUCT_TYPE_NAME + "-sku1";
    public static final String SKU2 = PRODUCT_TYPE_NAME + "-sku2";
    public static final String SKU_SOME_ID = PRODUCT_TYPE_NAME + "-sku-some-id";

    public static final String SKU_OTHER_ID = PRODUCT_TYPE_NAME + "-sku-other-id";
    public static final String ATTR_NAME_BOOLEAN = ("Boolean" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TEXT = ("Text" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_TEXT = ("LocText" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_TEXT2 = ("LocText2" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_ENUM = ("Enum" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_ENUM = ("LocEnum" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_NUMBER = ("Number" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_MONEY = ("Money" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_DATE = ("Date" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TIME = ("Time" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_DATE_TIME = ("DateTime" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_REF = ("Ref" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_BOOLEAN_SET = ("BooleanSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TEXT_SET = ("TextSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TEXT_SET2 = ("TextSet2" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_TEXT_SET = ("LocTextSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_ENUM_SET = ("EnumSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_ENUM_SET = ("LocEnumSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_NUMBER_SET = ("NumberSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_MONEY_SET = ("MoneySet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_DATE_SET = ("DateSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TIME_SET = ("TimeSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_DATE_TIME_SET = ("DateTimeSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_REF_SET = ("RefSet" + PRODUCT_TYPE_NAME);

    public static final String BOOL_TRUE = "true";
    public static final String BOOL_FALSE = "false";
    public static final String TEXT_FOO = "foo";
    public static final String TEXT_BAR = "bar";
    public static final String TEXT_ONE = "1";
    public static final String TEXT_TWO = "2";
    public static final String TEXT_THREE = "3";
    public static final LocalizedString LOC_TEXT_FOO = LocalizedString.ofEnglish("localized foo");
    public static final LocalizedString LOC_TEXT_FOO2 = LOC_TEXT_FOO.plus(GERMAN, "German foo");
    public static final LocalizedString LOC_TEXT_BAR = LocalizedString.ofEnglish("localized bar");
    public static final EnumValue ENUM_ONE = EnumValue.of("one-key", "one");
    public static final EnumValue ENUM_TWO = EnumValue.of("two-key", "two");
    public static final EnumValue ENUM_THREE = EnumValue.of("three-key", "three");
    public static final LocalizedEnumValue LOC_ENUM_ONE = LocalizedEnumValue.of("one-key", LocalizedString.of(GERMAN, "eins", FRENCH, "un"));
    public static final LocalizedEnumValue LOC_ENUM_TWO = LocalizedEnumValue.of("two-key", LocalizedString.of(GERMAN, "zwei", FRENCH, "deux"));
    public static final LocalizedEnumValue LOC_ENUM_THREE = LocalizedEnumValue.of("three-key", LocalizedString.of(GERMAN, "drei", FRENCH, "trois"));
    public static final BigDecimal NUMBER_5 = valueOf(5D);
    public static final BigDecimal NUMBER_10 = valueOf(10D);
    public static final MonetaryAmount MONEY_500_EUR = MoneyImpl.of(valueOf(500), "EUR");
    public static final MonetaryAmount MONEY_1000_USD = MoneyImpl.of(valueOf(1000), "USD");
    public static final LocalDate DATE_2001 = LocalDate.parse("2001-09-11");
    public static final LocalDate DATE_2002 = LocalDate.parse("2002-10-12");
    public static final LocalTime TIME_22H = LocalTime.parse("22:05:09.203");
    public static final LocalTime TIME_23H = LocalTime.parse("23:06:10.204");
    public static final ZonedDateTime DATE_TIME_2001_22H = ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00");
    public static final ZonedDateTime DATE_TIME_2002_23H = ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00");

    public static class Data extends Base {
        private final Product product1;
        private final Product product2;
        private final Product referencedProductA;
        private final Product referencedProductB;
        private final ProductType productType;

        public Data(final ProductType productType, final Product product1, final Product product2, final Product referencedProductA, final Product referencedProductB) {
            this.product1 = product1;
            this.product2 = product2;
            this.referencedProductA = referencedProductA;
            this.referencedProductB = referencedProductB;
            this.productType = productType;
        }

        public Product getProduct1() {
            return product1;
        }

        public Product getProduct2() {
            return product2;
        }

        public ProductType getProductType() {
            return productType;
        }

        public Product getReferencedProductA() {
            return referencedProductA;
        }

        public Product getReferencedProductB() {
            return referencedProductB;
        }

        public List<Product> getAllProducts() {
            return asList(getProduct1(), getProduct2(), getReferencedProductA(), getReferencedProductB());
        }
    }

    public static Data createScenario(final BlockingSphereClient client) {
        final ProductType productType = client.executeBlocking(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createProductType(client));

        final Query<Product> query = ProductQuery.of()
                .withPredicates(m -> m.masterData().staged().masterVariant().sku().isIn(asList(SKU1, SKU2, SKU_SOME_ID, SKU_OTHER_ID)));
        final List<Product> products = client.executeBlocking(query).getResults();

        final Function<String, Optional<Product>> findBySku =
                sku -> products.stream().filter(p -> sku.equals(p.getMasterData().getStaged().getMasterVariant().getSku())).findFirst();

        final Product productA = findBySku.apply(SKU_SOME_ID).orElseGet(() -> createTestProduct(client, "Some Id", ProductVariantDraftBuilder.of().sku(SKU_SOME_ID).build(), productType));
        final Product productB = findBySku.apply(SKU_OTHER_ID).orElseGet(() -> createTestProduct(client, "Other Id", ProductVariantDraftBuilder.of().sku(SKU_OTHER_ID).build(), productType));
        final Product product1 = findBySku.apply(SKU1).orElseGet(() -> createProduct1(client, productA, productB, productType));
        final Product product2 = findBySku.apply(SKU2).orElseGet(() -> createProduct2(client, productA, productB, productType));
        final Data data = new Data(productType, product1, product2, productA, productB);
        return data;
    }

    public static void destroy(final BlockingSphereClient client, final Data data) {
        final List<CompletionStage<Product>> productDeletions = data.getAllProducts().stream()
                .map(product -> client.execute(ProductDeleteCommand.of(product)))
                .collect(toList());
        productDeletions.forEach(stage -> SphereClientUtils.blockingWait(stage, 2, TimeUnit.SECONDS));
        client.executeBlocking(ProductTypeDeleteCommand.of(data.getProductType()));
    }

    public static Product createProduct1(final BlockingSphereClient client, final Referenceable<Product> referencedProductA, final Referenceable<Product> referencedProductB, final Referenceable<ProductType> productTypeReferenceable) {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(
                        AttributeAccess.ofBoolean().ofName(ATTR_NAME_BOOLEAN).draftOf(true),
                        AttributeAccess.ofText().ofName(ATTR_NAME_TEXT).draftOf(TEXT_FOO),
                        AttributeAccess.ofLocalizedString().ofName(ATTR_NAME_LOC_TEXT).draftOf(LOC_TEXT_FOO),
                        AttributeAccess.ofLocalizedString().ofName(ATTR_NAME_LOC_TEXT2).draftOf(LOC_TEXT_FOO2),
                        AttributeAccess.ofEnumValue().ofName(ATTR_NAME_ENUM).draftOf(ENUM_TWO),
                        AttributeAccess.ofLocalizedEnumValue().ofName(ATTR_NAME_LOC_ENUM).draftOf(LOC_ENUM_TWO),
                        AttributeAccess.ofDouble().ofName(ATTR_NAME_NUMBER).draftOf(NUMBER_5.doubleValue()),
                        AttributeAccess.ofMoney().ofName(ATTR_NAME_MONEY).draftOf(MONEY_500_EUR),
                        AttributeAccess.ofDate().ofName(ATTR_NAME_DATE).draftOf(DATE_2001),
                        AttributeAccess.ofTime().ofName(ATTR_NAME_TIME).draftOf(TIME_22H),
                        AttributeAccess.ofDateTime().ofName(ATTR_NAME_DATE_TIME).draftOf(DATE_TIME_2001_22H),
                        AttributeAccess.ofProductReference().ofName(ATTR_NAME_REF).draftOf(referencedProductA.toReference()),
                        AttributeAccess.ofBooleanSet().ofName(ATTR_NAME_BOOLEAN_SET).draftOf(asSet(true, false)),
                        AttributeAccess.ofStringSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet(TEXT_FOO, TEXT_BAR)),
                        AttributeAccess.ofStringSet().ofName(ATTR_NAME_TEXT_SET2).draftOf(asSet(TEXT_THREE, TEXT_ONE, TEXT_TWO)),
                        AttributeAccess.ofLocalizedStringSet().ofName(ATTR_NAME_LOC_TEXT_SET).draftOf(asSet(LOC_TEXT_FOO, LOC_TEXT_BAR)),
                        AttributeAccess.ofEnumValueSet().ofName(ATTR_NAME_ENUM_SET).draftOf(asSet(ENUM_TWO, ENUM_THREE)),
                        AttributeAccess.ofLocalizedEnumValueSet().ofName(ATTR_NAME_LOC_ENUM_SET).draftOf(asSet(LOC_ENUM_TWO, LOC_ENUM_THREE)),
                        AttributeAccess.ofDoubleSet().ofName(ATTR_NAME_NUMBER_SET).draftOf(asSet(NUMBER_5.doubleValue(), NUMBER_10.doubleValue())),
                        AttributeAccess.ofMoneySet().ofName(ATTR_NAME_MONEY_SET).draftOf(asSet(MONEY_500_EUR, MONEY_1000_USD)),
                        AttributeAccess.ofDateSet().ofName(ATTR_NAME_DATE_SET).draftOf(asSet(DATE_2001, DATE_2002)),
                        AttributeAccess.ofTimeSet().ofName(ATTR_NAME_TIME_SET).draftOf(asSet(TIME_22H, TIME_23H)),
                        AttributeAccess.ofDateTimeSet().ofName(ATTR_NAME_DATE_TIME_SET).draftOf(asSet(DATE_TIME_2001_22H, DATE_TIME_2002_23H)),
                        AttributeAccess.ofProductReferenceSet().ofName(ATTR_NAME_REF_SET).draftOf(asSet(referencedProductA.toReference(), referencedProductB.toReference())))
                .price(PriceDraft.of(new BigDecimal("23.45"), EUR))
                .sku(SKU1)
                .build();
        return createTestProduct(client, "Product one", masterVariant, productTypeReferenceable);
    }

    public static Product createProduct2(final BlockingSphereClient client, final Referenceable<Product> referencedProductA, final Referenceable<Product> referencedProductB, final Referenceable<ProductType> productTypeReferenceable) {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(
                        AttributeAccess.ofBoolean().ofName(ATTR_NAME_BOOLEAN).draftOf(false),
                        AttributeAccess.ofText().ofName(ATTR_NAME_TEXT).draftOf(TEXT_BAR),
                        AttributeAccess.ofLocalizedString().ofName(ATTR_NAME_LOC_TEXT).draftOf(LOC_TEXT_BAR),
                        AttributeAccess.ofEnumValue().ofName(ATTR_NAME_ENUM).draftOf(ENUM_THREE),
                        AttributeAccess.ofLocalizedEnumValue().ofName(ATTR_NAME_LOC_ENUM).draftOf(LOC_ENUM_THREE),
                        AttributeAccess.ofDouble().ofName(ATTR_NAME_NUMBER).draftOf(NUMBER_10.doubleValue()),
                        AttributeAccess.ofMoney().ofName(ATTR_NAME_MONEY).draftOf(MONEY_1000_USD),
                        AttributeAccess.ofDate().ofName(ATTR_NAME_DATE).draftOf(DATE_2002),
                        AttributeAccess.ofTime().ofName(ATTR_NAME_TIME).draftOf(TIME_23H),
                        AttributeAccess.ofDateTime().ofName(ATTR_NAME_DATE_TIME).draftOf(DATE_TIME_2002_23H),
                        AttributeAccess.ofProductReference().ofName(ATTR_NAME_REF).draftOf(referencedProductB.toReference()),
                        AttributeAccess.ofBooleanSet().ofName(ATTR_NAME_BOOLEAN_SET).draftOf(asSet(true)),
                        AttributeAccess.ofStringSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet(TEXT_FOO)),
                        AttributeAccess.ofLocalizedStringSet().ofName(ATTR_NAME_LOC_TEXT_SET).draftOf(asSet(LOC_TEXT_FOO)),
                        AttributeAccess.ofEnumValueSet().ofName(ATTR_NAME_ENUM_SET).draftOf(asSet(ENUM_TWO)),
                        AttributeAccess.ofLocalizedEnumValueSet().ofName(ATTR_NAME_LOC_ENUM_SET).draftOf(asSet(LOC_ENUM_TWO)),
                        AttributeAccess.ofDoubleSet().ofName(ATTR_NAME_NUMBER_SET).draftOf(asSet(NUMBER_5.doubleValue())),
                        AttributeAccess.ofMoneySet().ofName(ATTR_NAME_MONEY_SET).draftOf(asSet(MONEY_500_EUR)),
                        AttributeAccess.ofDateSet().ofName(ATTR_NAME_DATE_SET).draftOf(asSet(DATE_2001)),
                        AttributeAccess.ofTimeSet().ofName(ATTR_NAME_TIME_SET).draftOf(asSet(TIME_22H)),
                        AttributeAccess.ofDateTimeSet().ofName(ATTR_NAME_DATE_TIME_SET).draftOf(asSet(DATE_TIME_2001_22H)),
                        AttributeAccess.ofProductReferenceSet().ofName(ATTR_NAME_REF_SET).draftOf(asSet(referencedProductA.toReference())))
                .price(PriceDraft.of(new BigDecimal("46.80"), USD))
                .sku(SKU2)
                .build();
        return createTestProduct(client, "Product two", masterVariant, productTypeReferenceable);
    }


    public static ProductType createProductType(final BlockingSphereClient client) {
        final EnumAttributeType enumType = EnumAttributeType.of(ENUM_ONE, ENUM_TWO, ENUM_THREE);
        final LocalizedEnumAttributeType enumLocType = LocalizedEnumAttributeType.of(LOC_ENUM_ONE, LOC_ENUM_TWO, LOC_ENUM_THREE);
        final AttributeDefinition booleanAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_BOOLEAN, LocalizedString.ofEnglish(ATTR_NAME_BOOLEAN), BooleanAttributeType.of()).build();
        final AttributeDefinition textAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT, LocalizedString.ofEnglish(ATTR_NAME_TEXT), StringAttributeType.of()).build();
        final AttributeDefinition locTextAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT, LocalizedString.ofEnglish(ATTR_NAME_LOC_TEXT), LocalizedStringAttributeType.of()).build();
        final AttributeDefinition locTextAttrDef2 = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT2, LocalizedString.ofEnglish(ATTR_NAME_LOC_TEXT2), LocalizedStringAttributeType.of()).build();
        final AttributeDefinition enumAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_ENUM, LocalizedString.ofEnglish(ATTR_NAME_ENUM), enumType).build();
        final AttributeDefinition locEnumAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_ENUM, LocalizedString.ofEnglish(ATTR_NAME_LOC_ENUM), enumLocType).build();
        final AttributeDefinition numberAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_NUMBER, LocalizedString.ofEnglish(ATTR_NAME_NUMBER), NumberAttributeType.of()).build();
        final AttributeDefinition moneyAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_MONEY, LocalizedString.ofEnglish(ATTR_NAME_MONEY), MoneyAttributeType.of()).build();
        final AttributeDefinition dateAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE, LocalizedString.ofEnglish(ATTR_NAME_DATE), DateAttributeType.of()).build();
        final AttributeDefinition timeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TIME, LocalizedString.ofEnglish(ATTR_NAME_TIME), TimeAttributeType.of()).build();
        final AttributeDefinition dateTimeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_TIME, LocalizedString.ofEnglish(ATTR_NAME_DATE_TIME), DateTimeAttributeType.of()).build();
        final AttributeDefinition refAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_REF, LocalizedString.ofEnglish(ATTR_NAME_REF), ReferenceAttributeType.of(Product.referenceTypeId())).build();
        final AttributeDefinition booleanSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_BOOLEAN_SET, LocalizedString.ofEnglish(ATTR_NAME_BOOLEAN_SET), SetAttributeType.of(BooleanAttributeType.of())).build();
        final AttributeDefinition textSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT_SET, LocalizedString.ofEnglish(ATTR_NAME_TEXT_SET), SetAttributeType.of(StringAttributeType.of())).build();
        final AttributeDefinition textSetAttrDef2 = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT_SET2, LocalizedString.ofEnglish(ATTR_NAME_TEXT_SET2), SetAttributeType.of(StringAttributeType.of())).build();
        final AttributeDefinition locTextSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT_SET, LocalizedString.ofEnglish(ATTR_NAME_LOC_TEXT_SET), SetAttributeType.of(LocalizedStringAttributeType.of())).build();
        final AttributeDefinition enumSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_ENUM_SET, LocalizedString.ofEnglish(ATTR_NAME_ENUM_SET), SetAttributeType.of(enumType)).build();
        final AttributeDefinition locEnumSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_ENUM_SET, LocalizedString.ofEnglish(ATTR_NAME_LOC_ENUM_SET), SetAttributeType.of(enumLocType)).build();
        final AttributeDefinition numberSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_NUMBER_SET, LocalizedString.ofEnglish(ATTR_NAME_NUMBER_SET), SetAttributeType.of(NumberAttributeType.of())).build();
        final AttributeDefinition moneySetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_MONEY_SET, LocalizedString.ofEnglish(ATTR_NAME_MONEY_SET), SetAttributeType.of(MoneyAttributeType.of())).build();
        final AttributeDefinition dateSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_SET, LocalizedString.ofEnglish(ATTR_NAME_DATE_SET), SetAttributeType.of(DateAttributeType.of())).build();
        final AttributeDefinition timeSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TIME_SET, LocalizedString.ofEnglish(ATTR_NAME_TIME_SET), SetAttributeType.of(TimeAttributeType.of())).build();
        final AttributeDefinition dateTimeSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_TIME_SET, LocalizedString.ofEnglish(ATTR_NAME_DATE_TIME_SET), SetAttributeType.of(DateTimeAttributeType.of())).build();
        final AttributeDefinition refSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_REF_SET, LocalizedString.ofEnglish(ATTR_NAME_REF_SET), SetAttributeType.of(ReferenceAttributeType.of(Product.referenceTypeId()))).build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), PRODUCT_TYPE_NAME, "", asList(booleanAttrDef, textAttrDef,
                locTextAttrDef, enumAttrDef, locEnumAttrDef, numberAttrDef, moneyAttrDef, dateAttrDef, timeAttrDef, dateTimeAttrDef,
                refAttrDef, booleanSetAttrDef, textSetAttrDef, textSetAttrDef2, locTextSetAttrDef, locTextAttrDef2, enumSetAttrDef, locEnumSetAttrDef, numberSetAttrDef,
                moneySetAttrDef, dateSetAttrDef, timeSetAttrDef, dateTimeSetAttrDef, refSetAttrDef));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        return client.executeBlocking(productTypeCreateCommand);
    }

    public static Product createTestProduct(final BlockingSphereClient client, final String name, final ProductVariantDraft masterVariant, final Referenceable<ProductType> productTypeReferenceable) {
        final LocalizedString locName = LocalizedString.ofEnglish(name);
        final ProductDraft productDraft = ProductDraftBuilder.of(productTypeReferenceable.toReference(), locName, locName.slugifiedUnique(), masterVariant).build();
        return client.executeBlocking(ProductCreateCommand.of(productDraft));
    }
}
