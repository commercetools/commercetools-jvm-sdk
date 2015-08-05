package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.*;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static io.sphere.sdk.products.search.VariantSearchSortDirection.ASC;
import static io.sphere.sdk.products.search.VariantSearchSortDirection.ASC_MAX;
import static io.sphere.sdk.test.SphereTestUtils.stringFromResource;
import static java.math.BigDecimal.valueOf;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchTest {
    private static final ProductProjectionSearchModel MODEL = ProductProjectionSearchModel.of();

    @Test
    public void canAccessProductName() throws Exception {
        final StringSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.name().locale(ENGLISH);
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("name.en");
        assertThat(path.filtered().by("foo").toSphereFilter()).isEqualTo("name.en:\"foo\"");
        assertThat(path.sorted(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("name.en asc");
    }

    @Test
    public void canAccessCreatedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.createdAt();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("createdAt");
        assertThat(path.filtered().by(dateTime("2001-09-11T22:05:09.203+00:00")).toSphereFilter()).isEqualTo("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sorted(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("createdAt asc");
    }

    @Test
    public void canAccessLastModifiedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.lastModifiedAt();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("lastModifiedAt");
        assertThat(path.filtered().by(dateTime("2001-09-11T22:05:09.203+00:00")).toSphereFilter()).isEqualTo("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sorted(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void canAccessCategories() throws Exception {
        final ReferenceSearchModel<ProductProjection, Category> path = MODEL.categories();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("categories.id");
        assertThat(path.filtered().by(category("some-id")).toSphereFilter()).isEqualTo("categories.id:\"some-id\"");
    }

    @Test
    public void canAccessProductType() throws Exception {
        final ReferenceSearchModel<ProductProjection, ProductType> path = MODEL.productType();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("productType.id");
        assertThat(path.filtered().by(productType("some-id")).toSphereFilter()).isEqualTo("productType.id:\"some-id\"");
    }

    @Test
    public void canAccessPriceAmount() throws Exception {
        final MoneyAmountSearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.allVariants().price().amount();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.price.centAmount");
        assertThat(path.filtered().by(valueOf(10)).toSphereFilter()).isEqualTo("variants.price.centAmount:1000");
        assertThat(path.sorted(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("price asc");
    }

    @Test
    public void canAccessPriceCurrency() throws Exception {
        final CurrencySearchModel<ProductProjection, SimpleSearchSortDirection> path = MODEL.allVariants().price().currency();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.price.currencyCode");
        assertThat(path.filtered().by(currency("EUR")).toSphereFilter()).isEqualTo("variants.price.currencyCode:\"EUR\"");
        assertThat(path.sorted(SimpleSearchSortDirection.ASC).toSphereSort()).isEqualTo("variants.price.currencyCode asc");
    }

    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofText("brand");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.brand");
        assertThat(path.filtered().by("Apple").toSphereFilter()).isEqualTo("variants.attributes.brand:\"Apple\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofLocalizableText("material").locale(ENGLISH);
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.material.en");
        assertThat(path.filtered().by("steel").toSphereFilter()).isEqualTo("variants.attributes.material.en:\"steel\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.material.en asc.max");
    }

    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final BooleanSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofBoolean("isHandmade");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.isHandmade");
        assertThat(path.filtered().by(true).toSphereFilter()).isEqualTo("variants.attributes.isHandmade:true");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        final NumberSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofNumber("length");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.length");
        assertThat(path.filtered().by(valueOf(4)).toSphereFilter()).isEqualTo("variants.attributes.length:4");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.length asc.max");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        final DateSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofDate("expirationDate");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.expirationDate");
        assertThat(path.filtered().by(date("2001-09-11")).toSphereFilter()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        final TimeSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofTime("deliveryHours");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(path.filtered().by(time("22:05:09.203")).toSphereFilter()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.deliveryHours asc.max");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        final DateTimeSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofDateTime("createdDate");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.createdDate");
        assertThat(path.filtered().by(dateTime("2001-09-11T22:05:09.203+00:00")).toSphereFilter()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofEnum("originCountry").key();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(path.filtered().by("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.key asc.max");
    }

    @Test
    public void canAccessMoneyAmountCustomAttributes() throws Exception {
        final MoneyAmountSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofMoney("originalPrice").amount();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(path.filtered().by(valueOf(10)).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.centAmount:1000");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final CurrencySearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofMoney("originalPrice").currency();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(path.filtered().by(currency("EUR")).toSphereFilter()).isEqualTo("variants.attributes.originalPrice.currencyCode:\"EUR\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originalPrice.currencyCode asc.max");
    }

    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        final ReferenceSearchModel<ProductProjection, Product> path = attributeModel().ofProductReference("recommendedProduct");
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(path.filtered().by(product("some-id")).toSphereFilter()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofEnum("originCountry").label();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(path.filtered().by("Italy").toSphereFilter()).isEqualTo("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofLocalizableEnum("color").key();
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.color.key");
        assertThat(path.filtered().by("ROT").toSphereFilter()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(path.sorted(ASC_MAX).toSphereSort()).isEqualTo("variants.attributes.color.key asc.max");
    }

    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofLocalizableEnum("color").label().locale(ENGLISH);
        assertThat(path.faceted().byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.color.label.en");
        assertThat(path.filtered().by("red").toSphereFilter()).isEqualTo("variants.attributes.color.label.en:\"red\"");
        assertThat(path.sorted(ASC).toSphereSort()).isEqualTo("variants.attributes.color.label.en asc");
    }

    @Test
    public void usesAlias() throws Exception {
        final NumberSearchModel<ProductProjection, VariantSearchSortDirection> path = attributeModel().ofNumber("size");
        assertThat(path.faceted().withAlias("my-facet").byAllTerms().toSphereFacet()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(path.faceted().withAlias("my-facet").byTerm(valueOf(38)).toSphereFacet()).isEqualTo("variants.attributes.size:38 as my-facet");
        assertThat(path.faceted().withAlias("my-facet").byLessThan(valueOf(38)).toSphereFacet()).isEqualTo("variants.attributes.size:range(* to 38) as my-facet");
    }

    @Test
    public void unicode() throws Exception {
        final String path = ProductProjectionSearch.ofStaged().withText(GERMAN, "öón").httpRequestIntent().getPath();
        final String expected = "/product-projections/search?text.de=%C3%B6%C3%B3n&staged=true";
        assertThat(path).isEqualTo(expected);
    }

    private ProductAttributeSearchModel attributeModel() {
        return MODEL.allVariants().attribute();
    }

    private Product product(final String id) throws Exception {
        final String productJson = stringFromResource("ProductProjectionSearchTest/product.json")
                .replace("eb85ee2d-a5e5-4e15-a8ba-91281e599d68", id);
        return SphereJsonUtils.readObject(productJson, Product.typeReference());
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
