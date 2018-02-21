package io.sphere.sdk.products.queries;

import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.PagedResult;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryAllIntegrationTest extends QueryAllBase {
    //in production code it would higher #smalltestset
    private static final long PAGE_SIZE = 5;

    @BeforeClass
    public static void clean(){
        CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts(client());
    }

    @Test
    public void useIdPredicateInsteadOfOffset() throws Exception {
        final ProductQuery seedQuery = ProductQuery.of()
                //the original predicate, which queries products for a certain product type
                //the idea works also for no predicate to get all products
                .withPredicates(m -> m.productType().is(productType))
                //important, we sort by id, otherwise id > $lastId would not make sense
                .withSort(m -> m.id().sort().asc())
                .withLimit(PAGE_SIZE)
                .withFetchTotal(false);//saves also resources and time
        final CompletionStage<List<Product>> resultStage = findNext(seedQuery, seedQuery, new LinkedList<>());
        final List<Product> actualProducts = resultStage.toCompletableFuture().join();
        assertThat(actualProducts).hasSize(createdProducts.size());
        //!!! the underlying database has a different algorithm to sort by ID, it is a UUID, which differs from String sorting
        final List<Product> javaSortedActual = actualProducts.stream().sorted(BY_ID_COMPARATOR).collect(toList());
        assertThat(javaSortedActual).isEqualTo(createdProducts);
    }

    private CompletionStage<List<Product>> findNext(final ProductQuery seedQuery, final ProductQuery query, final List<Product> products) {
        final CompletionStage<PagedQueryResult<Product>> pageResult = sphereClient().execute(query);
        return pageResult.thenCompose(page -> {
            final List<Product> results = page.getResults();
            products.addAll(results);
            final boolean isLastQueryPage = results.size() < PAGE_SIZE;
            if (isLastQueryPage) {
                return CompletableFuture.completedFuture(products);
            } else {
                final String lastId = getIdForNextQuery(page);
                return findNext(seedQuery, seedQuery
                        .plusPredicates(m -> m.id().isGreaterThan(lastId)), products);
            }
        });
    }

    /**
     * Gets from a {@link PagedResult} the last ID if the result is not the last page.
     * This does only work correctly if it was sorted by ID in ascending order.
     * @param pagedResult the result to extract the id
     * @return the last ID, if present
     */
    private <T extends Identifiable<T>> String getIdForNextQuery(final PagedResult<T> pagedResult) {
        final List<T> results = pagedResult.getResults();
        final int indexLastElement = results.size() - 1;
        return results.get(indexLastElement).getId();
    }
}
