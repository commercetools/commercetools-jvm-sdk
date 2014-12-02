package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.search.ProductProjectionSearch;
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

    @Test
    public void escapesStringTerms() throws Exception {
        FacetExpression<ProductProjectionSearch> expression = new StringSearchModel<ProductProjectionSearch>(Optional.empty(), "").is("some\"text");
        assertThat(expression.toSphereFacet()).isEqualTo(":\"some\\\"text\"");
    }

    @Test
    public void canCreateFacetsForCategories() throws Exception {
        ReferenceSearchModel<ProductProjection, Category> categoryFacet = ProductProjectionSearch.model().categories();
        assertThat(categoryFacet.anyTerm().toSphereFacet()).isEqualTo("categories.id");
        assertThat(categoryFacet.is(category("some-id")).toSphereFacet()).isEqualTo("categories.id:\"some-id\"");
        assertThat(categoryFacet.isIn(asList(category("some-id"), category("other-id"))).toSphereFacet()).isEqualTo("categories.id:\"some-id\",\"other-id\"");
    }

    @Test
    public void canCreateTermFacets() throws Exception {
        MoneySearchModel<ProductProjection> money = ProductProjectionSearch.model().variants().price();
        assertThat(money.centAmount().anyTerm().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(money.centAmount().is(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:1000");
        assertThat(money.centAmount().isIn(asList(money(10), money(200))).toSphereFacet()).isEqualTo("variants.price.centAmount:1000,20000");
    }

    @Test
    public void canCreateRangeFacets() throws Exception {
        MoneyAmountSearchModel<ProductProjection> moneyFacet = ProductProjectionSearch.model().variants().price().centAmount();
        assertThat(moneyFacet.anyRange().toSphereFacet()).isEqualTo("variants.price.centAmount:range(* to *)");
        assertThat(moneyFacet.isWithin(range(money(10), money(200))).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to 20000)");
        assertThat(moneyFacet.isWithin(asList(range(money(10), money(200)), range(money(300), money(1000)))).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to 20000),(30000 to 100000)");
        assertThat(moneyFacet.isLessThan(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:range(* to 1000)");
        assertThat(moneyFacet.isGreaterThan(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to *)");
    }

    @Test
    public void canCreateBooleanAttributeFacets() throws Exception {
        BooleanSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofBoolean("name");
        assertThat(attribute.is(true).toSphereFacet()).isEqualTo("variants.attributes.name:true");
    }

    @Test
    public void canCreateTextAttributeFacets() throws Exception {
        StringSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofText("name");
        assertThat(attribute.is("value").toSphereFacet()).isEqualTo("variants.attributes.name:\"value\"");
    }

    @Test
    public void canCreateLocTextAttributeFacets() throws Exception {
        LocalizedStringsSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofLocalizableText("name");
        assertThat(attribute.locale(ENGLISH).is("value").toSphereFacet()).isEqualTo("variants.attributes.name.en:\"value\"");
    }

    @Test
    public void canCreateEnumAttributeFacets() throws Exception {
        EnumSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofEnum("name");
        assertThat(attribute.key().is("key").toSphereFacet()).isEqualTo("variants.attributes.name.key:\"key\"");
        assertThat(attribute.label().is("label").toSphereFacet()).isEqualTo("variants.attributes.name.label:\"label\"");
    }

    @Test
    public void canCreateLocEnumAttributeFacets() throws Exception {
        LocalizedEnumSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofLocalizableEnum("name");
        assertThat(attribute.label().locale(ENGLISH).is("label").toSphereFacet()).isEqualTo("variants.attributes.name.label.en:\"label\"");
    }

    @Test
    public void canCreateNumberAttributeFacets() throws Exception {
        NumberSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofNumber("name");
        assertThat(attribute.is(number(4)).toSphereFacet()).isEqualTo("variants.attributes.name:4");
        assertThat(attribute.isGreaterThan(number(4)).toSphereFacet()).isEqualTo("variants.attributes.name:range(4 to *)");
    }

    @Test
    public void canCreateMoneyAttributeFacets() throws Exception {
        MoneySearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofMoney("name");
        assertThat(attribute.centAmount().isGreaterThan(money(4)).toSphereFacet()).isEqualTo("variants.attributes.name.centAmount:range(400 to *)");
        assertThat(attribute.currency().is(currency("EUR")).toSphereFacet()).isEqualTo("variants.attributes.name.currency:\"EUR\"");
    }

    @Test
    public void canCreateDateAttributeFacets() throws Exception {
        DateSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofDate("name");
        assertThat(attribute.is(date("2001-09-11")).toSphereFacet()).isEqualTo("variants.attributes.name:\"2001-09-11\"");
        assertThat(attribute.isGreaterThan(date("1994-09-22")).toSphereFacet()).isEqualTo("variants.attributes.name:range(\"1994-09-22\" to *)");
    }

    @Test
    public void canCreateTimeAttributeFacets() throws Exception {
        TimeSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofTime("name");
        assertThat(attribute.is(time("22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.name:\"22:05:09.203\"");
        assertThat(attribute.isGreaterThan(time("22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.name:range(\"22:05:09.203\" to *)");
    }

    @Test
    public void canCreateDateTimeAttributeFacets() throws Exception {
        DateTimeSearchModel<ProductProjection> attribute = ProductProjectionSearch.model().variants().attributes().ofDateTime("name");
        assertThat(attribute.is(dateTime("2001-09-11T22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.name:\"2001-09-11T22:05:09.203Z\"");
        assertThat(attribute.isGreaterThan(dateTime("2001-09-11T22:05:09.203")).toSphereFacet()).isEqualTo("variants.attributes.name:range(\"2001-09-11T22:05:09.203Z\" to *)");
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
