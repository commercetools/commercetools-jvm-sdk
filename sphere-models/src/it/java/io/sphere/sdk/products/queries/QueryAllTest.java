package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.PagedResult;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryAllTest extends QueryAllBase {
    //yes, this is a small page size, but since this is tested,
    //we create not million products for test execution speed reasons
    //in production code it would higher
    private static final int PAGE_SIZE = 5;

    @Test
    public void useIdPredicateInsteadOfOffset() throws Exception {
        final ProductQuery seedQuery = ProductQuery.of()
                //the original predicate, which queries products for a certain product type
                //the idea works also for no predicate to get all products
                .withPredicate(m -> m.productType().is(productType))
                //important, we sort by id, otherwise id > $lastId would not make sense
                .withSort(m -> m.id().sort().asc())
                .withLimit(PAGE_SIZE)
                .withFetchTotal(false);
        final CompletionStage<List<Product>> resultStage = sphereClient().execute(seedQuery)
                .thenCompose(r -> queryStep(seedQuery, new LinkedList<>(), r));
        final List<Product> actualProducts = resultStage.toCompletableFuture().join();
        assertThat(actualProducts).hasSize(createdProducts.size());
        //!!! the underlying database has a different algorithm to sort by ID, it is a UUID, which differs from String sorting
        final List<Product> javaSortedActual = actualProducts.stream().sorted(BY_ID_COMPARATOR).collect(toList());
        assertThat(javaSortedActual).isEqualTo(createdProducts);
    }

    public CompletionStage<List<Product>> queryStep(final ProductQuery seedQuery, final List<Product> accumulator,
                                                    final PagedQueryResult<Product> pagedQueryResult) {
        accumulator.addAll(pagedQueryResult.getResults());

        final Optional<String> lastProductIdOptional = getIdForNextQuery(pagedQueryResult);
        return lastProductIdOptional.map(lastId -> {
            //here is the important part, we don't use offset, but query id > $lastId
            //since we have a predicate for a product type, we connect them via and
            //if no predicate is given, use {@code withPredicate(m -> m.id().isGreaterThan(lastId))};
            final ProductQuery sphereRequest = seedQuery
                    .withPredicate(m -> seedQuery.predicate().get().and(m.id().isGreaterThan(lastId)));
            final CompletionStage<PagedQueryResult<Product>> execute = sphereClient().execute(sphereRequest);
            return execute.thenCompose(r -> queryStep(seedQuery, accumulator, r));
        })
        .orElseGet(() -> CompletableFuture.completedFuture(accumulator));
    }

    /**
     * Gets from a {@link PagedResult} the last ID if the result is not the last page.
     * This does only work correctly if it was sorted by ID in ascending order.
     * @param pagedResult the result to extract the id
     * @return the last ID, if present
     */
    private <T extends Identifiable<T>> Optional<String> getIdForNextQuery(final PagedResult<T> pagedResult) {
        final List<T> results = pagedResult.getResults();
        return Optional.of(results.size() - 1)
                .filter(index -> index >= 0 && !pagedResult.isLast())
                .map(index -> results.get(index))
                .map(p -> p.getId());
    }
}
