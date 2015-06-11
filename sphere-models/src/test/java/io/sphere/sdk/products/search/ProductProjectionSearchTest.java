package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.search.*;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collections;

import static io.sphere.sdk.products.search.VariantSearchSortDirection.*;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchTest {
    private static final ExperimentalProductProjectionSearchModel MODEL = ProductProjectionSearch.model();

    @Test
    public void canAccessProductName() throws Exception {
        final StringSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.name().locale(ENGLISH);
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("name.en");
        assertThat(path.filterBy().exactly("foo").toSphereFilter()).isEqualTo("name.en:\"foo\"");
        assertThat(path.sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("name.en asc");
    }

    @Test
    public void canAccessCreatedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.createdAt();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("createdAt");
        assertThat(path.filterBy().exactly(dateTime("2001-09-11T22:05:09.203+00:00")).toSphereFilter()).isEqualTo("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("createdAt asc");
    }

    @Test
    public void canAccessLastModifiedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.lastModifiedAt();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("lastModifiedAt");
        assertThat(path.filterBy().exactly(dateTime("2001-09-11T22:05:09.203+00:00")).toSphereFilter()).isEqualTo("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void canAccessCategories() throws Exception {
        final ReferenceSearchModel<ProductProjection, Category> path = MODEL.categories();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("categories.id");
        assertThat(path.filterBy().exactly(category("some-id")).toSphereFilter()).isEqualTo("categories.id:\"some-id\"");
    }

    @Test
    public void canAccessProductType() throws Exception {
        final ReferenceSearchModel<ProductProjection, ProductType> path = MODEL.productType();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("productType.id");
        assertThat(path.filterBy().exactly(productType("some-id")).toSphereFilter()).isEqualTo("productType.id:\"some-id\"");
    }

    @Test
    public void canAccessPriceAmount() throws Exception {
        final MoneyAmountSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.allVariants().price().amount();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(path.filterBy().exactly(valueOf(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:1000");
        assertThat(path.sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("price asc");
    }

    @Test
    public void canAccessPriceCurrency() throws Exception {
        final CurrencySearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.allVariants().price().currency();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.price.currencyCode");
        assertThat(path.filterBy().exactly(currency("EUR")).toSphereFilter()).isEqualTo("variants.price.currencyCode:\"EUR\"");
        assertThat(path.sort(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("variants.price.currencyCode asc");
    }

    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofText("brand");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.brand");
        assertThat(path.filterBy().exactly("Apple").toSphereFilter()).isEqualTo("variants.attributes.brand:\"Apple\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofLocalizableText("material").locale(ENGLISH);
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.material.en");
        assertThat(path.filterBy().exactly("steel").toSphereFilter()).isEqualTo("variants.attributes.material.en:\"steel\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.material.en asc.max");
    }

    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final BooleanSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofBoolean("isHandmade");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.isHandmade");
        assertThat(path.filterBy().exactly(true).toSphereFilter()).isEqualTo("variants.attributes.isHandmade:true");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        final NumberSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofNumber("length");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.length");
        assertThat(path.filterBy().exactly(valueOf(4)).toSphereFilter()).isEqualTo("variants.attributes.length:4");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.length asc.max");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        final DateSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofDate("expirationDate");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.expirationDate");
        assertThat(path.filterBy().exactly(date("2001-09-11")).toSphereFilter()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        final TimeSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofTime("deliveryHours");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(path.filterBy().exactly(time("22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.deliveryHours asc.max");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        final DateTimeSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofDateTime("createdDate");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.createdDate");
        assertThat(path.filterBy().exactly(dateTime("2001-09-11T22:05:09.203+00:00")).toSphereFilter()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofEnum("originCountry").key();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(path.filterBy().exactly("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.key asc.max");
    }

    @Test
    public void canAccessMoneyAmountCustomAttributes() throws Exception {
        final MoneyAmountSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofMoney("originalPrice").amount();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(path.filterBy().exactly(valueOf(10)).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.centAmount:1000");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final CurrencySearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofMoney("originalPrice").currency();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(path.filterBy().exactly(currency("EUR")).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.currencyCode:\"EUR\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.currencyCode asc.max");
    }

    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        final ReferenceSearchModel<ProductProjection, Product> path = attributeModel().ofReference("recommendedProduct");
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(path.filterBy().exactly(product("some-id")).toSphereFilter()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofEnum("originCountry").label();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(path.filterBy().exactly("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofLocalizableEnum("color").key();
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.color.key");
        assertThat(path.filterBy().exactly("ROT").toSphereFilter()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(path.sort(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.color.key asc.max");
    }

    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofLocalizableEnum("color").label().locale(ENGLISH);
        assertThat(path.facetOf().all().toSphereFacet()).isEqualTo("variants.attributes.color.label.en");
        assertThat(path.filterBy().exactly("red").toSphereFilter()).isEqualTo("variants.attributes.color.label.en:\"red\"");
        assertThat(path.sort(ASC).toSphereSort()).isEqualTo("variants.attributes.color.label.en asc");
    }

    @Test
    public void usesAlias() throws Exception {
        final NumberSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofNumber("size");
        assertThat(path.facetOf().withAlias("my-facet").all().toSphereFacet()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(path.facetOf().withAlias("my-facet").only(valueOf(38)).toSphereFacet()).isEqualTo("variants.attributes.size:38 as my-facet");
        assertThat(path.facetOf().withAlias("my-facet").lessThan(valueOf(38)).toSphereFacet()).isEqualTo("variants.attributes.size:range(* to 38) as my-facet");
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
        final ProductVariant emptyProductVariant = ProductVariantBuilder.of(1).sku("sku-5000").get();
        final LocalizedStrings name = LocalizedStrings.of(ENGLISH, "name");
        final LocalizedStrings slug = LocalizedStrings.of(ENGLISH, "slug");
        final ProductData staged = ProductDataBuilder.of(name, slug, emptyProductVariant).build();
        final ProductCatalogData masterData = ProductCatalogDataBuilder.ofStaged(staged).get();
        return ProductBuilder.of(productType, masterData).id(id).build();
    }

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

    private Reference<Category> category(String id) {
        return Reference.of("category", id);
    }

    private Reference<ProductType> productType(String id) {
        return Reference.of("productType", id);
    }
}
