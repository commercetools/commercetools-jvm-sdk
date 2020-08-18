package io.sphere.sdk.products.search;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.selection.LocaleSelection;
import io.sphere.sdk.selection.StoreSelection;
import io.sphere.sdk.stores.StoreFixtures;
import org.assertj.core.api.Condition;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductProjectionSearchMainIntegrationTest extends ProductProjectionSearchIntegrationTest {

    public static final NamedAttributeAccess<String> COLOR_ATTRIBUTE_ACCESS = AttributeAccess.ofString().ofName(ATTR_NAME_COLOR);
    public static final NamedAttributeAccess<Long> SIZE_ATTRIBUTE_ACCESS = AttributeAccess.ofLong().ofName(ATTR_NAME_SIZE);
    public static final ProductProjectionFilterSearchModel PRODUCT_MODEL = ProductProjectionSearchModel.of().filter();

    @Test
    public void selectProductByLocaleProjectionInProductProjectionSearch() {
        final String localeProjection = "en-EN";
        ProductFixtures.withProduct(client(), product -> {
            final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                    .withQueryFilters(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjection));
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=en-EN");
        });
    }

    @Test
    public void selectProductByListOfLocaleProjectionsInProductProjectionSearch() {
        final List<String> localeProjection = Arrays.asList("en-EN", "it-IT");
        ProductFixtures.withProduct(client(), product -> {
            final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                    .withQueryFilters(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjection));
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=en-EN");
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=it-IT");
        });
    }
    @Test
    public void selectProductByTwoLocaleProjectionsInProductProjectionSearch() {
        final String localeProjectionEN = "en-EN";
        final String localeProjectionDE = "de-DE";
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                    .withQueryFilters(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjectionDE))
                    .plusLocaleSelection(LocaleSelection.of(localeProjectionEN));
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=en-EN");
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=de-DE");
        });
    }

    @Test
    public void selectProductByAListOfLocaleProjectionsInProductProjectionSearch() {
        final String localeProjectionEN = "en-EN";
        final List<String> localeProjectionList = asList("de-DE");
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                    .withQueryFilters(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjectionEN))
                    .plusLocaleSelection(LocaleSelection.of(localeProjectionList));
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=en-EN");
            assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("localeProjection=de-DE");
        });
    }

    @Test
    public void selectProductByStoreProjectionInProductProjectionSearch() {
        BlockingSphereClient client = client();
        StoreFixtures.withStore(client, store -> {
            ProductFixtures.withProduct(client, product -> {
                final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                        .withQueryFilters(m -> m.id().is(product.getId()))
                        .withStoreSelection(StoreSelection.of(store.getKey()));
                assertThat(searchRequest.httpRequestIntent().getBody().toString()).contains("storeProjection=" + store.getKey());
            });
        });
    }

    @Ignore
    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withText(ENGLISH, "shoe");
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void resultsArePaginated() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_COLOR).isIn(asList("blue", "red")))
                .withSort(sort -> sort.name().locale(ENGLISH).desc())
                .withOffset(1L)
                .withLimit(1L);
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product2.getId()));
    }

    @Ignore
    @Test
    public void paginationExample() {
        final long offset = 10;
        final long limit = 25;
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withOffset(offset)
                .withLimit(limit);
        testResult(search, result -> {
            final long remainingProducts = max(result.getTotal() - offset, 0);
            final long expectedProducts = min(limit, remainingProducts);
            assertThat(result.getCount()).as("size").isEqualTo(expectedProducts);
            assertThat(result.getOffset()).as("offset").isEqualTo(offset);
        });
    }

    @Ignore
    @Test
    public void allowsExpansion() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withLimit(1L);
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results).isNotEmpty();
            assertThat(results.get(0).getProductType().getObj()).isNull();
        });
        testResult(search.withExpansionPaths(product -> {
            return product.productType();
        }), result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results).isNotEmpty();
            assertThat(results.get(0).getProductType().getObj()).isEqualTo(productType);
        });
    }

    @Ignore
    @Test
    public void fuzzySearch() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withText(ENGLISH, "short")
                .withQueryFilters(productModel -> productModel.productType().id().is(productType.getId()));
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () ->
                assertThat(client().executeBlocking(search).getResults()).matches(containsIdentifiable(product2).negate(), "not included"));
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () ->
                assertThat(client().executeBlocking(search.withFuzzy(true)).getResults()).matches(containsIdentifiable(product2), "included"));
    }

    @Ignore
    @Test
    public void findMatchingVariantByQueryFilter() throws Exception {
        testBlueFilter(filters -> ProductProjectionSearch.ofStaged().withMarkingMatchingVariants(true).withQueryFilters(filters));
    }

    @Ignore
    @Test
    public void findMatchingVariantByResultFilter() throws Exception {
        testBlueFilter(filters -> ProductProjectionSearch.ofStaged().withMarkingMatchingVariants(true).withResultFilters(filters));
    }

    @Ignore // HTTP API still not having sort associated to matching variant flag
    @Test
    public void findMatchingVariantBySort() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).ascWithMaxValue());
        testResult(search, result ->
                assertThat(result.getResults()).are(productWithMatchingVariantsHavingMaxSize()));
    }

    @Ignore // HTTP API doesn't mark the correct variant as true when the searched info is inside the variant
    @Test
    public void findMatchingVariantByFulltextSearchOnVariant() throws Exception {
        testBlueFilter(filters -> ProductProjectionSearch.ofStaged().withText(ENGLISH, "blue"));
    }

    @Ignore
    @Test
    public void findMatchingVariantByFulltextSearch() throws Exception {
        final Condition<ProductVariant> allMatchingVariants = new Condition<>(variant -> variant.isMatchingVariant(), "all are matching variants");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withMarkingMatchingVariants(true).withText(ENGLISH, "shoe");
        assertThat(executeSearch(search).getResults())
                .flatExtracting(p -> p.getAllVariants())
                .are(allMatchingVariants);
    }

    @Ignore
    @Test
    public void slugExistsExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().bySlug(ENGLISH, SLUG1);
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results.size()).isEqualTo(1);
            assertThat(results.get(0).getSlug().get(ENGLISH)).isEqualTo(SLUG1);
        });
    }

    @Ignore
    @Test
    public void slugDontExistsExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().bySlug(ENGLISH, "blafasel");
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results).isEmpty();
        });
    }

    @Ignore
    @Test
    public void skuExistsExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().bySku(SKU1);
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results.size()).isEqualTo(1);
            assertThat(results.get(0).getAllVariants().stream().anyMatch(variant -> SKU1.equals(variant.getSku()))).isEqualTo(true);

        });
    }

    @Ignore
    @Test
    public void skuDontExistsExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().bySku("thisSKUshouldn't exist");
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results).isEmpty();
        });
    }

    @Ignore
    @Test
    public void multipleSKUsExistsExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().bySku(asList(SKU1, SKU2, SKU3, "thisSKUshouldn't exist"));
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            assertThat(results).hasSize(3);
        });
    }


    private Condition<ProductProjection> productWithMatchingVariantsHavingMaxSize() {
        final Predicate<ProductProjection> matchingVariantsHaveMaxSize = product -> {
            final long productMaximumSize = product.getAllVariants().stream()
                    .mapToLong(variant -> variant.findAttribute(SIZE_ATTRIBUTE_ACCESS).orElse(0L))
                    .max()
                    .getAsLong();
            return product.findMatchingVariants().stream()
                    .mapToLong(variant -> variant.findAttribute(SIZE_ATTRIBUTE_ACCESS).get())
                    .allMatch(size -> size == productMaximumSize);
        };
        return new Condition<>(matchingVariantsHaveMaxSize, "all matching variants have maximum size");
    }

    private void testBlueFilter(final Function<List<FilterExpression<ProductProjection>>, ProductProjectionSearch> createSearch) {
        final ProductProjectionSearch search = createSearch.apply(PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).is("blue"));
        testResult(search, result -> {
            final List<ProductProjection> results = result.getResults();
            final Predicate<ProductVariant> isBlue = variant -> variant.findAttribute(COLOR_ATTRIBUTE_ACCESS)
                    .map(color -> color.equals("blue"))
                    .orElse(false);
            final Condition<ProductVariant> blueVariant = new Condition<>(isBlue, "variant is blue");

            assertThat(results)
                    .flatExtracting(ProductProjection::getAllVariants)
                    .areAtLeastOne(notMatching(blueVariant))
                    .areAtLeastOne(blueVariant);
            final List<ProductVariant> allMatchingVariants = results.stream()
                    .flatMap(r -> r.findMatchingVariants().stream())
                    .collect(toList());
            assertThat(allMatchingVariants).are(blueVariant);
            assertThat(results)
                    .extracting(r -> r.findFirstMatchingVariant().get())
                    .containsOnlyElementsOf(allMatchingVariants);

        });
    }

    private <T> Condition<T> notMatching(final Condition<T> matchingCondition) {
        return new Condition<>(obj -> !matchingCondition.matches(obj), "not matching " + matchingCondition.description());
    }

    private <T> Predicate<List<? extends Identifiable<T>>> containsIdentifiable(final Identifiable<T> identifiable) {
        return list -> list.stream().anyMatch(element -> element.getId().equals(identifiable.getId()));
    }

    private static void testResultIds(final ProductProjectionSearch search, final Consumer<List<String>> test) {
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> {
            final PagedSearchResult<ProductProjection> result = executeSearch(search);
            test.accept(resultsToIds(result));
        });
    }

    private static void testResult(final ProductProjectionSearch search, final Consumer<PagedSearchResult<ProductProjection>> test) {
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> test.accept(executeSearch(search)));
    }
}
