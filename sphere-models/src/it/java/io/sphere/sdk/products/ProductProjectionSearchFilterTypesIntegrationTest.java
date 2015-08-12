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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import javax.money.CurrencyUnit;
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
    public void booleanAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN)
                        .filtered().by(true));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void booleanAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, Boolean> facetExpr = model().allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        //assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(true, 1), TermStats.of(false, 1))); // still returning F, T. to be fixed by API
    }

    @Test
    public void textAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_TEXT)
                        .filtered().by("foo"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void textAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_TEXT)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("foo", 1), TermStats.of("bar", 1)));
    }

    @Test
    public void locTextAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH)
                        .filtered().by("localized foo"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locTextAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("localized foo", 1), TermStats.of("localized bar", 1)));
    }

    @Test
    public void enumKeyAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key()
                        .filtered().by("two-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumKeyAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("two-key", 1), TermStats.of("three-key", 1)));
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
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("two", 1), TermStats.of("three", 1)));
    }

    @Test
    public void locEnumKeyAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key()
                        .filtered().by("two-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumKeyAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("two-key", 1), TermStats.of("three-key", 1)));
    }

    @Test
    public void locEnumLabelAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN)
                        .filtered().by("zwei"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumLabelAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("zwei", 1), TermStats.of("drei", 1)));
    }

    @Test
    public void numberAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                        .filtered().by(valueOf(5D)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                        .filtered().byLessThanOrEqualTo(valueOf(5D)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void numberAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, BigDecimal> termExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        //assertThat(executeAndReturnTerms(termSearch, termExpr)).containsOnlyElementsOf(asList(TermStats.ofNumber(valueOf(5D), 1), TermStats.ofNumber(valueOf(10D), 1)));

        final RangeFacetExpression<ProductProjection, BigDecimal> rangeExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER)
                .faceted().byGreaterThanOrEqualTo(valueOf(0));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats<BigDecimal>> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        //assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of(valueOf(0D), null, 2L, valueOf(5D), valueOf(10D), valueOf(15D), 7.5D)));
    }

    @Test
    public void moneyAmountAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).amount()
                        .filtered().by(valueOf(500)));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).amount()
                        .filtered().byLessThanOrEqualTo(valueOf(500)));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void moneyAmountAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, BigDecimal> termExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).amount()
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        //assertThat(executeAndReturnTerms(termSearch, termExpr)).containsOnlyElementsOf(asList(TermStats.of(valueOf(50000), 1), TermStats.of(valueOf(100000), 1)));

        final RangeFacetExpression<ProductProjection, BigDecimal> rangeExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).amount()
                .faceted().byGreaterThanOrEqualTo(valueOf(0));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats<BigDecimal>> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        //assertThat(actual).containsOnlyElementsOf(asList(RangeStats.of(valueOf(0D), null, 2L, valueOf(50000D), valueOf(100000D), valueOf(150000D), 75000D))); // it is actually cent amount!
    }

    @Test
    public void moneyCurrencyAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency()
                        .filtered().by(Monetary.getCurrency("EUR")));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void moneyCurrencyAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, CurrencyUnit> facetExpr = model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        //assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(Monetary.getCurrency("EUR"), 1), TermStats.of(Monetary.getCurrency("USD"), 1)));
    }

    @Test
    public void dateAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE)
                        .filtered().by(LocalDate.parse("2001-09-11")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDate(ATTR_NAME_DATE)
                        .filtered().byLessThanOrEqualTo(LocalDate.parse("2001-09-11")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void dateAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, LocalDate> termExpr = model().allVariants().attribute().ofDate(ATTR_NAME_DATE)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats<LocalDate>> terms = executeAndReturnTerms(termSearch, termExpr);
        //assertThat(terms).containsOnlyElementsOf(asList(TermStats.of(LocalDate.parse("2001-09-11"), 1), TermStats.of(LocalDate.parse("2002-10-12"), 1)));

        final RangeFacetExpression<ProductProjection, LocalDate> rangeExpr = model().allVariants().attribute().ofDate(ATTR_NAME_DATE)
                .faceted().byGreaterThanOrEqualTo(LocalDate.parse("2001-09-11"));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats<LocalDate>> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        //assertThat(actual).containsOnlyElementsOf(asList());
    }

    @Test
    public void timeAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME)
                        .filtered().by(LocalTime.parse("22:05:09.203")));

        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofTime(ATTR_NAME_TIME)
                        .filtered().byLessThanOrEqualTo(LocalTime.parse("22:05:09.203")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void timeAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, LocalTime> termExpr = model().allVariants().attribute().ofTime(ATTR_NAME_TIME)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats<LocalTime>> terms = executeAndReturnTerms(termSearch, termExpr);
        //assertThat(terms).containsOnlyElementsOf(asList(TermStats.of(LocalTime.parse("22:05:09.203"), 1), TermStats.of(LocalTime.parse("23:06:10.204"), 1)));

        final RangeFacetExpression<ProductProjection, LocalTime> rangeExpr = model().allVariants().attribute().ofTime(ATTR_NAME_TIME)
                .faceted().byGreaterThanOrEqualTo(LocalTime.parse("22:05:09.203"));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats<LocalTime>> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        //assertThat(actual).containsOnlyElementsOf(asList());
    }

    @Test
    public void dateTimeAttributesFilters() throws Exception {
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                        .filtered().by(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00")));
        assertThat(executeAndReturnIds(termSearch)).containsOnly(product1.getId());

        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                        .filtered().byLessThanOrEqualTo(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00")));
        assertThat(executeAndReturnIds(rangeSearch)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void datetimeAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, ZonedDateTime> termExpr = model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                .faceted().byAllTerms();
        final ProductProjectionSearch termSearch = ProductProjectionSearch.ofStaged().withFacets(termExpr);
        final List<TermStats<ZonedDateTime>> terms = executeAndReturnTerms(termSearch, termExpr);
        //assertThat(terms).containsOnlyElementsOf(asList(TermStats.of(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00"), 1), TermStats.of(ZonedDateTime.parse("2002-10-12T23:06:10.204+00:00"), 1)));

        final RangeFacetExpression<ProductProjection, ZonedDateTime> rangeExpr = model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME)
                .faceted().byGreaterThanOrEqualTo(ZonedDateTime.parse("2001-09-11T22:05:09.203+00:00"));
        final ProductProjectionSearch rangeSearch = ProductProjectionSearch.ofStaged().withFacets(rangeExpr);
        final List<RangeStats<ZonedDateTime>> actual = executeAndReturnRange(rangeSearch, rangeExpr);
        //assertThat(actual).containsOnlyElementsOf(asList());
    }

    @Test
    public void referenceAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofProductReference(ATTR_NAME_REF)
                        .filtered().by(productSomeId));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void booleanSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN_SET)
                        .filtered().by(false));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Ignore
    @Test
    public void booleanSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_BOOLEAN_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        //assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of(true, 2), TermStats.of(false, 1)));
    }

    @Test
    public void textSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_TEXT_SET)
                        .filtered().by("bar"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void textSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_TEXT_SET)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("foo", 2), TermStats.of("bar", 1)));
    }

    @Test
    public void locTextSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH)
                        .filtered().by("localized bar"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locTextSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("localized foo", 2), TermStats.of("localized bar", 1)));
    }

    @Test
    public void enumKeySetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key()
                        .filtered().by("three-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumKeySetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("two-key", 2), TermStats.of("three-key", 1)));
    }

    @Test
    public void enumLabelSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label()
                        .filtered().by("three"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void enumLabelSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("two", 2), TermStats.of("three", 1)));
    }

    @Test
    public void locEnumKeySetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key()
                        .filtered().by("three-key"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumKeySetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key()
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("two-key", 2), TermStats.of("three-key", 1)));
    }

    @Test
    public void locEnumLabelSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN)
                        .filtered().by("drei"));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void locEnumLabelSetAttributesFacets() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN)
                .faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withFacets(facetExpr);
        assertThat(executeAndReturnTerms(search, facetExpr)).containsOnlyElementsOf(asList(TermStats.of("zwei", 2), TermStats.of("drei", 1)));
    }

    @Test
    public void numberSetAttributesFilters() throws Exception {
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
    public void moneyAmountSetAttributesFilters() throws Exception {
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
    public void moneyCurrencySetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency()
                        .filtered().by(Monetary.getCurrency("USD")));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    @Test
    public void dateSetAttributesFilters() throws Exception {
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
    public void timeSetAttributesFilters() throws Exception {
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
    public void dateTimeSetAttributesFilters() throws Exception {
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
    public void referenceSetAttributesFilters() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withQueryFilters(model -> model.allVariants().attribute().ofProductReference(ATTR_NAME_REF_SET)
                        .filtered().by(productOtherId));
        assertThat(executeAndReturnIds(search)).containsOnly(product1.getId());
    }

    private ProductProjectionSearchModel model() {
        return ProductProjectionSearchModel.of();
    }

    private static List<String> executeAndReturnIds(final ProductProjectionSearch search) {
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        return resultsToIds(result);
    }

    private static <V> List<TermStats<V>> executeAndReturnTerms(final ProductProjectionSearch search, TermFacetExpression<ProductProjection, V> facetExpr) {
        final TermFacetResult<V> termFacetResult = executeSearch(search).getTermFacetResult(facetExpr);
        return termFacetResult.getTerms();
    }

    private static <V extends Comparable<? super V>> List<RangeStats<V>> executeAndReturnRange(final ProductProjectionSearch search, RangeFacetExpression<ProductProjection, V> facetExpr) {
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
                        AttributeAccess.ofStringSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet("foo", "bar")),
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
                        AttributeAccess.ofStringSet().ofName(ATTR_NAME_TEXT_SET).draftOf(asSet("foo")),
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
