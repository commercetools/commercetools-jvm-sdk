package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.*;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static java.math.BigDecimal.valueOf;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchTest {
    private static final ProductDataFilterSearchModel FILTER = ProductProjectionSearchModel.of().filter();
    private static final ProductDataFacetSearchModel FACET = ProductProjectionSearchModel.of().facet();
    private static final ProductDataSortSearchModel SORT = ProductProjectionSearchModel.of().sort();
    private static final ProductAttributeFilterSearchModel FILTER_ATTR = ProductProjectionSearchModel.of().filter().allVariants().attribute();
    private static final ProductAttributeFacetSearchModel FACET_ATTR = ProductProjectionSearchModel.of().facet().allVariants().attribute();
    private static final ProductAttributeSortSearchModel SORT_ATTR = ProductProjectionSearchModel.of().sort().allVariants().attribute();

    @Test
    public void canAccessProductName() throws Exception {
        assertThat(FACET.name().locale(ENGLISH).allTerms().expression()).isEqualTo("name.en");
        assertThat(FILTER.name().locale(ENGLISH).by("foo")).extracting(expression()).containsExactly("name.en:\"foo\"");
        assertThat(SORT.name().locale(ENGLISH).byAsc().expression()).isEqualTo("name.en asc");
    }

    @Test
    public void canAccessCreatedAt() throws Exception {
        assertThat(FACET.createdAt().allTerms().expression()).isEqualTo("createdAt");
        assertThat(FILTER.createdAt().by(dateTime("2001-09-11T22:05:09.203+00:00"))).extracting(expression()).containsExactly("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(SORT.createdAt().byDesc().expression()).isEqualTo("createdAt desc");
    }

    @Test
    public void canAccessLastModifiedAt() throws Exception {
        assertThat(FACET.lastModifiedAt().allTerms().expression()).isEqualTo("lastModifiedAt");
        assertThat(FILTER.lastModifiedAt().by(dateTime("2001-09-11T22:05:09.203+00:00"))).extracting(expression()).containsExactly("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(SORT.lastModifiedAt().byAsc().expression()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void canAccessCategories() throws Exception {
        assertThat(FACET.categories().id().allTerms().expression()).isEqualTo("categories.id");
        assertThat(FILTER.categories().id().by("some-id")).extracting(expression()).containsExactly("categories.id:\"some-id\"");
    }

    @Test
    public void canAccessProductType() throws Exception {
        assertThat(FACET.productType().id().allTerms().expression()).isEqualTo("productType.id");
        assertThat(FILTER.productType().id().by("some-id")).extracting(expression()).containsExactly("productType.id:\"some-id\"");
    }

    @Test
    public void canAccessPriceAmount() throws Exception {
        assertThat(FACET.allVariants().price().centAmount().allTerms().expression()).isEqualTo("variants.price.centAmount");
        assertThat(FILTER.allVariants().price().centAmount().by(1000L)).extracting(expression()).containsExactly("variants.price.centAmount:1000");
        assertThat(SORT.allVariants().price().byDescWithMin().expression()).isEqualTo("price desc.min");
    }

    @Test
    public void canAccessPriceCurrency() throws Exception {
        assertThat(FACET.allVariants().price().currency().allTerms().expression()).isEqualTo("variants.price.currencyCode");
        assertThat(FILTER.allVariants().price().currency().by(currency("EUR"))).extracting(expression()).containsExactly("variants.price.currencyCode:\"EUR\"");
    }

    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final String attrName = "brand";
        assertThat(FACET_ATTR.ofString(attrName).allTerms().expression()).isEqualTo("variants.attributes.brand");
        assertThat(FILTER_ATTR.ofString(attrName).by("Apple")).extracting(expression()).containsExactly("variants.attributes.brand:\"Apple\"");
        assertThat(SORT_ATTR.ofString(attrName).byAscWithMax().expression()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final String attrName = "material";
        assertThat(FACET_ATTR.ofLocalizedString(attrName).locale(ENGLISH).allTerms().expression()).isEqualTo("variants.attributes.material.en");
        assertThat(FILTER_ATTR.ofLocalizedString(attrName).locale(ENGLISH).by("steel")).extracting(expression()).containsExactly("variants.attributes.material.en:\"steel\"");
        assertThat(SORT_ATTR.ofLocalizedString(attrName).locale(ENGLISH).byDescWithMin().expression()).isEqualTo("variants.attributes.material.en desc.min");
    }

    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final String attrName = "isHandmade";
        assertThat(FACET_ATTR.ofBoolean(attrName).allTerms().expression()).isEqualTo("variants.attributes.isHandmade");
        assertThat(FILTER_ATTR.ofBoolean(attrName).by(true)).extracting(expression()).containsExactly("variants.attributes.isHandmade:true");
        assertThat(SORT_ATTR.ofBoolean(attrName).byAscWithMax().expression()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        final String attrName = "length";
        assertThat(FACET_ATTR.ofNumber(attrName).allTerms().expression()).isEqualTo("variants.attributes.length");
        assertThat(FILTER_ATTR.ofNumber(attrName).by(valueOf(4))).extracting(expression()).containsExactly("variants.attributes.length:4");
        assertThat(SORT_ATTR.ofNumber(attrName).byDescWithMin().expression()).isEqualTo("variants.attributes.length desc.min");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        final String attrName = "expirationDate";
        assertThat(FACET_ATTR.ofDate(attrName).allTerms().expression()).isEqualTo("variants.attributes.expirationDate");
        assertThat(FILTER_ATTR.ofDate(attrName).by(date("2001-09-11"))).extracting(expression()).containsExactly("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(SORT_ATTR.ofDateTime(attrName).byAscWithMax().expression()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        final String attrName = "deliveryHours";
        assertThat(FACET_ATTR.ofTime(attrName).allTerms().expression()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(FILTER_ATTR.ofTime(attrName).by(time("22:05:09.203"))).extracting(expression()).containsExactly("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(SORT_ATTR.ofTime(attrName).byDescWithMin().expression()).isEqualTo("variants.attributes.deliveryHours desc.min");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        final String attrName = "createdDate";
        assertThat(FACET_ATTR.ofDateTime(attrName).allTerms().expression()).isEqualTo("variants.attributes.createdDate");
        assertThat(FILTER_ATTR.ofDateTime(attrName).by(dateTime("2001-09-11T22:05:09.203+00:00"))).extracting(expression()).containsExactly("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(SORT_ATTR.ofDateTime(attrName).byAscWithMax().expression()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final String attrName = "originCountry";
        assertThat(FACET_ATTR.ofEnum(attrName).key().allTerms().expression()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(FILTER_ATTR.ofEnum(attrName).key().by("Italy")).extracting(expression()).containsExactly("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(SORT_ATTR.ofEnum(attrName).key().byDescWithMin().expression()).isEqualTo("variants.attributes.originCountry.key desc.min");
    }

    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        final String attrName = "originCountry";
        assertThat(FACET_ATTR.ofEnum(attrName).label().allTerms().expression()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(FILTER_ATTR.ofEnum(attrName).label().by("Italy")).extracting(expression()).containsExactly("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(SORT_ATTR.ofEnum(attrName).label().byAscWithMax().expression()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Test
    public void canAccessMoneyCentAmountCustomAttributes() throws Exception {
        final String attrName = "originalPrice";
        assertThat(FACET_ATTR.ofMoney(attrName).centAmount().allTerms().expression()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(FILTER_ATTR.ofMoney(attrName).centAmount().by(1000L)).extracting(expression()).containsExactly("variants.attributes.originalPrice.centAmount:1000");
        assertThat(SORT_ATTR.ofMoney(attrName).centAmount().byAscWithMax().expression()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final String attrName = "originalPrice";
        assertThat(FACET_ATTR.ofMoney(attrName).currency().allTerms().expression()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(FILTER_ATTR.ofMoney(attrName).currency().by(currency("EUR"))).extracting(expression()).containsExactly("variants.attributes.originalPrice.currencyCode:\"EUR\"");
        assertThat(SORT_ATTR.ofMoney(attrName).currency().byDescWithMin().expression()).isEqualTo("variants.attributes.originalPrice.currencyCode desc.min");
    }

    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        final String attrName = "recommendedProduct";
        assertThat(FACET_ATTR.ofReference(attrName).id().allTerms().expression()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(FILTER_ATTR.ofReference(attrName).id().by("some-id")).extracting(expression()).containsExactly("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        final String attrName = "color";
        assertThat(FACET_ATTR.ofLocalizedEnum(attrName).key().allTerms().expression()).isEqualTo("variants.attributes.color.key");
        assertThat(FILTER_ATTR.ofLocalizedEnum(attrName).key().by("ROT")).extracting(expression()).containsExactly("variants.attributes.color.key:\"ROT\"");
        assertThat(SORT_ATTR.ofLocalizedEnum(attrName).key().byDescWithMin().expression()).isEqualTo("variants.attributes.color.key desc.min");
    }

    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        final String attrName = "color";
        assertThat(FACET_ATTR.ofLocalizedEnum(attrName).label().locale(ENGLISH).allTerms().expression()).isEqualTo("variants.attributes.color.label.en");
        assertThat(FILTER_ATTR.ofLocalizedEnum(attrName).label().locale(ENGLISH).by("red")).extracting(expression()).containsExactly("variants.attributes.color.label.en:\"red\"");
        assertThat(SORT_ATTR.ofLocalizedEnum(attrName).label().locale(ENGLISH).byAscWithMax().expression()).isEqualTo("variants.attributes.color.label.en asc.max");
    }

    @Test
    public void usesAlias() throws Exception {
        final RangeTermFacetSearchModel<ProductProjection, BigDecimal> path = FACET_ATTR.ofNumber("size");
        assertThat(path.withAlias("my-facet").allTerms().expression()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(path.withAlias("my-facet").onlyTerm(valueOf(38)).expression()).isEqualTo("variants.attributes.size:38 as my-facet");
        assertThat(path.withAlias("my-facet").onlyLessThan(valueOf(38)).expression()).isEqualTo("variants.attributes.size:range(* to 38) as my-facet");
    }

    @Test
    public void unicode() throws Exception {
        final String path = ProductProjectionSearch.ofStaged().withText(GERMAN, "öón").httpRequestIntent().getPath();
        final String expected = "/product-projections/search?text.de=%C3%B6%C3%B3n&staged=true";
        assertThat(path).isEqualTo(expected);
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
