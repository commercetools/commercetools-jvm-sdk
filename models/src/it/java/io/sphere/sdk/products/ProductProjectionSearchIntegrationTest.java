package io.sphere.sdk.products;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.search.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class ProductProjectionSearchIntegrationTest extends IntegrationTest {

    public static final SearchSort<ProductProjection> SORT_CREATED_AT_DESC = SearchSort.of("createdAt desc");
    private static Product testProduct1;
    private static Product testProduct2;
    private static ProductType productType;
    public static final String TEST_CLASS_NAME = ProductProjectionSearchIntegrationTest.class.getSimpleName();
    public static final String COLOR = ("Color" + TEST_CLASS_NAME).substring(0, Math.min(25, TEST_CLASS_NAME.length()));
    public static final String SIZE = ("Size" + TEST_CLASS_NAME).substring(0, Math.min(25, TEST_CLASS_NAME.length()));
    public static final String COLOR_ATTRIBUTE_KEY = "variants.attributes." + COLOR;
    public static final String SIZE_ATTRIBUTE_KEY = "variants.attributes." + SIZE;
    public static final String PRICE_ATTRIBUTE_KEY = "variants.price.centAmount";

    @BeforeClass
    public static void setupProducts() {
        removeProducts();
        final AttributeDefinition colorAttributeDefinition = AttributeDefinitionBuilder
                .of(COLOR, LocalizedStrings.ofEnglishLocale(COLOR), new TextType()).build();
        final AttributeDefinition sizeAttributeDefinition = AttributeDefinitionBuilder
                .of(SIZE, LocalizedStrings.ofEnglishLocale(SIZE), new TextType()).build();

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(TEST_CLASS_NAME, "", asList(colorAttributeDefinition, sizeAttributeDefinition));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        productType = execute(productTypeCreateCommand);
        testProduct1 = createTestProduct(productType, "Schuh", "shoe", "red", "M");
        testProduct2 = createTestProduct(productType, "Hemd", "shirt", "blue", "XL");
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).withSort(SORT_CREATED_AT_DESC);
        execute(search, res -> {
            final List<String> ids = toIds(res.getResults());
            return ids.contains(testProduct1.getId()) && ids.contains(testProduct2.getId());
        });
        try {
            Thread.sleep(500); // Wait for elasticsearch synchronization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Product createTestProduct(ProductType productType, String germanName, String englishName, String color, final String size) {
        final LocalizedStrings name = LocalizedStrings.of(GERMAN, germanName, ENGLISH, englishName);
        final ProductVariantDraft variant = ProductVariantDraftBuilder.of()
                .attributes(Attribute.of(COLOR, color), Attribute.of(SIZE, size))
                .price(Price.of(new BigDecimal("23.45"), EUR))
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name, variant).build();
        return execute(ProductCreateCommand.of(productDraft));
    }

    @AfterClass
    public static void removeProducts() {
        List<ProductType> productTypes = execute(ProductTypeQuery.of().byName(TEST_CLASS_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final List<ProductProjection> products = execute(ProductProjectionQuery.of(STAGED)
                    .withPredicate(ProductProjectionQuery.model().productType().isAnyOf(productTypes))).getResults();
            products.forEach(p -> execute(ProductDeleteCommand.of(p.toProductVersioned())));
            productTypes.forEach(p -> execute(ProductTypeDeleteCommand.of(p)));
        }
        testProduct1 = null;
        testProduct2 = null;
        productType = null;
    }

    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final Search<ProductProjection> search = ProductProjectionSearch.of(STAGED).withText(ENGLISH, "shoe");
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        //end example parsing here
        assertThat(toIds(pagedSearchResult.getResults())).containsExactly(testProduct1.getId());
    }

    protected static <T> T execute(final SphereRequest<T> clientRequest, final Predicate<T> isOk) {
        return execute(clientRequest, 9, isOk);
    }

    protected static <T> T execute(final SphereRequest<T> clientRequest, final int attemptsLeft, final Predicate<T> isOk) {
        if (attemptsLeft < 1) {
            fail("Could not satisfy the request.");
        }

        T result = execute(clientRequest);
        if (isOk.test(result)) {
            return result;
        } else {
            LoggerFactory.getLogger(ProductProjectionSearchIntegrationTest.class).info("attempts left " + (attemptsLeft - 1));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return execute(clientRequest, attemptsLeft - 1, isOk);
        }
    }

    private void paginationExample() {
        final SearchSort<ProductProjection> sort = getASortExpressionSomeHow();
        final Search<ProductProjection> search = ProductProjectionSearch.of(STAGED)
                .withSort(sort)
                .withOffset(50)
                .withLimit(25);
    }

    private SearchSort<ProductProjection> getASortExpressionSomeHow() {
        return null;
    }
}
