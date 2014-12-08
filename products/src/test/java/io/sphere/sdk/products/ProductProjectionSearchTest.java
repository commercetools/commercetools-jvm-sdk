package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.search.*;
import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;

public class ProductProjectionSearchTest {
    private static final ProductProjectionSearchModel MODEL = ProductProjectionSearch.model();

    @Test
    public void escapesStringTerms() throws Exception {
        FilterExpression<ProductProjection> expression = new StringSearchModel<ProductProjection>(Optional.empty(), "").filter().is("some\"text");
        assertThat(expression.toSphereFilter()).isEqualTo(":\"some\\\"text\"");
    }

    @Test
    public void canCreateFacetsForCategories() throws Exception {
        TermFacetSearchModel<ProductProjection, Referenceable<Category>> facet = MODEL.categories().facet();
        assertThat(facet.allTerms().toSphereFacet()).isEqualTo("categories.id");
        assertThat(facet.only(category("some-id")).toSphereFacet()).isEqualTo("categories.id:\"some-id\"");
        assertThat(facet.only(asList(category("some-id"), category("other-id"))).toSphereFacet()).isEqualTo("categories.id:\"some-id\",\"other-id\"");
    }

    @Test
    public void canCreateTermFacets() throws Exception {
        RangeTermFacetSearchModel<ProductProjection, Money> facet = MODEL.variants().price().centAmount().facet();
        assertThat(facet.allTerms().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(facet.only(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:1000");
        assertThat(facet.only(asList(money(10), money(200))).toSphereFacet()).isEqualTo("variants.price.centAmount:1000,20000");
    }

    @Test
    public void canCreateRangeFacets() throws Exception {
        RangeTermFacetSearchModel<ProductProjection, Money> facet = MODEL.variants().price().centAmount().facet();
        assertThat(facet.allRanges().toSphereFacet()).isEqualTo("variants.price.centAmount:range(* to *)");
        assertThat(facet.onlyWithin(range(money(10), money(200))).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to 20000)");
        assertThat(facet.onlyWithin(asList(range(money(10), money(200)), range(money(300), money(1000)))).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to 20000),(30000 to 100000)");
        assertThat(facet.onlyLessThan(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:range(* to 1000)");
        assertThat(facet.onlyGreaterThan(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to *)");
    }

    @Test
    public void canCreateTermFilters() throws Exception {
        RangeTermFilterSearchModel<ProductProjection, Money> filter = MODEL.variants().price().centAmount().filter();
        assertThat(filter.is(money(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:1000");
        assertThat(filter.isIn(asList(money(10), money(200))).toSphereFilter()).isEqualTo("variants.price.centAmount:1000,20000");
    }

    @Test
    public void canCreateRangeFilters() throws Exception {
        RangeTermFilterSearchModel<ProductProjection, Money> filter = MODEL.variants().price().centAmount().filter();
        assertThat(filter.isWithin(range(money(10), money(200))).toSphereFilter()).isEqualTo("variants.price.centAmount:range(1000 to 20000)");
        assertThat(filter.isWithin(asList(range(money(10), money(200)), range(money(300), money(1000)))).toSphereFilter()).isEqualTo("variants.price.centAmount:range(1000 to 20000),(30000 to 100000)");
        assertThat(filter.isLessThan(money(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:range(* to 1000)");
        assertThat(filter.isGreaterThan(money(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:range(1000 to *)");
    }

    @Test
    public void canCreateBooleanAttributeExpressions() throws Exception {
        BooleanSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofBoolean("isHandmade");
        assertThat(attribute.facet().only(true).toSphereFacet()).isEqualTo("variants.attributes.isHandmade:true");
        assertThat(attribute.filter().is(true).toSphereFilter()).isEqualTo("variants.attributes.isHandmade:true");
    }

    @Test
    public void canCreateTextAttributeExpressions() throws Exception {
        StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofText("brand");
        assertThat(attribute.facet().only("Apple").toSphereFacet()).isEqualTo("variants.attributes.brand:\"Apple\"");
        assertThat(attribute.filter().is("Apple").toSphereFilter()).isEqualTo("variants.attributes.brand:\"Apple\"");
    }

    @Test
    public void canCreateLocTextAttributeExpressions() throws Exception {
        LocalizedStringsSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofLocalizableText("material");
        assertThat(attribute.locale(ENGLISH).facet().only("steel").toSphereFacet()).isEqualTo("variants.attributes.material.en:\"steel\"");
        assertThat(attribute.locale(ENGLISH).filter().is("steel").toSphereFilter()).isEqualTo("variants.attributes.material.en:\"steel\"");
    }

    @Test
    public void canCreateEnumAttributeExpressions() throws Exception {
        EnumSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofEnum("originCountry");
        assertThat(attribute.key().facet().only("IT").toSphereFacet()).isEqualTo("variants.attributes.originCountry.key:\"IT\"");
        assertThat(attribute.label().facet().only("IT").toSphereFacet()).isEqualTo("variants.attributes.originCountry.label:\"IT\"");
        assertThat(attribute.key().filter().is("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(attribute.label().filter().is("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.label:\"Italy\"");
    }

    @Test
    public void canCreateLocEnumAttributeExpressions() throws Exception {
        LocalizedEnumSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofLocalizableEnum("color");
        assertThat(attribute.key().facet().only("ROT").toSphereFacet()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(attribute.key().filter().is("ROT").toSphereFilter()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(attribute.label().locale(ENGLISH).facet().only("red").toSphereFacet()).isEqualTo("variants.attributes.color.label.en:\"red\"");
        assertThat(attribute.label().locale(ENGLISH).filter().is("red").toSphereFilter()).isEqualTo("variants.attributes.color.label.en:\"red\"");
    }

    @Test
    public void canCreateNumberAttributeExpressions() throws Exception {
        NumberSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofNumber("length");
        assertThat(attribute.facet().only(number(4)).toSphereFacet()).isEqualTo("variants.attributes.length:4");
        assertThat(attribute.facet().onlyGreaterThan(number(4)).toSphereFacet()).isEqualTo("variants.attributes.length:range(4 to *)");
        assertThat(attribute.filter().is(number(4)).toSphereFilter()).isEqualTo("variants.attributes.length:4");
        assertThat(attribute.filter().isGreaterThan(number(4)).toSphereFilter()).isEqualTo("variants.attributes.length:range(4 to *)");
    }

    @Test
    public void canCreateMoneyAttributeExpressions() throws Exception {
        MoneySearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofMoney("originalPrice");
        assertThat(attribute.centAmount().facet().onlyGreaterThan(money(4)).toSphereFacet()).isEqualTo("variants.attributes.originalPrice.centAmount:range(400 to *)");
        assertThat(attribute.centAmount().filter().isGreaterThan(money(4)).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.centAmount:range(400 to *)");
        assertThat(attribute.currency().facet().only(currency("EUR")).toSphereFacet()).isEqualTo("variants.attributes.originalPrice.currency:\"EUR\"");
        assertThat(attribute.currency().filter().is(currency("EUR")).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.currency:\"EUR\"");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        DateSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofDate("expirationDate");
        assertThat(attribute.facet().only(date("2001-09-11")).toSphereFacet()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(attribute.facet().onlyGreaterThan(date("1994-09-22")).toSphereFacet()).isEqualTo("variants.attributes.expirationDate:range(\"1994-09-22\" to *)");
        assertThat(attribute.filter().is(date("2001-09-11")).toSphereFilter()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(attribute.filter().isGreaterThan(date("1994-09-22")).toSphereFilter()).isEqualTo("variants.attributes.expirationDate:range(\"1994-09-22\" to *)");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        TimeSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofTime("deliveryHours");
        assertThat(attribute.facet().only(time("22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(attribute.facet().onlyGreaterThan(time("22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.deliveryHours:range(\"22:05:09.203\" to *)");
        assertThat(attribute.filter().is(time("22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(attribute.filter().isGreaterThan(time("22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.deliveryHours:range(\"22:05:09.203\" to *)");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        DateTimeSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofDateTime("createdDate");
        assertThat(attribute.facet().only(dateTime("2001-09-11T22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(attribute.facet().onlyGreaterThan(dateTime("2001-09-11T22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.createdDate:range(\"2001-09-11T22:05:09.203Z\" to *)");
        assertThat(attribute.filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(attribute.filter().isGreaterThan(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.createdDate:range(\"2001-09-11T22:05:09.203Z\" to *)");
    }

    @Test
    public void canCreateReferenceAttributeExpressions() throws Exception {
        ReferenceSearchModel<ProductProjection, Product> attribute = MODEL.variants().attribute().ofReference("recommendedProduct");
        assertThat(attribute.facet().only(product("some-id")).toSphereFacet()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
        assertThat(attribute.filter().is(product("some-id")).toSphereFilter()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    private Product product(String id) {
        ProductType productType = ProductTypeBuilder.of("some-other-id", "", "", asList()).build();
        return ProductBuilder.of(productType, null).id(id).build();
    }

    private LocalDateTime dateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime);
    }

    private LocalTime time(final String time) {
        return LocalTime.parse(time);
    }

    private LocalDate date(final String date) {
        return LocalDate.parse(date);
    }

    private Currency currency(final String currencyCode) {
        return Currency.getInstance(currencyCode);
    }

    private BigDecimal number(final long number) {
        return new BigDecimal(number);
    }

    private Range<Money> range(Money lowerEndpoint, Money upperEndpoint) {
        Bound<Money> lowerBound = Bound.exclusive(lowerEndpoint);
        Bound<Money> upperBound = Bound.exclusive(upperEndpoint);
        return Range.of(lowerBound, upperBound);
    }

    private Reference<Category> category(String id) {
        return new Reference<>("category", id, Optional.empty());
    }

    private Money money(double amount) {
        return Money.of(new BigDecimal(amount), "EUR");
    }
}
