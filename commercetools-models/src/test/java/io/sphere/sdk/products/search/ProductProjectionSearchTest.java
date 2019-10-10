package io.sphere.sdk.products.search;

import io.sphere.sdk.http.StringHttpRequestBody;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Ignore;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.math.BigDecimal.valueOf;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductProjectionSearchTest {
    private static final ProductProjectionFilterSearchModel FILTER_MODEL = ProductProjectionSearchModel.of().filter();
    private static final ProductDataFacetSearchModel FACET_MODEL = ProductProjectionSearchModel.of().facet();
    private static final ProductDataSortSearchModel SORT_MODEL = ProductProjectionSearchModel.of().sort();
    private static final ProductAttributeFilterSearchModel FILTER_ATTR = ProductProjectionSearchModel.of().filter().allVariants().attribute();
    private static final ProductAttributeFacetSearchModel FACET_ATTR = ProductProjectionSearchModel.of().facet().allVariants().attribute();
    private static final ProductAttributeSortSearchModel SORT_ATTR = ProductProjectionSearchModel.of().sort().allVariants().attribute();
    public static final PriceSelectionDsl FULL_PRICE_SELECTION = PriceSelectionBuilder.of(EUR)
            .priceChannelId("channel-id")
            .priceCustomerGroupId("customer-group-id")
            .priceCountry(DE)
            .build();
    public static final ProductProjectionSearch SEARCH_WITH_FULL_PRICE_SELECTION = ProductProjectionSearch.ofStaged().withPriceSelection(FULL_PRICE_SELECTION);

    @Ignore
    @Test
    public void canAccessProductName() throws Exception {
        assertThat(FACET_MODEL.name().locale(ENGLISH).allTerms().expression()).isEqualTo("name.en");
        assertThat(FILTER_MODEL.name().locale(ENGLISH).is("foo")).extracting(expression()).containsExactly("name.en:\"foo\"");
        assertThat(SORT_MODEL.name().locale(ENGLISH).asc().expression()).isEqualTo("name.en asc");
    }

    @Ignore
    @Test
    public void canAccessCreatedAt() throws Exception {
        assertThat(FACET_MODEL.createdAt().allTerms().expression()).isEqualTo("createdAt");
        assertThat(FILTER_MODEL.createdAt().is(dateTime("2001-09-11T22:05:09.203+00:00"))).extracting(expression()).containsExactly("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(SORT_MODEL.createdAt().desc().expression()).isEqualTo("createdAt desc");
    }

    @Ignore
    @Test
    public void canAccessLastModifiedAt() throws Exception {
        assertThat(FACET_MODEL.lastModifiedAt().allTerms().expression()).isEqualTo("lastModifiedAt");
        assertThat(FILTER_MODEL.lastModifiedAt().is(dateTime("2001-09-11T22:05:09.203+00:00"))).extracting(expression()).containsExactly("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(SORT_MODEL.lastModifiedAt().asc().expression()).isEqualTo("lastModifiedAt asc");
    }

    @Ignore
    @Test
    public void canAccessCategories() throws Exception {
        assertThat(FACET_MODEL.categories().id().allTerms().expression()).isEqualTo("categories.id");
        assertThat(FILTER_MODEL.categories().id().is("some-id")).extracting(expression()).containsExactly("categories.id:\"some-id\"");
    }

    @Ignore
    @Test
    public void canAccessProductType() throws Exception {
        assertThat(FACET_MODEL.productType().id().allTerms().expression()).isEqualTo("productType.id");
        assertThat(FILTER_MODEL.productType().id().is("some-id")).extracting(expression()).containsExactly("productType.id:\"some-id\"");
    }

    @Ignore
    @Test
    public void canAccessPriceAmount() throws Exception {
        assertThat(FACET_MODEL.allVariants().price().centAmount().allTerms().expression()).isEqualTo("variants.price.centAmount");
        assertThat(FILTER_MODEL.allVariants().price().centAmount().is(1000L)).extracting(expression()).containsExactly("variants.price.centAmount:1000");
        assertThat(SORT_MODEL.allVariants().price().descWithMinValue().expression()).isEqualTo("price desc.min");
    }

    @Ignore
    @Test
    public void canAccessPriceCurrency() throws Exception {
        assertThat(FACET_MODEL.allVariants().price().currency().allTerms().expression()).isEqualTo("variants.price.currencyCode");
        assertThat(FILTER_MODEL.allVariants().price().currency().is(currency("EUR"))).extracting(expression()).containsExactly("variants.price.currencyCode:\"EUR\"");
    }

    @Ignore
    @Test
    public void canAccessScopedPriceDiscounted() throws Exception {
        assertThat(FACET_MODEL.allVariants().scopedPriceDiscounted().allTerms().expression()).isEqualTo("variants.scopedPriceDiscounted");
        assertThat(FILTER_MODEL.allVariants().scopedPriceDiscounted().is(true)).extracting(expression()).containsExactly("variants.scopedPriceDiscounted:true");
        assertThat(SORT_MODEL.allVariants().scopedPriceDiscounted().asc().expression()).isEqualTo("variants.scopedPriceDiscounted asc");
    }

    @Ignore
    @Test
    public void canAccessSku() throws Exception {
        final String sku = "sku-test";
        assertThat(FILTER_MODEL.allVariants().sku().is(sku)).extracting(expression()).containsExactly("variants.sku:\"" + sku +"\"");
        assertThat(SORT_MODEL.allVariants().sku().asc().expression()).isEqualTo("variants.sku asc");
    }

    @Ignore
    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final String attrName = "brand";
        assertThat(FACET_ATTR.ofString(attrName).allTerms().expression()).isEqualTo("variants.attributes.brand");
        assertThat(FILTER_ATTR.ofString(attrName).is("Apple")).extracting(expression()).containsExactly("variants.attributes.brand:\"Apple\"");
        assertThat(SORT_ATTR.ofString(attrName).ascWithMaxValue().expression()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Ignore
    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final String attrName = "material";
        assertThat(FACET_ATTR.ofLocalizedString(attrName).locale(ENGLISH).allTerms().expression()).isEqualTo("variants.attributes.material.en");
        assertThat(FILTER_ATTR.ofLocalizedString(attrName).locale(ENGLISH).is("steel")).extracting(expression()).containsExactly("variants.attributes.material.en:\"steel\"");
        assertThat(SORT_ATTR.ofLocalizedString(attrName).locale(ENGLISH).descWithMinValue().expression()).isEqualTo("variants.attributes.material.en desc.min");
    }

    @Ignore
    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final String attrName = "isHandmade";
        assertThat(FACET_ATTR.ofBoolean(attrName).allTerms().expression()).isEqualTo("variants.attributes.isHandmade");
        assertThat(FILTER_ATTR.ofBoolean(attrName).is(true)).extracting(expression()).containsExactly("variants.attributes.isHandmade:true");
        assertThat(SORT_ATTR.ofBoolean(attrName).ascWithMaxValue().expression()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Ignore
    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        final String attrName = "length";
        assertThat(FACET_ATTR.ofNumber(attrName).allTerms().expression()).isEqualTo("variants.attributes.length");
        assertThat(FILTER_ATTR.ofNumber(attrName).is(valueOf(4))).extracting(expression()).containsExactly("variants.attributes.length:4");
        assertThat(SORT_ATTR.ofNumber(attrName).descWithMinValue().expression()).isEqualTo("variants.attributes.length desc.min");
    }

    @Ignore
    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        final String attrName = "expirationDate";
        assertThat(FACET_ATTR.ofDate(attrName).allTerms().expression()).isEqualTo("variants.attributes.expirationDate");
        assertThat(FILTER_ATTR.ofDate(attrName).is(date("2001-09-11"))).extracting(expression()).containsExactly("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(SORT_ATTR.ofDateTime(attrName).ascWithMaxValue().expression()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Ignore
    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        final String attrName = "deliveryHours";
        assertThat(FACET_ATTR.ofTime(attrName).allTerms().expression()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(FILTER_ATTR.ofTime(attrName).is(time("22:05:09.203"))).extracting(expression()).containsExactly("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(SORT_ATTR.ofTime(attrName).descWithMinValue().expression()).isEqualTo("variants.attributes.deliveryHours desc.min");
    }

    @Ignore
    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        final String attrName = "createdDate";
        assertThat(FACET_ATTR.ofDateTime(attrName).allTerms().expression()).isEqualTo("variants.attributes.createdDate");
        assertThat(FILTER_ATTR.ofDateTime(attrName).is(dateTime("2001-09-11T22:05:09.203+00:00"))).extracting(expression()).containsExactly("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(SORT_ATTR.ofDateTime(attrName).ascWithMaxValue().expression()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Ignore
    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final String attrName = "originCountry";
        assertThat(FACET_ATTR.ofEnum(attrName).key().allTerms().expression()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(FILTER_ATTR.ofEnum(attrName).key().is("Italy")).extracting(expression()).containsExactly("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(SORT_ATTR.ofEnum(attrName).key().descWithMinValue().expression()).isEqualTo("variants.attributes.originCountry.key desc.min");
    }

    @Ignore
    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        final String attrName = "originCountry";
        assertThat(FACET_ATTR.ofEnum(attrName).label().allTerms().expression()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(FILTER_ATTR.ofEnum(attrName).label().is("Italy")).extracting(expression()).containsExactly("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(SORT_ATTR.ofEnum(attrName).label().ascWithMaxValue().expression()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Ignore
    @Test
    public void canAccessMoneyCentAmountCustomAttributes() throws Exception {
        final String attrName = "originalPrice";
        assertThat(FACET_ATTR.ofMoney(attrName).centAmount().allTerms().expression()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(FILTER_ATTR.ofMoney(attrName).centAmount().is(1000L)).extracting(expression()).containsExactly("variants.attributes.originalPrice.centAmount:1000");
        assertThat(SORT_ATTR.ofMoney(attrName).centAmount().ascWithMaxValue().expression()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Ignore
    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final String attrName = "originalPrice";
        assertThat(FACET_ATTR.ofMoney(attrName).currency().allTerms().expression()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(FILTER_ATTR.ofMoney(attrName).currency().is(currency("EUR"))).extracting(expression()).containsExactly("variants.attributes.originalPrice.currencyCode:\"EUR\"");
        assertThat(SORT_ATTR.ofMoney(attrName).currency().descWithMinValue().expression()).isEqualTo("variants.attributes.originalPrice.currencyCode desc.min");
    }

    @Ignore
    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        final String attrName = "recommendedProduct";
        assertThat(FACET_ATTR.ofReference(attrName).id().allTerms().expression()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(FILTER_ATTR.ofReference(attrName).id().is("some-id")).extracting(expression()).containsExactly("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Ignore
    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        final String attrName = "color";
        assertThat(FACET_ATTR.ofLocalizedEnum(attrName).key().allTerms().expression()).isEqualTo("variants.attributes.color.key");
        assertThat(FILTER_ATTR.ofLocalizedEnum(attrName).key().is("ROT")).extracting(expression()).containsExactly("variants.attributes.color.key:\"ROT\"");
        assertThat(SORT_ATTR.ofLocalizedEnum(attrName).key().descWithMinValue().expression()).isEqualTo("variants.attributes.color.key desc.min");
    }

    @Ignore
    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        final String attrName = "color";
        assertThat(FACET_ATTR.ofLocalizedEnum(attrName).label().locale(ENGLISH).allTerms().expression()).isEqualTo("variants.attributes.color.label.en");
        assertThat(FILTER_ATTR.ofLocalizedEnum(attrName).label().locale(ENGLISH).is("red")).extracting(expression()).containsExactly("variants.attributes.color.label.en:\"red\"");
        assertThat(SORT_ATTR.ofLocalizedEnum(attrName).label().locale(ENGLISH).ascWithMaxValue().expression()).isEqualTo("variants.attributes.color.label.en asc.max");
    }

    @Ignore
    @Test
    public void usesAlias() throws Exception {
        final RangeTermFacetSearchModel<ProductProjection, BigDecimal> path = FACET_ATTR.ofNumber("size");
        assertThat(path.withAlias("my-facet").allTerms().expression()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(path.withAlias("my-facet").onlyTerm(valueOf(38)).expression()).isEqualTo("variants.attributes.size:38 as my-facet");
        assertThat(path.withAlias("my-facet").onlyLessThan(valueOf(38)).expression()).isEqualTo("variants.attributes.size:range(* to 38) as my-facet");
    }

    @Ignore
    @Test
    public void unicode() throws Exception {
        final StringHttpRequestBody body = (StringHttpRequestBody) ProductProjectionSearch.ofStaged().withText(GERMAN, "öón").httpRequestIntent().getBody();
        final String path = body.getString();
        final String expected = "text.de=%C3%B6%C3%B3n&staged=true";
        assertThat(path).isEqualTo(expected);
    }

    @Ignore
    @Test
    public void priceSelectionParameterCanBeAdded() {
        assertThat(SEARCH_WITH_FULL_PRICE_SELECTION.httpRequestIntent().getBody().toString())
                .contains("priceCurrency=EUR&priceCountry=DE&priceCustomerGroup=customer-group-id&priceChannel=channel-id");
        assertThat(SEARCH_WITH_FULL_PRICE_SELECTION.getPriceSelection()).isEqualTo(FULL_PRICE_SELECTION);
    }

    @Ignore
    @Test
    public void priceSelectionCanBeRefined() {
        final PriceSelectionDsl priceSelectionWithoutCustomerGroup = FULL_PRICE_SELECTION.withPriceCustomerGroup(null);
        final ProductProjectionSearch priceSearchWithoutCustomer =
                SEARCH_WITH_FULL_PRICE_SELECTION.withPriceSelection(priceSelectionWithoutCustomerGroup);
        assertThat(priceSearchWithoutCustomer.httpRequestIntent().getBody().toString())
                .contains("priceCurrency=EUR&priceCountry=DE&priceChannel=channel-id")
                .doesNotContain("priceCustomerGroup").doesNotContain("customer-group-id");
        assertThat(priceSearchWithoutCustomer.getPriceSelection()).isEqualTo(priceSelectionWithoutCustomerGroup);
    }

    @Ignore
    @Test
    public void removePriceSelection() {
        final ProductProjectionSearch priceSelectionRemoved = SEARCH_WITH_FULL_PRICE_SELECTION.withPriceSelection(null);
        assertThat(priceSelectionRemoved.httpRequestIntent().getBody().toString()).doesNotContain("price");
        assertThat(priceSelectionRemoved.getPriceSelection()).isNull();
    }

    @Ignore
    @Test
    public void categorySubtree() {
        final List<String> expressionsWithoutSubtrees =
                ProductProjectionSearch.ofStaged().withQueryFilters(m -> m.categories().id().isIn(asList("id1", "id2"))).queryFilters().stream().map(e -> e.expression()).collect(Collectors.toList());

        assertThat(expressionsWithoutSubtrees.get(0)).isEqualTo("categories.id:\"id1\",\"id2\"");

        final List<FilterExpression<ProductProjection>> filterExpressions = ProductProjectionSearch.ofStaged().withQueryFilters(m -> m.categories().id().isInSubtreeOrInCategory(asList("id1", "id2"), asList("id3", "id4"))).queryFilters();
        final List<String> collect = filterExpressions.stream().map(e -> e.expression()).collect(Collectors.toList());

        final String expected = "categories.id:subtree(\"id1\"),subtree(\"id2\"),\"id3\",\"id4\"";

        assertThat(collect).hasSize(1);
        assertThat(collect.get(0)).isEqualTo(expected);
    }

    @Ignore
    @Test
    public void existFilter() {
        final List<FilterExpression<ProductProjection>> exists = FILTER_MODEL.categories().exists();
        assertThat(exists).extracting(expression()).containsExactly("categories:exists");
    }

    //    @Test
//    public void canCreateFacetedSearchExpressions() throws Exception {
//        final FacetAndFilterSearchExpression<ProductProjection> facetedSearch = attributeModel().ofDate("expirationDate").facetedAndFiltered().by("2001-09-11");
//        assertThat(facetedSearch.facetExpression().expression()).isEqualTo("variants.attributes.expirationDate");
//        assertThat(facetedSearch.filterExpressions()).extracting(expression()).containsExactly("variants.attributes.expirationDate:\"2001-09-11\"");
//    }

    private ZonedDateTime dateTime(final String dateTime) {
        return ZonedDateTime.parse(dateTime);
    }

    private LocalTime time(final String time) {
        return LocalTime.parse(time);
    }

    private LocalDate date(final String date) {
        return LocalDate.parse(date);
    }

    private CurrencyUnit currency(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }

    private Extractor<FilterExpression<ProductProjection>, String> expression() {
        return FilterExpression::expression;
    }
}
