package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductBuilder;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.search.*;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.junit.Test;

import javax.money.CurrencyContext;
import javax.money.CurrencyUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static io.sphere.sdk.products.search.VariantSearchSortDirection.*;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.fest.assertions.Assertions.assertThat;

public class ProductProjectionSearchTest {
    private static final ExperimentalProductProjectionSearchModel MODEL = ProductProjectionSearch.model();

    @Test
    public void canAccessProductName() throws Exception {
        assertThat(MODEL.name().locale(ENGLISH).facet().all().toSphereFacet()).isEqualTo("name.en");
        assertThat(MODEL.name().locale(ENGLISH).filter().is("foo").toSphereFilter()).isEqualTo("name.en:\"foo\"");
        assertThat(MODEL.name().locale(ENGLISH).sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("name.en asc");
    }

    @Test
    public void canAccessCreatedAt() throws Exception {
        assertThat(MODEL.createdAt().facet().all().toSphereFacet()).isEqualTo("createdAt");
        assertThat(MODEL.createdAt().filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(MODEL.createdAt().sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("createdAt asc");
    }

    @Test
    public void canAccessLastModifiedAt() throws Exception {
        assertThat(MODEL.lastModifiedAt().facet().all().toSphereFacet()).isEqualTo("lastModifiedAt");
        assertThat(MODEL.lastModifiedAt().filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(MODEL.lastModifiedAt().sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void canAccessCategories() throws Exception {
        assertThat(MODEL.categories().facet().all().toSphereFacet()).isEqualTo("categories.id");
        assertThat(MODEL.categories().filter().is(category("some-id")).toSphereFilter()).isEqualTo("categories.id:\"some-id\"");
    }

    @Test
    public void canAccessPriceAmount() throws Exception {
        assertThat(MODEL.allVariants().price().amount().facet().all().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(MODEL.allVariants().price().amount().filter().is(valueOf(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:1000");
        assertThat(MODEL.allVariants().price().amount().sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("price asc");
    }

    @Test
    public void canAccessPriceCurrency() throws Exception {
        assertThat(MODEL.allVariants().price().currency().facet().all().toSphereFacet()).isEqualTo("variants.price.currencyCode");
        assertThat(MODEL.allVariants().price().currency().filter().is(currency("EUR")).toSphereFilter()).isEqualTo("variants.price.currencyCode:\"EUR\"");
        assertThat(MODEL.allVariants().price().currency().sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("variants.price.currencyCode asc");
    }

    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final String attrName = "brand";
        assertThat(attributeModel().ofText(attrName).facet().all().toSphereFacet()).isEqualTo("variants.attributes.brand");
        assertThat(attributeModel().ofText(attrName).filter().is("Apple").toSphereFilter()).isEqualTo("variants.attributes.brand:\"Apple\"");
        assertThat(attributeModel().ofText(attrName).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final String attrName = "material";
        assertThat(attributeModel().ofLocalizableText(attrName).locale(ENGLISH).facet().all().toSphereFacet()).isEqualTo("variants.attributes.material.en");
        assertThat(attributeModel().ofLocalizableText(attrName).locale(ENGLISH).filter().is("steel").toSphereFilter()).isEqualTo("variants.attributes.material.en:\"steel\"");
        assertThat(attributeModel().ofLocalizableText(attrName).locale(ENGLISH).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.material.en asc.max");
    }

    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final String attrName = "isHandmade";
        assertThat(attributeModel().ofBoolean(attrName).facet().all().toSphereFacet()).isEqualTo("variants.attributes.isHandmade");
        assertThat(attributeModel().ofBoolean(attrName).filter().is(true).toSphereFilter()).isEqualTo("variants.attributes.isHandmade:true");
        assertThat(attributeModel().ofBoolean(attrName).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        final String attrName = "length";
        assertThat(attributeModel().ofNumber(attrName).facet().all().toSphereFacet()).isEqualTo("variants.attributes.length");
        assertThat(attributeModel().ofNumber(attrName).filter().is(valueOf(4)).toSphereFilter()).isEqualTo("variants.attributes.length:4");
        assertThat(attributeModel().ofNumber(attrName).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.length asc.max");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        final String attrName = "expirationDate";
        assertThat(attributeModel().ofDate(attrName).facet().all().toSphereFacet()).isEqualTo("variants.attributes.expirationDate");
        assertThat(attributeModel().ofDate(attrName).filter().is(date("2001-09-11")).toSphereFilter()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(attributeModel().ofDate(attrName).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        final String attrName = "deliveryHours";
        assertThat(attributeModel().ofTime(attrName).facet().all().toSphereFacet()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(attributeModel().ofTime(attrName).filter().is(time("22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(attributeModel().ofTime(attrName).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.deliveryHours asc.max");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        final String attrName = "createdDate";
        assertThat(attributeModel().ofDateTime(attrName).facet().all().toSphereFacet()).isEqualTo("variants.attributes.createdDate");
        assertThat(attributeModel().ofDateTime(attrName).filter().is(dateTime("2001-09-11T22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(attributeModel().ofDateTime(attrName).sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final String attrName = "originCountry";
        assertThat(attributeModel().ofEnum(attrName).key().facet().all().toSphereFacet()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(attributeModel().ofEnum(attrName).key().filter().is("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(attributeModel().ofEnum(attrName).key().sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.key asc.max");
    }

    @Test
    public void canAccessMoneyAmountCustomAttributes() throws Exception {
        final String attrName = "originalPrice";
        assertThat(attributeModel().ofMoney(attrName).amount().facet().all().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(attributeModel().ofMoney(attrName).amount().filter().is(valueOf(10)).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.centAmount:1000");
        assertThat(attributeModel().ofMoney(attrName).amount().sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final String attrName = "originalPrice";
        assertThat(attributeModel().ofMoney(attrName).currency().facet().all().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(attributeModel().ofMoney(attrName).currency().filter().is(currency("EUR")).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.currencyCode:\"EUR\"");
        assertThat(attributeModel().ofMoney(attrName).currency().sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.currencyCode asc.max");
    }

    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        final String attrName = "recommendedProduct";
        final ReferenceSearchModel<ProductProjection, Product> referenceModel = attributeModel().ofReference(attrName);
        assertThat(referenceModel.facet().all().toSphereFacet()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(referenceModel.filter().is(product("some-id")).toSphereFilter()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        final String attrName = "originCountry";
        assertThat(attributeModel().ofEnum(attrName).label().facet().all().toSphereFacet()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(attributeModel().ofEnum(attrName).label().filter().is("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(attributeModel().ofEnum(attrName).label().sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        final String attrName = "color";
        assertThat(attributeModel().ofLocalizableEnum(attrName).key().facet().all().toSphereFacet()).isEqualTo("variants.attributes.color.key");
        assertThat(attributeModel().ofLocalizableEnum(attrName).key().filter().is("ROT").toSphereFilter()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(attributeModel().ofLocalizableEnum(attrName).key().sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.color.key asc.max");
    }

    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        final String attrName = "color";
        assertThat(attributeModel().ofLocalizableEnum(attrName).label().locale(ENGLISH).facet().all().toSphereFacet()).isEqualTo("variants.attributes.color.label.en");
        assertThat(attributeModel().ofLocalizableEnum(attrName).label().locale(ENGLISH).filter().is("red").toSphereFilter()).isEqualTo("variants.attributes.color.label.en:\"red\"");
        assertThat(attributeModel().ofLocalizableEnum(attrName).label().locale(ENGLISH).sort(ASC).toSphereSort()).isEqualTo("variants.attributes.color.label.en asc");
    }

    @Test
    public void usesAlias() throws Exception {
        final String attrName = "size";
        assertThat(attributeModel().ofNumber(attrName).facet().withAlias("my-facet").all().toSphereFacet()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(attributeModel().ofNumber(attrName).facet().withAlias("my-facet").only(valueOf(38)).toSphereFacet()).isEqualTo("variants.attributes.size:38 as my-facet");
        assertThat(attributeModel().ofNumber(attrName).facet().withAlias("my-facet").onlyLessThan(valueOf(38)).toSphereFacet()).isEqualTo("variants.attributes.size:range(* to 38) as my-facet");
    }

    @Test
    public void unicode() throws Exception {
        final String path = ProductProjectionSearch.of(ProductProjectionType.STAGED).withText(GERMAN, "öón").httpRequestIntent().getPath();
        final String expected = "/product-projections/search?text.de=%C3%B6%C3%B3n&staged=true";
        assertThat(path).isEqualTo(expected);
    }

    private ProductAttributeSearchModel attributeModel() {
        return MODEL.allVariants().attribute();
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

    private Reference<Category> category(String id) {
        return Reference.of("category", id);
    }
}
