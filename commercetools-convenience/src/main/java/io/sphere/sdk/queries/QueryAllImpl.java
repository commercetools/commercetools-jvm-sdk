package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

final class QueryAllImpl<T, C extends QueryDsl<T, C>> {
    private final QueryDsl<T, C> baseQuery;
    private final long pageSize;

    private QueryAllImpl(final QueryDsl<T, C> baseQuery, final long pageSize) {
        this.baseQuery = !baseQuery.sort().isEmpty() ? baseQuery : baseQuery.withSort(QuerySort.of("id asc"));
        this.pageSize = pageSize;
    }

    public CompletionStage<List<T>> run(final SphereClient client) {
        return queryPage(client, 0).thenCompose(result -> {
            final List<CompletableFuture<List<T>>> futureResults = new ArrayList<>();
            futureResults.add(completedFuture(result.getResults()));
            futureResults.addAll(queryNextPages(client, result.getTotal()));
            return transformListOfFuturesToFutureOfLists(futureResults);
        });
    }

    private List<CompletableFuture<List<T>>> queryNextPages(final SphereClient client, final long totalElements) {
        final long totalPages = totalElements / pageSize;
        return LongStream.rangeClosed(1, totalPages)
                .mapToObj(page -> queryPage(client, page)
                        .thenApply(PagedQueryResult::getResults)
                        .toCompletableFuture())
                .collect(toList());
    }

    private CompletionStage<PagedQueryResult<T>> queryPage(final SphereClient client, final long pageNumber) {
        final QueryDsl<T, C> query = baseQuery
                .withOffset(pageNumber * pageSize)
                .withLimit(pageSize);
        return client.execute(query);
    }

    @SuppressWarnings("rawtypes")
    private CompletableFuture<List<T>> transformListOfFuturesToFutureOfLists(final List<CompletableFuture<List<T>>> futures) {
        final CompletableFuture[] futuresAsArray = futures.toArray(new CompletableFuture[futures.size()]);
        return CompletableFuture.allOf(futuresAsArray)
                .thenApply(x -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(Collection::stream)
                        .collect(Collectors.<T>toList()));
    }

    static <T, C extends QueryDsl<T, C>> QueryAllImpl<T, C> of(final QueryDsl<T, C> baseQuery, final int pageSize) {
        return new QueryAllImpl<>(baseQuery, pageSize);
    }
}
