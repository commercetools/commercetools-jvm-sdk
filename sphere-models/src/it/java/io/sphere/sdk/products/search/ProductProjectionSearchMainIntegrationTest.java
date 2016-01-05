package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.PagedSearchResult;
import org.assertj.core.api.Condition;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchMainIntegrationTest extends ProductProjectionSearchIntegrationTest {

    public static final NamedAttributeAccess<String> COLOR_ATTRIBUTE_ACCESS = AttributeAccess.ofString().ofName(ATTR_NAME_COLOR);
    public static final NamedAttributeAccess<Long> SIZE_ATTRIBUTE_ACCESS = AttributeAccess.ofLong().ofName(ATTR_NAME_SIZE);
    public static final ProductProjectionFilterSearchModel FILTER_MODEL = ProductProjectionSearchModel.of().filter();

    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withText(ENGLISH, "shoe");
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId());
    }

    @Test
    public void resultsArePaginated() throws Exception {
        final PagedSearchResult<ProductProjection> result = executeSearch(ProductProjectionSearch.ofStaged()
                .plusQueryFilters(filter -> filter.allVariants().attribute().ofString(ATTR_NAME_COLOR).byAny(asList("blue", "red")))
                .withSort(sort -> sort.name().locale(ENGLISH).byDesc())
                .withOffset(1L)
                .withLimit(1L));
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
    }

    @Test
    public void paginationExample() {
        final long offset = 10;
        final long limit = 25;
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withOffset(offset)
                .withLimit(limit);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final long remainingProducts = max(result.getTotal() - offset, 0);
        final long expectedProducts = min(limit, remainingProducts);
        assertThat(result.size()).as("size").isEqualTo(expectedProducts);
        assertThat(result.getOffset()).as("offset").isEqualTo(offset);
    }

    @Test
    public void allowsExpansion() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withLimit(1L);
        assertThat(executeSearch(search).getResults().get(0).getProductType().getObj()).isNull();
        final PagedSearchResult<ProductProjection> result = executeSearch(search.withExpansionPaths(model -> model.productType()));
        assertThat(result.getResults().get(0).getProductType().getObj()).isEqualTo(productType);
    }

    @Test
    public void fuzzySearch() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withText(ENGLISH, "short")
                .withQueryFilters(filter -> filter.productType().id().by(productType.getId()));
        assertThat(client().executeBlocking(search).getResults()).matches(containsIdentifiable(product2).negate(), "not included");
        assertThat(client().executeBlocking(search.withFuzzy(true)).getResults()).matches(containsIdentifiable(product2), "included");
    }

    @Test
    public void findMatchingVariantByQueryFilter() throws Exception {
        testBlueFilter(filters -> ProductProjectionSearch.ofStaged().withQueryFilters(filters));
    }

    @Test
    public void findMatchingVariantByResultFilter() throws Exception {
        testBlueFilter(filters -> ProductProjectionSearch.ofStaged().withResultFilters(filters));
    }

    @Ignore // HTTP API still not having sort associated to matching variant flag
    @Test
    public void findMatchingVariantBySort() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(product -> product.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).byAscWithMax());
        assertThat(executeSearch(search).getResults()).are(productWithMatchingVariantsHavingMaxSize());
    }

    @Ignore // HTTP API doesn't mark the correct variant as true when the searched info is inside the variant
    @Test
    public void findMatchingVariantByFulltextSearchOnVariant() throws Exception {
        testBlueFilter(filters -> ProductProjectionSearch.ofStaged().withText(ENGLISH, "blue"));
    }

    @Ignore // HTTP API still not having at least one matching variant flag true
    @Test
    public void findMatchingVariantByFulltextSearch() throws Exception {
        final Condition<ProductVariant> allMatchingVariants = new Condition<>(variant -> variant.isMatchingVariant(), "all are matching variants");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withText(ENGLISH, "shoe");
        assertThat(executeSearch(search).getResults())
                .flatExtracting(p -> p.getAllVariants())
                .are(allMatchingVariants);
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
        final ProductProjectionSearch search = createSearch.apply(FILTER_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).by("blue"));
        final List<ProductProjection> results = executeSearch(search).getResults();

        final Predicate<ProductVariant> isBlue = variant -> variant.findAttribute(COLOR_ATTRIBUTE_ACCESS)
                .map(color -> color.equals("blue"))
                .orElse(false);
        final Condition<ProductVariant> blueVariant = new Condition<>(isBlue, "variant is blue");

        assertThat(results)
                .flatExtracting(result -> result.getAllVariants())
                .areAtLeastOne(notMatching(blueVariant))
                .areAtLeastOne(blueVariant);
        final List<ProductVariant> allMatchingVariants = results.stream()
                .flatMap(r -> r.findMatchingVariants().stream())
                .collect(toList());
        assertThat(allMatchingVariants).are(blueVariant);
        assertThat(results)
                .extracting(r -> r.findFirstMatchingVariant().get())
                .containsOnlyElementsOf(allMatchingVariants);
    }

    private <T> Condition<T> notMatching(final Condition<T> matchingCondition) {
        return new Condition<>(obj -> !matchingCondition.matches(obj), "not matching " + matchingCondition.description());
    }

    private <T> Predicate<List<? extends Identifiable<T>>> containsIdentifiable(final Identifiable<T> identifiable) {
        return list -> list.stream().anyMatch(element -> element.getId().equals(identifiable.getId()));
    }

}
