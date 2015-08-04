package io.sphere.sdk.products;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
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
import org.junit.Test;
import org.slf4j.LoggerFactory;

import javax.money.Monetary;
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
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFilterTypesIntegrationTest extends IntegrationTest {
    private static Product product1;
    private static Product product2;
    private static Product productSomeId;
    private static Product productOtherId;
    private static ProductType productType;

    private static final String PRODUCT_TYPE_NAME = "ProductSearchTypeIT";
    private static final String SKU1 = PRODUCT_TYPE_NAME + "-sku1";
    private static final String SKU2 = PRODUCT_TYPE_NAME + "-sku2";
    private static final String SKU_SOME_ID = PRODUCT_TYPE_NAME + "-sku-some-id";
    private static final String SKU_OTHER_ID = PRODUCT_TYPE_NAME + "-sku-other-id";
    public static final String ATTR_NAME_BOOLEAN = ("Boolean" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TEXT = ("Text" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_TEXT = ("LocText" + PRODUCT_TYPE_NAME);
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
    public static final String ATTR_NAME_LOC_TEXT_SET = ("LocTextSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_ENUM_SET = ("EnumSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_LOC_ENUM_SET = ("LocEnumSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_NUMBER_SET = ("NumberSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_MONEY_SET = ("MoneySet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_DATE_SET = ("DateSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_TIME_SET = ("TimeSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_DATE_TIME_SET = ("DateTimeSet" + PRODUCT_TYPE_NAME);
    public static final String ATTR_NAME_REF_SET = ("RefSet" + PRODUCT_TYPE_NAME);

    public static final EnumValue ENUM_ONE = EnumValue.of("one-key", "one");
    public static final EnumValue ENUM_TWO = EnumValue.of("two-key", "two");
    public static final EnumValue ENUM_THREE = EnumValue.of("three-key", "three");
    public static final LocalizedEnumValue LOC_ENUM_ONE = LocalizedEnumValue.of("one-key", LocalizedString.of(GERMAN, "eins", FRENCH, "un"));
    public static final LocalizedEnumValue LOC_ENUM_TWO = LocalizedEnumValue.of("two-key", LocalizedString.of(GERMAN, "zwei", FRENCH, "deux"));
    public static final LocalizedEnumValue LOC_ENUM_THREE = LocalizedEnumValue.of("three-key", LocalizedString.of(GERMAN, "drei", FRENCH, "trois"));

    @Rule
    public RetryIntegrationTest retry = new RetryIntegrationTest(10, 0, LoggerFactory.getLogger(this.getClass()));

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

    @Test
    public void filtersBooleanAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN)
                        .filtered().by(true));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersTextAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofText(ATTR_NAME_TEXT)
                        .filtered().by("foo"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersLocTextAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableText(ATTR_NAME_LOC_TEXT).locale(ENGLISH)
                        .filtered().by("localized foo"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersEnumKeyAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key()
                        .filtered().by("two-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersEnumLabelAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label()
                        .filtered().by("two"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersLocEnumKeyAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key()
                        .filtered().by("two-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersLocEnumLabelAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN)
                        .filtered().by("zwei"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersNumberAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                        .filtered().by(valueOf(5D)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                        .filtered().byLessThanOrEqualTo(valueOf(5D)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersMoneyAmountAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).amount()
                        .filtered().by(valueOf(500)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).amount()
                        .filtered().byLessThanOrEqualTo(valueOf(500)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersMoneyCurrencyAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency()
                        .filtered().by(Monetary.getCurrency("EUR")));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersDateAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE)
                        .filtered().by(LocalDate.parse("2001-09-11")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE)
                        .filtered().byLessThanOrEqualTo(LocalDate.parse("2001-09-11")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersTimeAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME)
                        .filtered().by(LocalTime.parse("22:05:09.203")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME)
                        .filtered().byLessThanOrEqualTo(LocalTime.parse("22:05:09.203")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersDateTimeAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                        .filtered().by(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                        .filtered().byLessThanOrEqualTo(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersReferenceAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofProductReference(ATTR_NAME_REF)
                        .filtered().by(productSomeId));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersBooleanSetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN_SET)
                        .filtered().by(false));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersTextSetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofText(ATTR_NAME_TEXT_SET)
                        .filtered().by("bar"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersLocTextSetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableText(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH)
                        .filtered().by("localized bar"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersEnumKeySetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key()
                        .filtered().by("three-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersEnumLabelSetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label()
                        .filtered().by("three"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersLocEnumKeySetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key()
                        .filtered().by("three-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersLocEnumLabelSetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN)
                        .filtered().by("drei"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersNumberSetAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET)
                        .filtered().by(valueOf(10D)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET)
                        .filtered().byGreaterThanOrEqualTo(valueOf(10D)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersMoneyAmountSetAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).amount()
                        .filtered().by(valueOf(1000)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).amount()
                        .filtered().byGreaterThanOrEqualTo(valueOf(1000)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersMoneyCurrencySetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency()
                        .filtered().by(Monetary.getCurrency("USD")));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void filtersDateSetAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE_SET)
                        .filtered().by(LocalDate.parse("2002-10-12")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE_SET)
                        .filtered().byGreaterThanOrEqualTo(LocalDate.parse("2002-10-12")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersTimeSetAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME_SET)
                        .filtered().by(LocalTime.parse("23:06:10.204")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME_SET)
                        .filtered().byGreaterThanOrEqualTo(LocalTime.parse("23:06:10.204")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersDateTimeSetAttributes() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET)
                        .filtered().by(ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET)
                        .filtered().byGreaterThanOrEqualTo(ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void filtersReferenceSetAttributes() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofProductReference(ATTR_NAME_REF_SET)
                        .filtered().by(productOtherId));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    private static List<String> executeAndReturnIds(final ProductProjectionSearch search) {
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        return resultsToIds(result);
    }

    private static List<String> resultsToIds(final PagedSearchResult<ProductProjection> result) {
        return toIds(result.getResults());
    }

    private static PagedSearchResult<ProductProjection> executeSearch(final ProductProjectionSearch search) {
        final FilterExpression<ProductProjection> onlyCreatedProducts = FilterExpression.of(
                String.format("id:\"%s\",\"%s\"", product1.getId(), product2.getId()));
        return execute(search.plusQueryFilters(onlyCreatedProducts));
    }

    private static ProductType createProductType() {
        final EnumType enumType = EnumType.of(ENUM_ONE, ENUM_TWO, ENUM_THREE);
        final LocalizedEnumType enumLocType = LocalizedEnumType.of(LOC_ENUM_ONE, LOC_ENUM_TWO, LOC_ENUM_THREE);
        final AttributeDefinition booleanAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_BOOLEAN, ofEnglishLocale(ATTR_NAME_BOOLEAN), BooleanType.of()).build();
        final AttributeDefinition textAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT, ofEnglishLocale(ATTR_NAME_TEXT), TextType.of()).build();
        final AttributeDefinition locTextAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT, ofEnglishLocale(ATTR_NAME_LOC_TEXT), LocalizedStringType.of()).build();
        final AttributeDefinition enumAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_ENUM, ofEnglishLocale(ATTR_NAME_ENUM), enumType).build();
        final AttributeDefinition locEnumAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_ENUM, ofEnglishLocale(ATTR_NAME_LOC_ENUM), enumLocType).build();
        final AttributeDefinition numberAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_NUMBER, ofEnglishLocale(ATTR_NAME_NUMBER), NumberType.of()).build();
        final AttributeDefinition moneyAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_MONEY, ofEnglishLocale(ATTR_NAME_MONEY), MoneyType.of()).build();
        final AttributeDefinition dateAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE, ofEnglishLocale(ATTR_NAME_DATE), DateType.of()).build();
        final AttributeDefinition timeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TIME, ofEnglishLocale(ATTR_NAME_TIME), TimeType.of()).build();
        final AttributeDefinition dateTimeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_TIME, ofEnglishLocale(ATTR_NAME_DATE_TIME), DateTimeType.of()).build();
        final AttributeDefinition refAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_REF, ofEnglishLocale(ATTR_NAME_REF), ReferenceType.of(Product.typeId())).build();
        final AttributeDefinition booleanSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_BOOLEAN_SET, ofEnglishLocale(ATTR_NAME_BOOLEAN_SET), SetType.of(BooleanType.of())).build();
        final AttributeDefinition textSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT_SET, ofEnglishLocale(ATTR_NAME_TEXT_SET), SetType.of(TextType.of())).build();
        final AttributeDefinition locTextSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_TEXT_SET, ofEnglishLocale(ATTR_NAME_LOC_TEXT_SET), SetType.of(LocalizedStringType.of())).build();
        final AttributeDefinition enumSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_ENUM_SET, ofEnglishLocale(ATTR_NAME_ENUM_SET), SetType.of(enumType)).build();
        final AttributeDefinition locEnumSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_LOC_ENUM_SET, ofEnglishLocale(ATTR_NAME_LOC_ENUM_SET), SetType.of(enumLocType)).build();
        final AttributeDefinition numberSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_NUMBER_SET, ofEnglishLocale(ATTR_NAME_NUMBER_SET), SetType.of(NumberType.of())).build();
        final AttributeDefinition moneySetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_MONEY_SET, ofEnglishLocale(ATTR_NAME_MONEY_SET), SetType.of(MoneyType.of())).build();
        final AttributeDefinition dateSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_SET, ofEnglishLocale(ATTR_NAME_DATE_SET), SetType.of(DateType.of())).build();
        final AttributeDefinition timeSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TIME_SET, ofEnglishLocale(ATTR_NAME_TIME_SET), SetType.of(TimeType.of())).build();
        final AttributeDefinition dateTimeSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_DATE_TIME_SET, ofEnglishLocale(ATTR_NAME_DATE_TIME_SET), SetType.of(DateTimeType.of())).build();
        final AttributeDefinition refSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_REF_SET, ofEnglishLocale(ATTR_NAME_REF_SET), SetType.of(ReferenceType.of(Product.typeId()))).build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "", asList(booleanAttrDef, textAttrDef,
                locTextAttrDef, enumAttrDef, locEnumAttrDef, numberAttrDef, moneyAttrDef, dateAttrDef, timeAttrDef, dateTimeAttrDef,
                refAttrDef, booleanSetAttrDef, textSetAttrDef, locTextSetAttrDef, enumSetAttrDef, locEnumSetAttrDef, numberSetAttrDef,
                moneySetAttrDef, dateSetAttrDef, timeSetAttrDef, dateTimeSetAttrDef, refSetAttrDef));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        return execute(productTypeCreateCommand);
    }

    private static Product createProduct1() {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(
                        AttributeAccess.ofBoolean().ofName(ATTR_NAME_BOOLEAN).draftOf(true),
                        AttributeAccess.ofText().ofName(ATTR_NAME_TEXT).draftOf("foo"),
                        AttributeAccess.ofLocalizedString().ofName(ATTR_NAME_LOC_TEXT).draftOf(ofEnglishLocale("localized foo")),
                        AttributeAccess.ofEnumValue().ofName(ATTR_NAME_ENUM).draftOf(ENUM_TWO),
                        AttributeAccess.ofLocalizedEnumValue().ofName(ATTR_NAME_LOC_ENUM).draftOf(LOC_ENUM_TWO),
                        AttributeAccess.ofDouble().ofName(ATTR_NAME_NUMBER).draftOf(5D),
                        AttributeAccess.ofMoney().ofName(ATTR_NAME_MONEY).draftOf(MoneyImpl.of(valueOf(500), "EUR")),
                        AttributeAccess.ofDate().ofName(ATTR_NAME_DATE).draftOf(LocalDate.parse("2001-09-11")),
                        AttributeAccess.ofTime().ofName(ATTR_NAME_TIME).draftOf(LocalTime.parse("22:05:09.203")),
                        AttributeAccess.ofDateTime().ofName(ATTR_NAME_DATE_TIME).draftOf(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00")),
                        AttributeAccess.ofProductReference().ofName(ATTR_NAME_REF).draftOf(productSomeId.toReference()),
                        AttributeAccess.ofBooleanSet().ofName(ATTR_NAME_BOOLEAN_SET).draftOf(asSet(true, false)),
                        AttributeAccess.ofTextSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet("foo", "bar")),
                        AttributeAccess.ofLocalizedStringSet().ofName(ATTR_NAME_LOC_TEXT_SET).draftOf(asSet(ofEnglishLocale("localized foo"), ofEnglishLocale("localized bar"))),
                        AttributeAccess.ofEnumValueSet().ofName(ATTR_NAME_ENUM_SET).draftOf(asSet(ENUM_TWO, ENUM_THREE)),
                        AttributeAccess.ofLocalizedEnumValueSet().ofName(ATTR_NAME_LOC_ENUM_SET).draftOf(asSet(LOC_ENUM_TWO, LOC_ENUM_THREE)),
                        AttributeAccess.ofDoubleSet().ofName(ATTR_NAME_NUMBER_SET).draftOf(asSet(5D, 10D)),
                        AttributeAccess.ofMoneySet().ofName(ATTR_NAME_MONEY_SET).draftOf(asSet(MoneyImpl.of(valueOf(500), "EUR"), MoneyImpl.of(valueOf(1000), "USD"))),
                        AttributeAccess.ofDateSet().ofName(ATTR_NAME_DATE_SET).draftOf(asSet(LocalDate.parse("2001-09-11"), LocalDate.parse("2002-10-12"))),
                        AttributeAccess.ofTimeSet().ofName(ATTR_NAME_TIME_SET).draftOf(asSet(LocalTime.parse("22:05:09.203"), LocalTime.parse("23:06:10.204"))),
                        AttributeAccess.ofDateTimeSet().ofName(ATTR_NAME_DATE_TIME_SET).draftOf(asSet(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00"), ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00"))),
                        AttributeAccess.ofProductReferenceSet().ofName(ATTR_NAME_REF_SET).draftOf(asSet(productSomeId.toReference(), productOtherId.toReference())))
                .price(Price.of(new BigDecimal("23.45"), EUR))
                .sku(SKU1)
                .build();
        return createTestProduct("Product one", masterVariant);
    }

    private static Product createProduct2() {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(
                        AttributeAccess.ofBoolean().ofName(ATTR_NAME_BOOLEAN).draftOf(false),
                        AttributeAccess.ofText().ofName(ATTR_NAME_TEXT).draftOf("bar"),
                        AttributeAccess.ofLocalizedString().ofName(ATTR_NAME_LOC_TEXT).draftOf(ofEnglishLocale("localized bar")),
                        AttributeAccess.ofEnumValue().ofName(ATTR_NAME_ENUM).draftOf(ENUM_THREE),
                        AttributeAccess.ofLocalizedEnumValue().ofName(ATTR_NAME_LOC_ENUM).draftOf(LOC_ENUM_THREE),
                        AttributeAccess.ofDouble().ofName(ATTR_NAME_NUMBER).draftOf(10D),
                        AttributeAccess.ofMoney().ofName(ATTR_NAME_MONEY).draftOf(MoneyImpl.of(valueOf(1000), "USD")),
                        AttributeAccess.ofDate().ofName(ATTR_NAME_DATE).draftOf(LocalDate.parse("2002-10-12")),
                        AttributeAccess.ofTime().ofName(ATTR_NAME_TIME).draftOf(LocalTime.parse("23:06:10.204")),
                        AttributeAccess.ofDateTime().ofName(ATTR_NAME_DATE_TIME).draftOf(ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00")),
                        AttributeAccess.ofProductReference().ofName(ATTR_NAME_REF).draftOf(productOtherId.toReference()),
                        AttributeAccess.ofBooleanSet().ofName(ATTR_NAME_BOOLEAN_SET).draftOf(asSet(true)),
                        AttributeAccess.ofTextSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet("foo")),
                        AttributeAccess.ofLocalizedStringSet().ofName(ATTR_NAME_LOC_TEXT_SET).draftOf(asSet(ofEnglishLocale("localized foo"))),
                        AttributeAccess.ofEnumValueSet().ofName(ATTR_NAME_ENUM_SET).draftOf(asSet(ENUM_TWO)),
                        AttributeAccess.ofLocalizedEnumValueSet().ofName(ATTR_NAME_LOC_ENUM_SET).draftOf(asSet(LOC_ENUM_TWO)),
                        AttributeAccess.ofDoubleSet().ofName(ATTR_NAME_NUMBER_SET).draftOf(asSet(5D)),
                        AttributeAccess.ofMoneySet().ofName(ATTR_NAME_MONEY_SET).draftOf(asSet(MoneyImpl.of(valueOf(500), "EUR"))),
                        AttributeAccess.ofDateSet().ofName(ATTR_NAME_DATE_SET).draftOf(asSet(LocalDate.parse("2001-09-11"))),
                        AttributeAccess.ofTimeSet().ofName(ATTR_NAME_TIME_SET).draftOf(asSet(LocalTime.parse("22:05:09.203"))),
                        AttributeAccess.ofDateTimeSet().ofName(ATTR_NAME_DATE_TIME_SET).draftOf(asSet(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00"))),
                        AttributeAccess.ofProductReferenceSet().ofName(ATTR_NAME_REF_SET).draftOf(asSet(productSomeId.toReference())))
                .price(Price.of(new BigDecimal("46.80"), USD))
                .sku(SKU2)
                .build();
        return createTestProduct("Product two", masterVariant);
    }

    private static Product createTestProduct(final String name, final ProductVariantDraft masterVariant) {
        final LocalizedString locName = ofEnglishLocale(name);
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, locName, locName.slugifiedUnique(), masterVariant).build();
        return execute(ProductCreateCommand.of(productDraft));
    }
}
