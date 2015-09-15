package io.sphere.sdk.products;

import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
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

import javax.money.CurrencyUnit;
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
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFilterTypesIntegrationTest extends IntegrationTest {
    public static final String TRUE_TERMSTATS_KEY = "true";
    public static final String FALSE_TERMSTATS_KEY = "false";
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

    public static final String TEXT_FOO = "foo";
    public static final String TEXT_BAR = "bar";
    public static final LocalizedString LOC_TEXT_FOO = ofEnglishLocale("localized foo");
    public static final LocalizedString LOC_TEXT_BAR = ofEnglishLocale("localized bar");
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
                .price(Price.of(new BigDecimal("23.45"), EUR))
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
                .price(Price.of(new BigDecimal("46.80"), USD))
                .sku(SKU2)
                .build();
        return createTestProduct("Product two", masterVariant);
    }

    @Test
    public void booleanAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN)
                        .filtered().by(true));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void booleanAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, Boolean> facetExpr = model().allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(TRUE_TERMSTATS_KEY, 1), TermStats.of(FALSE_TERMSTATS_KEY, 1)));
    }

    @Test
    public void textAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_TEXT)
                        .filtered().by(TEXT_FOO));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void textAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_TEXT)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(TEXT_FOO, 1), TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void locTextAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH)
                        .filtered().by(LOC_TEXT_FOO.get(ENGLISH)));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locTextAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1), TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void enumKeyAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key()
                        .filtered().by(ENUM_TWO.getKey()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumKeyAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(ENUM_TWO.getKey(), 1), TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void enumLabelAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label()
                        .filtered().by("two"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumLabelAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(ENUM_TWO.getLabel(), 1), TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void locEnumKeyAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key()
                        .filtered().by(LOC_ENUM_TWO.getKey()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumKeyAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(LOC_ENUM_TWO.getKey(), 1), TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void locEnumLabelAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN)
                        .filtered().by(LOC_ENUM_TWO.getLabel().get(GERMAN)));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumLabelAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1), TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void numberAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                        .filtered().by(NUMBER_5));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                        .filtered().byLessThanOrEqualTo(NUMBER_5));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void numberAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, BigDecimal> termExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        assertThat(executeAndReturnTerms(termSearch, termExpr)).containsOnlyElementsOf(asList(TermStats.of("5.0", 1), TermStats.of("10.0", 1)));

        final RangeFacetExpression<ProductProjection, BigDecimal> rangeExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                .faceted().byGreaterThanOrEqualTo(valueOf(0));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("0.0", null, 2L, "5.0", "10.0", "15.0", 7.50D)));
    }

    @Test
    public void moneyAmountAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount()
                        .filtered().by(toCents(MONEY_500_EUR)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount()
                        .filtered().byLessThanOrEqualTo(toCents(MONEY_500_EUR)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void moneyAmountAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, Long> termExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount()
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        assertThat(executeAndReturnTerms(termSearch, termExpr)).containsOnlyElementsOf(asList(TermStats.of("50000", 1), TermStats.of("100000", 1)));

        final RangeFacetExpression<ProductProjection, Long> rangeExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount()
                .faceted().byGreaterThanOrEqualTo(0L);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("0.0", null, 2L, "50000.0", "100000.0", "150000.0", 75000D)));
    }

    @Test
    public void moneyCurrencyAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency()
                        .filtered().by(MONEY_500_EUR.getCurrency()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void moneyCurrencyAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, CurrencyUnit> facetExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("EUR", 1), TermStats.of("USD", 1)));
    }

    @Test
    public void dateAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE)
                        .filtered().by(DATE_2001));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE)
                        .filtered().byLessThanOrEqualTo(DATE_2001));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void dateAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, LocalDate> termExpr = model().allVariants().attribute().ofDate(ATTR_NAME_DATE)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats> terms = executeAndReturnTerms(termSearch, termExpr);
        assertThat(terms).containsOnlyElementsOf(asList(TermStats.of("2001-09-11", 1), TermStats.of("2002-10-12", 1)));

        final RangeFacetExpression<ProductProjection, LocalDate> rangeExpr = model().allVariants().attribute().ofDate(ATTR_NAME_DATE)
                .faceted().byGreaterThanOrEqualTo(DATE_2001);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("1.0001664E12", null, 2L, "1.0001664E12", "1.0343808E12", "2.0345472E12", 1.0172736E12D)));
    }

    @Test
    public void timeAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME)
                        .filtered().by(TIME_22H));

        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME)
                        .filtered().byLessThanOrEqualTo(TIME_22H));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void timeAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, LocalTime> termExpr = model().allVariants().attribute().ofTime(ATTR_NAME_TIME)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats> terms = executeAndReturnTerms(termSearch, termExpr);
        assertThat(terms).containsOnlyElementsOf(asList(TermStats.of("22:05:09.203", 1), TermStats.of("23:06:10.204", 1)));

        final RangeFacetExpression<ProductProjection, LocalTime> rangeExpr = model().allVariants().attribute().ofTime(ATTR_NAME_TIME)
                .faceted().byGreaterThanOrEqualTo(TIME_22H);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("7.9509203E7", null, 2L, "7.9509203E7", "8.3170204E7", "1.62679407E8", 8.13397035E7D)));
    }

    @Test
    public void dateTimeAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                        .filtered().by(DATE_TIME_2001_22H));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                        .filtered().byLessThanOrEqualTo(DATE_TIME_2001_22H));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void datetimeAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, ZonedDateTime> termExpr = model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats> terms = executeAndReturnTerms(termSearch, termExpr);
        assertThat(terms).containsOnlyElementsOf(asList(TermStats.of("2002-10-12T23:06:10.204+0000", 1), TermStats.of("2001-09-11T22:05:09.203+0000", 1)));

        final RangeFacetExpression<ProductProjection, ZonedDateTime> rangeExpr = model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                .faceted().byGreaterThanOrEqualTo(DATE_TIME_2001_22H);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("1.000245909203E12", null, 2L, "1.000245909203E12", "1.034463970204E12", "2.034709879407E12", 1.0173549397035E12D)));
    }

    @Test
    public void referenceAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofReference(ATTR_NAME_REF).id()
                        .filtered().by(productSomeId.getId()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void referenceAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofReference(ATTR_NAME_REF).id()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(productSomeId.getId(), 1), TermStats.of(productOtherId.getId(), 1)));
    }

    @Test
    public void booleanSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN_SET)
                        .filtered().by(false));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void booleanSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_BOOLEAN_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(TRUE_TERMSTATS_KEY, 2), TermStats.of(FALSE_TERMSTATS_KEY, 1)));
    }

    @Test
    public void textSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_TEXT_SET)
                        .filtered().by(TEXT_BAR));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void textSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_TEXT_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(TEXT_FOO, 2), TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void locTextSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH)
                        .filtered().by(LOC_TEXT_BAR.get(ENGLISH)));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locTextSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2), TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void enumKeySetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key()
                        .filtered().by(ENUM_THREE.getKey()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumKeySetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(ENUM_TWO.getKey(), 2), TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void enumLabelSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label()
                        .filtered().by(ENUM_THREE.getLabel()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumLabelSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(ENUM_TWO.getLabel(), 2), TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void locEnumKeySetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key()
                        .filtered().by(LOC_ENUM_THREE.getKey()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumKeySetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(LOC_ENUM_TWO.getKey(), 2), TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void locEnumLabelSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN)
                        .filtered().by(LOC_ENUM_THREE.getLabel().get(GERMAN)));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumLabelSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2), TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void numberSetAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET)
                        .filtered().by(NUMBER_10));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET)
                        .filtered().byGreaterThanOrEqualTo(NUMBER_10));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void numberSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, BigDecimal> termExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        assertThat(executeAndReturnTerms(termSearch, termExpr)).containsOnlyElementsOf(asList(TermStats.of("5.0", 2), TermStats.of("10.0", 1)));

        final RangeFacetExpression<ProductProjection, BigDecimal> rangeExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET)
                .faceted().byGreaterThanOrEqualTo(valueOf(0));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("0.0", null, 2L, "5.0", "5.0", "10.0", 5.0D)));
    }

    @Test
    public void moneyAmountSetAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount()
                        .filtered().by(toCents(MONEY_1000_USD)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount()
                        .filtered().byGreaterThanOrEqualTo(toCents(MONEY_1000_USD)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void moneyAmountSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, Long> termExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount()
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        assertThat(executeAndReturnTerms(termSearch, termExpr)).containsOnlyElementsOf(asList(TermStats.of("50000", 2), TermStats.of("100000", 1)));

        final RangeFacetExpression<ProductProjection, Long> rangeExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount()
                .faceted().byGreaterThanOrEqualTo(0L);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("0.0", null, 2L, "50000.0", "50000.0", "100000.0", 50000D)));
    }

    @Test
    public void moneyCurrencySetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency()
                        .filtered().by(MONEY_1000_USD.getCurrency()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void moneyCurrencySetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, CurrencyUnit> facetExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("EUR", 2), TermStats.of("USD", 1)));
    }

    @Test
    public void dateSetAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE_SET)
                        .filtered().by(DATE_2002));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE_SET)
                        .filtered().byGreaterThanOrEqualTo(DATE_2002));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void dateSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, LocalDate> termExpr = model().allVariants().attribute().ofDate(ATTR_NAME_DATE_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats> terms = executeAndReturnTerms(termSearch, termExpr);
        assertThat(terms).containsOnlyElementsOf(asList(TermStats.of("2001-09-11", 2), TermStats.of("2002-10-12", 1)));

        final RangeFacetExpression<ProductProjection, LocalDate> rangeExpr = model().allVariants().attribute().ofDate(ATTR_NAME_DATE_SET)
                .faceted().byGreaterThanOrEqualTo(DATE_2001);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("1.0001664E12", null, 2L, "1.0001664E12", "1.0001664E12", "2.0003328E12", 1.0001664E12D)));
    }

    @Test
    public void timeSetAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME_SET)
                        .filtered().by(TIME_23H));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME_SET)
                        .filtered().byGreaterThanOrEqualTo(TIME_23H));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void timeSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, LocalTime> termExpr = model().allVariants().attribute().ofTime(ATTR_NAME_TIME_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats> terms = executeAndReturnTerms(termSearch, termExpr);
        assertThat(terms).containsOnlyElementsOf(asList(TermStats.of("22:05:09.203", 2), TermStats.of("23:06:10.204", 1)));

        final RangeFacetExpression<ProductProjection, LocalTime> rangeExpr = model().allVariants().attribute().ofTime(ATTR_NAME_TIME_SET)
                .faceted().byGreaterThanOrEqualTo(TIME_22H);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("7.9509203E7", null, 2L, "7.9509203E7", "7.9509203E7", "1.59018406E8", 7.9509203E7D)));
    }

    @Test
    public void dateTimeSetAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET)
                        .filtered().by(DATE_TIME_2002_23H));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET)
                        .filtered().byGreaterThanOrEqualTo(DATE_TIME_2002_23H));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Test
    public void datetimeSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, ZonedDateTime> termExpr = model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats> terms = executeAndReturnTerms(termSearch, termExpr);
        assertThat(terms).containsOnlyElementsOf(asList(TermStats.of("2001-09-11T22:05:09.203+0000", 2), TermStats.of("2002-10-12T23:06:10.204+0000", 1)));

        final RangeFacetExpression<ProductProjection, ZonedDateTime> rangeExpr = model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET)
                .faceted().byGreaterThanOrEqualTo(DATE_TIME_2001_22H);
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of("1.000245909203E12", null, 2L, "1.000245909203E12", "1.000245909203E12", "2.000491818406E12", 1.000245909203E12D)));
    }

    @Test
    public void referenceSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofReference(ATTR_NAME_REF_SET).id()
                        .filtered().by(productOtherId.getId()));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void referenceSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofReference(ATTR_NAME_REF_SET).id()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(productSomeId.getId(), 2), TermStats.of(productOtherId.getId(), 1)));
    }

    private ProductProjectionSearchModel model() {
        return ProductProjectionSearchModel.of();
    }

    private Long toCents(final MonetaryAmount money) {
        return money.getNumber().numberValueExact(BigDecimal.class).movePointRight(2).longValue();
    }

    private static List<String> executeAndReturnIds(final ProductProjectionSearch search) {
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        return resultsToIds(result);
    }

    private static List<TermStats> executeAndReturnTerms(final ProductProjectionSearch search, TermFacetExpression<ProductProjection, ?> facetExpr) {
        final TermFacetResult termFacetResult = executeSearch(search).getTermFacetResult(facetExpr);
        return termFacetResult.getTerms();
    }

    private static List<RangeStats> executeAndReturnRange(final ProductProjectionSearch search, RangeFacetExpression<ProductProjection, ?> facetExpr) {
        return executeSearch(search).getRangeFacetResult(facetExpr).getRanges();
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
        final AttributeDefinition textAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT, ofEnglishLocale(ATTR_NAME_TEXT), StringType.of()).build();
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
        final AttributeDefinition textSetAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_TEXT_SET, ofEnglishLocale(ATTR_NAME_TEXT_SET), SetType.of(StringType.of())).build();
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

    private static Product createTestProduct(final String name, final ProductVariantDraft masterVariant) {
        final LocalizedString locName = ofEnglishLocale(name);
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, locName, locName.slugifiedUnique(), masterVariant).build();
        return execute(ProductCreateCommand.of(productDraft));
    }
}
