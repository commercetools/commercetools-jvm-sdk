package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.*;
import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class ProductProjectionSearchTest {

    @Test
    public void escapesStringTerms() throws Exception {
        FacetExpression<ProductProjectionSearch> expression = new StringSearchModel<ProductProjectionSearch>(Optional.empty(), "").is("some\"text");
        assertThat(expression.toSphereFacet()).isEqualTo(":\"some\\\"text\"");
    }

    @Test
    public void canCreateFacetsForCategories() throws Exception {
        ReferenceListSearchModel<ProductProjectionSearch, Category> categoryFacet = ProductProjectionSearch.model().categories();
        assertThat(categoryFacet.any().toSphereFacet()).isEqualTo("categories.id");
        assertThat(categoryFacet.is(category("some-id")).toSphereFacet()).isEqualTo("categories.id:\"some-id\"");
        assertThat(categoryFacet.isIn(asList(category("some-id"), category("other-id"))).toSphereFacet()).isEqualTo("categories.id:\"some-id\",\"other-id\"");
    }

    @Test
    public void canCreateTermFacetsForPrice() throws Exception {
        MoneyAmountSearchModel<ProductProjectionSearch> moneyFacet = ProductProjectionSearch.model().variants().price().centAmount();
        assertThat(moneyFacet.anyTerm().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(moneyFacet.is(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:1000");
        assertThat(moneyFacet.isIn(asList(money(10), money(200))).toSphereFacet()).isEqualTo("variants.price.centAmount:1000,20000");
    }

    @Test
    public void canCreateRangeFacetsForPrice() throws Exception {
        MoneyAmountSearchModel<ProductProjectionSearch> moneyFacet = ProductProjectionSearch.model().variants().price().centAmount();
        assertThat(moneyFacet.anyRange().toSphereFacet()).isEqualTo("variants.price.centAmount:range(* to *)");
        assertThat(moneyFacet.isWithin(range(money(10), money(200))).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to 20000)");
        assertThat(moneyFacet.isWithin(asList(range(money(10), money(200)), range(money(300), money(1000)))).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to 20000),(30000 to 100000)");
        assertThat(moneyFacet.isLessThan(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:range(* to 1000)");
        assertThat(moneyFacet.isGreaterThan(money(10)).toSphereFacet()).isEqualTo("variants.price.centAmount:range(1000 to *)");
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
