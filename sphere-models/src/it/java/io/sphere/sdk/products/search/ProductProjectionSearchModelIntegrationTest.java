package io.sphere.sdk.products.search;

import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.LocalizedString.ofEnglishLocale;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.math.BigDecimal.valueOf;
import static java.util.Locale.FRENCH;

/**
 * Generates some products to perform integration search, as shows the following table:
 *
 *
 *                  | Product 1                     | Product 2
 * ---------------------------------------------------------------------------------
 * boolean          | true                          | false
 * text             | foo                           | bar
 * locText          | en: foo                       | en: bar
 * enum             | two                           | three
 * locEnum          | en: two                       | en: three
 * number           | 5                             | 10
 * money            | 500 EUR                       | 1000 USD
 * date             | 2001-09-11                    | 2002-10-12
 * time             | 22:05:09.203                  | 23:06:10.204
 * dateTime         | 2001-09-11T22:05:09.203+00:00 | 2002-10-12T23:06:10.204+00:00
 * reference        | productSomeId                 | productOtherId
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
 * reference Set    | productSomeId, productOtherId | productSomeId
 *
 */

public class ProductProjectionSearchModelIntegrationTest extends IntegrationTest {
    protected static Product product1;
    protected static Product product2;
    protected static Product productSomeId;
    protected static Product productOtherId;
    protected static ProductType productType;
    protected static final String PRODUCT_TYPE_NAME = "ProductSearchTypeIT";
    protected static final String SKU1 = PRODUCT_TYPE_NAME + "-sku1";
    protected static final String SKU2 = PRODUCT_TYPE_NAME + "-sku2";
    protected static final String SKU_SOME_ID = PRODUCT_TYPE_NAME + "-sku-some-id";

    protected static final String SKU_OTHER_ID = PRODUCT_TYPE_NAME + "-sku-other-id";
    protected static final String ATTR_NAME_BOOLEAN = ("Boolean" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_TEXT = ("Text" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_LOC_TEXT = ("LocText" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_ENUM = ("Enum" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_LOC_ENUM = ("LocEnum" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_NUMBER = ("Number" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_MONEY = ("Money" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_DATE = ("Date" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_TIME = ("Time" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_DATE_TIME = ("DateTime" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_REF = ("Ref" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_BOOLEAN_SET = ("BooleanSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_TEXT_SET = ("TextSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_LOC_TEXT_SET = ("LocTextSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_ENUM_SET = ("EnumSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_LOC_ENUM_SET = ("LocEnumSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_NUMBER_SET = ("NumberSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_MONEY_SET = ("MoneySet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_DATE_SET = ("DateSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_TIME_SET = ("TimeSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_DATE_TIME_SET = ("DateTimeSet" + PRODUCT_TYPE_NAME);
    protected static final String ATTR_NAME_REF_SET = ("RefSet" + PRODUCT_TYPE_NAME);

    protected static final String BOOL_TRUE = "true";
    protected static final String BOOL_FALSE = "false";
    protected static final String TEXT_FOO = "foo";
    protected static final String TEXT_BAR = "bar";
    protected static final LocalizedString LOC_TEXT_FOO = ofEnglishLocale("localized foo");
    protected static final LocalizedString LOC_TEXT_BAR = ofEnglishLocale("localized bar");
    protected static final EnumValue ENUM_ONE = EnumValue.of("one-key", "one");
    protected static final EnumValue ENUM_TWO = EnumValue.of("two-key", "two");
    protected static final EnumValue ENUM_THREE = EnumValue.of("three-key", "three");
    protected static final LocalizedEnumValue LOC_ENUM_ONE = LocalizedEnumValue.of("one-key", LocalizedString.of(GERMAN, "eins", FRENCH, "un"));
    protected static final LocalizedEnumValue LOC_ENUM_TWO = LocalizedEnumValue.of("two-key", LocalizedString.of(GERMAN, "zwei", FRENCH, "deux"));
    protected static final LocalizedEnumValue LOC_ENUM_THREE = LocalizedEnumValue.of("three-key", LocalizedString.of(GERMAN, "drei", FRENCH, "trois"));
    protected static final BigDecimal NUMBER_5 = valueOf(5D);
    protected static final BigDecimal NUMBER_10 = valueOf(10D);
    protected static final MonetaryAmount MONEY_500_EUR = MoneyImpl.of(valueOf(500), "EUR");
    protected static final MonetaryAmount MONEY_1000_USD = MoneyImpl.of(valueOf(1000), "USD");
    protected static final LocalDate DATE_2001 = LocalDate.parse("2001-09-11");
    protected static final LocalDate DATE_2002 = LocalDate.parse("2002-10-12");
    protected static final LocalTime TIME_22H = LocalTime.parse("22:05:09.203");
    protected static final LocalTime TIME_23H = LocalTime.parse("23:06:10.204");
    protected static final ZonedDateTime DATE_TIME_2001_22H = ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00");
    protected static final ZonedDateTime DATE_TIME_2002_23H = ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00");

    @Rule
    public RetryIntegrationTest retry = new RetryIntegrationTest(10, 10000, LoggerFactory.getLogger(this.getClass()));

    @BeforeClass
    public static void setupProducts() {
        productType = execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createProductType());

        final Query<Product> query = ProductQuery.of()
                .withPredicates(m -> m.masterData().staged().masterVariant().sku().isIn(asList(SKU1, SKU2, SKU_SOME_ID, SKU_OTHER_ID)));
        final List<Product> products = execute(query).getResults();

        final Function<String, Optional<Product>> findBySku =
                sku -> products.stream().filter(p -> sku.equals(p.getMasterData().getStaged().getMasterVariant().getSku())).findFirst();

        productSomeId = findBySku.apply(SKU_SOME_ID).orElseGet(() -> createTestProduct("Some Id", ProductVariantDraftBuilder.of().sku(SKU_SOME_ID).build()));
        productOtherId = findBySku.apply(SKU_OTHER_ID).orElseGet(() -> createTestProduct("Other Id", ProductVariantDraftBuilder.of().sku(SKU_OTHER_ID).build()));
        product1 = findBySku.apply(SKU1).orElseGet(() -> createProduct1());
        product2 = findBySku.apply(SKU2).orElseGet(() -> createProduct2());
    }

    protected static Long toCents(final MonetaryAmount money) {
        return money.getNumber().numberValueExact(BigDecimal.class).movePointRight(2).longValue();
    }

    protected static PagedSearchResult<ProductProjection> executeSearch(final ProductProjectionSearch search) {
        final List<String> ids = asList(product1.getId(), product2.getId());
        return execute(search.plusQueryFilters(filter -> filter.id().byAny(ids)));
    }

    private static Product createProduct1() {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(
                        AttributeAccess.ofBoolean().ofName(ATTR_NAME_BOOLEAN).draftOf(true),
                        AttributeAccess.ofText().ofName(ATTR_NAME_TEXT).draftOf(TEXT_FOO),
                        AttributeAccess.ofLocalizedString().ofName(ATTR_NAME_LOC_TEXT).draftOf(LOC_TEXT_FOO),
                        AttributeAccess.ofEnumValue().ofName(ATTR_NAME_ENUM).draftOf(ENUM_TWO),
                        AttributeAccess.ofLocalizedEnumValue().ofName(ATTR_NAME_LOC_ENUM).draftOf(LOC_ENUM_TWO),
                        AttributeAccess.ofDouble().ofName(ATTR_NAME_NUMBER).draftOf(NUMBER_5.doubleValue()),
                        AttributeAccess.ofMoney().ofName(ATTR_NAME_MONEY).draftOf(MONEY_500_EUR),
                        AttributeAccess.ofDate().ofName(ATTR_NAME_DATE).draftOf(DATE_2001),
                        AttributeAccess.ofTime().ofName(ATTR_NAME_TIME).draftOf(TIME_22H),
                        AttributeAccess.ofDateTime().ofName(ATTR_NAME_DATE_TIME).draftOf(DATE_TIME_2001_22H),
                        AttributeAccess.ofProductReference().ofName(ATTR_NAME_REF).draftOf(productSomeId.toReference()),
                        AttributeAccess.ofBooleanSet().ofName(ATTR_NAME_BOOLEAN_SET).draftOf(asSet(true, false)),
                        AttributeAccess.ofStringSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet(TEXT_FOO, TEXT_BAR)),
                        AttributeAccess.ofLocalizedStringSet().ofName(ATTR_NAME_LOC_TEXT_SET).draftOf(asSet(LOC_TEXT_FOO, LOC_TEXT_BAR)),
                        AttributeAccess.ofEnumValueSet().ofName(ATTR_NAME_ENUM_SET).draftOf(asSet(ENUM_TWO, ENUM_THREE)),
                        AttributeAccess.ofLocalizedEnumValueSet().ofName(ATTR_NAME_LOC_ENUM_SET).draftOf(asSet(LOC_ENUM_TWO, LOC_ENUM_THREE)),
                        AttributeAccess.ofDoubleSet().ofName(ATTR_NAME_NUMBER_SET).draftOf(asSet(NUMBER_5.doubleValue(), NUMBER_10.doubleValue())),
                        AttributeAccess.ofMoneySet().ofName(ATTR_NAME_MONEY_SET).draftOf(asSet(MONEY_500_EUR, MONEY_1000_USD)),
                        AttributeAccess.ofDateSet().ofName(ATTR_NAME_DATE_SET).draftOf(asSet(DATE_2001, DATE_2002)),
                        AttributeAccess.ofTimeSet().ofName(ATTR_NAME_TIME_SET).draftOf(asSet(TIME_22H, TIME_23H)),
                        AttributeAccess.ofDateTimeSet().ofName(ATTR_NAME_DATE_TIME_SET).draftOf(asSet(DATE_TIME_2001_22H, DATE_TIME_2002_23H)),
                        AttributeAccess.ofProductReferenceSet().ofName(ATTR_NAME_REF_SET).draftOf(asSet(productSomeId.toReference(), productOtherId.toReference())))
                .price(PriceDraft.of(new BigDecimal("23.45"), EUR))
                .sku(SKU1)
                .build();
        return createTestProduct("Product one", masterVariant);
    }

    private static Product createProduct2() {
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
                        AttributeAccess.ofProductReference().ofName(ATTR_NAME_REF).draftOf(productOtherId.toReference()),
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
                        AttributeAccess.ofProductReferenceSet().ofName(ATTR_NAME_REF_SET).draftOf(asSet(productSomeId.toReference())))
                .price(PriceDraft.of(new BigDecimal("46.80"), USD))
                .sku(SKU2)
                .build();
        return createTestProduct("Product two", masterVariant);
    }

    private static ProductType createProductType() {
        final EnumAttributeType enumType = EnumAttributeType.of(ENUM_ONE, ENUM_TWO, ENUM_THREE);
        final LocalizedEnumAttributeType enumLocType = LocalizedEnumAttributeType.of(LOC_ENUM_ONE, LOC_ENUM_TWO, LOC_ENUM_THREE);
        final AttributeDefinition booleanAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_BOOLEAN, ofEnglishLocale(ATTR_NAME_BOOLEAN), BooleanAttributeType.of()).build();
        final AttributeDefinition textAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT, ofEnglishLocale(ATTR_NAME_TEXT), StringAttributeType.of()).build();
        final AttributeDefinition locTextAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT, ofEnglishLocale(ATTR_NAME_LOC_TEXT), LocalizedStringAttributeType.of()).build();
        final AttributeDefinition enumAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_ENUM, ofEnglishLocale(ATTR_NAME_ENUM), enumType).build();
        final AttributeDefinition locEnumAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_ENUM, ofEnglishLocale(ATTR_NAME_LOC_ENUM), enumLocType).build();
        final AttributeDefinition numberAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_NUMBER, ofEnglishLocale(ATTR_NAME_NUMBER), NumberAttributeType.of()).build();
        final AttributeDefinition moneyAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_MONEY, ofEnglishLocale(ATTR_NAME_MONEY), MoneyAttributeType.of()).build();
        final AttributeDefinition dateAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE, ofEnglishLocale(ATTR_NAME_DATE), DateAttributeType.of()).build();
        final AttributeDefinition timeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TIME, ofEnglishLocale(ATTR_NAME_TIME), TimeAttributeType.of()).build();
        final AttributeDefinition dateTimeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_TIME, ofEnglishLocale(ATTR_NAME_DATE_TIME), DateTimeAttributeType.of()).build();
        final AttributeDefinition refAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_REF, ofEnglishLocale(ATTR_NAME_REF), ReferenceAttributeType.of(Product.referenceTypeId())).build();
        final AttributeDefinition booleanSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_BOOLEAN_SET, ofEnglishLocale(ATTR_NAME_BOOLEAN_SET), SetAttributeType.of(BooleanAttributeType.of())).build();
        final AttributeDefinition textSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT_SET, ofEnglishLocale(ATTR_NAME_TEXT_SET), SetAttributeType.of(StringAttributeType.of())).build();
        final AttributeDefinition locTextSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT_SET, ofEnglishLocale(ATTR_NAME_LOC_TEXT_SET), SetAttributeType.of(LocalizedStringAttributeType.of())).build();
        final AttributeDefinition enumSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_ENUM_SET, ofEnglishLocale(ATTR_NAME_ENUM_SET), SetAttributeType.of(enumType)).build();
        final AttributeDefinition locEnumSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_ENUM_SET, ofEnglishLocale(ATTR_NAME_LOC_ENUM_SET), SetAttributeType.of(enumLocType)).build();
        final AttributeDefinition numberSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_NUMBER_SET, ofEnglishLocale(ATTR_NAME_NUMBER_SET), SetAttributeType.of(NumberAttributeType.of())).build();
        final AttributeDefinition moneySetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_MONEY_SET, ofEnglishLocale(ATTR_NAME_MONEY_SET), SetAttributeType.of(MoneyAttributeType.of())).build();
        final AttributeDefinition dateSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_SET, ofEnglishLocale(ATTR_NAME_DATE_SET), SetAttributeType.of(DateAttributeType.of())).build();
        final AttributeDefinition timeSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TIME_SET, ofEnglishLocale(ATTR_NAME_TIME_SET), SetAttributeType.of(TimeAttributeType.of())).build();
        final AttributeDefinition dateTimeSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_TIME_SET, ofEnglishLocale(ATTR_NAME_DATE_TIME_SET), SetAttributeType.of(DateTimeAttributeType.of())).build();
        final AttributeDefinition refSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_REF_SET, ofEnglishLocale(ATTR_NAME_REF_SET), SetAttributeType.of(ReferenceAttributeType.of(Product.referenceTypeId()))).build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), PRODUCT_TYPE_NAME, "", asList(booleanAttrDef, textAttrDef,
                locTextAttrDef, enumAttrDef, locEnumAttrDef, numberAttrDef, moneyAttrDef, dateAttrDef, timeAttrDef, dateTimeAttrDef,
                refAttrDef, booleanSetAttrDef, textSetAttrDef, locTextSetAttrDef, enumSetAttrDef, locEnumSetAttrDef, numberSetAttrDef,
                moneySetAttrDef, dateSetAttrDef, timeSetAttrDef, dateTimeSetAttrDef, refSetAttrDef));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        return execute(productTypeCreateCommand);
    }

    private static Product createTestProduct(final String name, final ProductVariantDraft masterVariant) {
        final LocalizedString locName = ofEnglishLocale(name);
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, locName, locName.slugifiedUnique(), masterVariant).build();
        return execute(ProductCreateCommand.of(productDraft));
    }
}
