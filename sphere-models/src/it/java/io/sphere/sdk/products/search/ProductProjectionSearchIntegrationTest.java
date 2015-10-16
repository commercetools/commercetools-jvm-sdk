package io.sphere.sdk.products.search;

import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.LocalizedString.ofEnglishLocale;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 *            |  Product 1 "shoe"   |  Product 2 "shirt"  |  Product 3 "dress"
 *            --------------------------------------------------------------------
 * Variant    |    1     |    2     |    1     |    2     |    1     |    2
 * -------------------------------------------------------------------------------
 * Color      |   blue   |    -     |   red    |    -     |   blue   |    -
 * Size       |    38    |    46    |    36    |    44    |    40    |    42
 *
 */

public class ProductProjectionSearchIntegrationTest extends IntegrationTest {
    protected static final String EVIL_CHARACTER_WORD = "öóßàç";

    protected static Product product1;
    protected static Product product2;
    protected static Product product3;
    protected static ProductType productType;

    protected static Product evilProduct1;
    protected static Product evilProduct2;
    private static ProductType evilProductType;

    private static final String TEST_CLASS_NAME = ProductProjectionSearchIntegrationTest.class.getSimpleName();
    private static final String EVIL_PRODUCT_TYPE_NAME = "Evil" + TEST_CLASS_NAME;
    private static final String SKU_A = EVIL_PRODUCT_TYPE_NAME + "-skuA";
    private static final String SKU_B = EVIL_PRODUCT_TYPE_NAME + "-skuB";
    private static final String PRODUCT_TYPE_NAME = TEST_CLASS_NAME;
    private static final String SKU1 = PRODUCT_TYPE_NAME + "-sku1";
    private static final String SKU2 = PRODUCT_TYPE_NAME + "-sku2";
    private static final String SKU3 = PRODUCT_TYPE_NAME + "-sku3";
    protected static final String ATTR_NAME_COLOR = ("Color" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));
    protected static final String ATTR_NAME_SIZE = ("Size" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));
    protected static final String ATTR_NAME_EVIL = ("Evil" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));

    @Rule
    public RetryIntegrationTest retry = new RetryIntegrationTest(10, 10000, LoggerFactory.getLogger(this.getClass()));

    @BeforeClass
    public static void setupProducts() {
        productType = execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createProductType());

        evilProductType = execute(ProductTypeQuery.of().byName(EVIL_PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createEvilProductType());

        final Query<Product> query = ProductQuery.of()
                .withPredicates(m -> m.masterData().staged().masterVariant().sku().isIn(asList(SKU1, SKU2, SKU3, SKU_A, SKU_B)));
        final List<Product> products = execute(query).getResults();

        final Function<String, Optional<Product>> findBySku =
                sku -> products.stream().filter(p -> sku.equals(p.getMasterData().getStaged().getMasterVariant().getSku())).findFirst();

        product1 = findBySku.apply(SKU1).orElseGet(() -> createTestProduct(productType, "Schuh", "shoe", "blue", 38, 46, SKU1));
        product2 = findBySku.apply(SKU2).orElseGet(() -> createTestProduct(productType, "Hemd", "shirt", "red", 36, 44, SKU2));
        product3 = findBySku.apply(SKU3).orElseGet(() -> createTestProduct(productType, "Kleider", "dress", "blue", 40, 42, SKU3));
        evilProduct1 = findBySku.apply(SKU_A).orElseGet(() -> createEvilTestProduct(evilProductType, EVIL_CHARACTER_WORD, EVIL_PRODUCT_TYPE_NAME + "foo", SKU_A));
        evilProduct2 = findBySku.apply(SKU_B).orElseGet(() -> createEvilTestProduct(evilProductType, EVIL_PRODUCT_TYPE_NAME + "bar", EVIL_CHARACTER_WORD, SKU_B));
    }

    protected static List<String> resultsToIds(final PagedSearchResult<ProductProjection> result) {
        return toIds(result.getResults());
    }

    protected static PagedSearchResult<ProductProjection> executeSearch(final ProductProjectionSearch search) {
        final List<String> ids = asList(product1.getId(), product2.getId(), product3.getId());
        return execute(search.plusQueryFilters(filter -> filter.id().byAny(ids)));
    }

    protected static PagedSearchResult<ProductProjection> executeEvilSearch(final ProductProjectionSearch search) {
        final List<String> ids = asList(evilProduct1.getId(), evilProduct2.getId());
        return execute(search.plusQueryFilters(filter -> filter.id().byAny(ids)));
    }

    private static ProductType createProductType() {
        final AttributeDefinition colorAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_COLOR, ofEnglishLocale(ATTR_NAME_COLOR), StringType.of()).build();
        final AttributeDefinition sizeAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_SIZE, ofEnglishLocale(ATTR_NAME_SIZE), NumberType.of()).build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "", asList(colorAttrDef, sizeAttrDef));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        return execute(productTypeCreateCommand);
    }

    private static ProductType createEvilProductType() {
        final AttributeDefinition evilAttrDef = AttributeDefinitionBuilder.of(ATTR_NAME_EVIL, ofEnglishLocale(ATTR_NAME_EVIL), StringType.of()).build();
        final ProductTypeDraft evilProductTypeDraft = ProductTypeDraft.of(EVIL_PRODUCT_TYPE_NAME, "", singletonList(evilAttrDef));
        final ProductTypeCreateCommand evilProductTypeCreateCommand = ProductTypeCreateCommand.of(evilProductTypeDraft);
        return execute(evilProductTypeCreateCommand);
    }

    private static Product createTestProduct(final ProductType productType, final String germanName, final String englishName,
                                             final String color, final int size1, final int size2, final String sku) {
        final LocalizedString name = LocalizedString.of(GERMAN, germanName, ENGLISH, englishName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(AttributeDraft.of(ATTR_NAME_SIZE, size1), AttributeDraft.of(ATTR_NAME_COLOR, color))
                .price(Price.of(new BigDecimal("23.45"), EUR))
                .sku(sku)
                .build();
        final ProductVariantDraft variant = ProductVariantDraftBuilder.of()
                .attributes(AttributeDraft.of(ATTR_NAME_SIZE, size2))
                .price(Price.of(new BigDecimal("27.45"), EUR))
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name.slugifiedUnique(), masterVariant)
                .variants(singletonList(variant)).build();
        return execute(ProductCreateCommand.of(productDraft));
    }

    private static Product createEvilTestProduct(final ProductType productType, final String germanName, final String evilValue, final String sku) {
        final LocalizedString name = LocalizedString.of(GERMAN, germanName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(AttributeDraft.of(ATTR_NAME_EVIL, evilValue))
                .sku(sku)
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name.slugifiedUnique(), masterVariant).build();
        return execute(ProductCreateCommand.of(productDraft));
    }
}
