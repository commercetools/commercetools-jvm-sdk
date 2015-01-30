package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductBuilder;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.search.*;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.junit.Test;

import javax.money.CurrencyContext;
import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.search.SearchSortDirection.*;

public class ProductProjectionSearchTest {
    private static final ProductProjectionSearchModel MODEL = ProductProjectionSearch.model();

    @Test
    public void canAccessProductName() throws Exception {
        final LocalizedStringsSearchModel<ProductProjection> name = MODEL.name();
        assertThat(name.locale(ENGLISH).facet().allTerms().toSphereFacet()).isEqualTo("name.en");
        assertThat(name.locale(ENGLISH).filter().is("foo").toSphereFilter()).isEqualTo("name.en:\"foo\"");
        assertThat(name.locale(ENGLISH).sort(ASC).toSphereSort()).isEqualTo("name.en asc");
    }

    @Test
    public void canAccessCreatedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection> createdAt = MODEL.createdAt();
        assertThat(createdAt.facet().allTerms().toSphereFacet()).isEqualTo("createdAt");
        assertThat(createdAt.filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(createdAt.sort(ASC).toSphereSort()).isEqualTo("createdAt asc");
    }

    @Test
    public void canAccessLastModifiedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection> lastModifiedAt = MODEL.lastModifiedAt();
        assertThat(lastModifiedAt.facet().allTerms().toSphereFacet()).isEqualTo("lastModifiedAt");
        assertThat(lastModifiedAt.filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(lastModifiedAt.sort(ASC).toSphereSort()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void canAccessCategories() throws Exception {
        final ReferenceSearchModel<ProductProjection, Category> categories = MODEL.categories();
        assertThat(categories.facet().allTerms().toSphereFacet()).isEqualTo("categories.id");
        assertThat(categories.filter().is(category("some-id")).toSphereFilter()).isEqualTo("categories.id:\"some-id\"");
    }

    @Test
    public void canAccessPriceAmount() throws Exception {
        final MoneyAmountSearchModel<ProductProjection> amount = MODEL.variants().price().amount();
        assertThat(amount.facet().allTerms().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(amount.filter().is(money(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:1000");
        assertThat(amount.sort(ASC).toSphereSort()).isEqualTo("variants.price.centAmount asc");
    }

    @Test
    public void canAccessPriceCurrency() throws Exception {
        final CurrencySearchModel<ProductProjection> currency = MODEL.variants().price().currency();
        assertThat(currency.facet().allTerms().toSphereFacet()).isEqualTo("variants.price.currencyCode");
        assertThat(currency.filter().is(currency("EUR")).toSphereFilter()).isEqualTo("variants.price.currencyCode:\"eur\"");
        assertThat(currency.sort(ASC).toSphereSort()).isEqualTo("variants.price.currencyCode asc");
    }

    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofText("brand");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.brand");
        assertThat(attribute.filter().is("Apple").toSphereFilter()).isEqualTo("variants.attributes.brand:\"Apple\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofLocalizableText("material").locale(ENGLISH);
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.material.en");
        assertThat(attribute.filter().is("steel").toSphereFilter()).isEqualTo("variants.attributes.material.en:\"steel\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.material.en asc.max");
    }

    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final BooleanSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofBoolean("isHandmade");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.isHandmade");
        assertThat(attribute.filter().is(true).toSphereFilter()).isEqualTo("variants.attributes.isHandmade:true");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        NumberSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofNumber("length");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.length");
        assertThat(attribute.filter().is(number(4)).toSphereFilter()).isEqualTo("variants.attributes.length:4");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.length asc.max");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        DateSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofDate("expirationDate");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(attribute.filter().is(date("2001-09-11")).toSphereFilter()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        TimeSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofTime("deliveryHours");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(attribute.filter().is(time("22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.deliveryHours asc.max");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        DateTimeSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofDateTime("createdDate");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.createdDate");
        assertThat(attribute.filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofEnum("originCountry").key();
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(attribute.filter().is("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.key asc.max");
    }

    @Test
    public void canAccessMoneyAmountCustomAttributes() throws Exception {
        final MoneyAmountSearchModel<ProductProjection> amount = MODEL.variants().attribute().ofMoney("originalPrice").amount();
        assertThat(amount.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(amount.filter().is(money(10)).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.centAmount:1000");
        assertThat(amount.sort(ASC).toSphereSort()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final CurrencySearchModel<ProductProjection> currency = MODEL.variants().attribute().ofMoney("originalPrice").currency();
        assertThat(currency.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(currency.filter().is(currency("EUR")).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.currencyCode:\"eur\"");
        assertThat(currency.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.currencyCode asc.max");
    }

    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        ReferenceSearchModel<ProductProjection, Product> attribute = MODEL.variants().attribute().ofReference("recommendedProduct");
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(attribute.filter().is(product("some-id")).toSphereFilter()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofEnum("originCountry").label();
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(attribute.filter().is("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofLocalizableEnum("color").key();
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.color.key");
        assertThat(attribute.filter().is("ROT").toSphereFilter()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(attribute.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.color.key asc");
    }

    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        StringSearchModel<ProductProjection> attribute = MODEL.variants().attribute().ofLocalizableEnum("color").label().locale(ENGLISH);
        assertThat(attribute.facet().allTerms().toSphereFacet()).isEqualTo("variants.attributes.color.label.en");
        assertThat(attribute.filter().is("red").toSphereFilter()).isEqualTo("variants.attributes.color.label.en:\"red\"");
        assertThat(attribute.sort(ASC).toSphereSort()).isEqualTo("variants.attributes.color.label.en asc");
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

    private CurrencyUnit currency(final String currencyCode) {
        return CurrencyUnitBuilder.of(currencyCode, CurrencyContext.KEY_PROVIDER).build();
    }

    private BigDecimal number(final long number) {
        return new BigDecimal(number);
    }

    private Reference<Category> category(String id) {
        return Reference.of("category", id);
    }

    private BigDecimal money(final double amount) {
        return new BigDecimal(amount);
    }
}
