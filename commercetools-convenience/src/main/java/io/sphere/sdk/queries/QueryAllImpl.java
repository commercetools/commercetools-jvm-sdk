package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

final class QueryAllImpl<T, C extends QueryDsl<T, C>> {
    private final QueryDsl<T, C> baseQuery;
    private final long pageSize;

    private QueryAllImpl(final QueryDsl<T, C> baseQuery, final long pageSize) {
        this.baseQuery = !baseQuery.sort().isEmpty() ? baseQuery : baseQuery.withSort(QuerySort.of("id asc"));
        this.pageSize = pageSize;
    }

    /**
     * Execute the {@link #baseQuery} on the {@code client} and return completion stage with all pages results
     * aggregated to list.
     * @param client the CTP client that the query is run on.
     * @return {@link CompletionStage} with the {@link #baseQuery} results aggregated from all pages to list.
     */
    public CompletionStage<List<T>> run(final SphereClient client) {
        return run(client, entry -> entry);
    }

    /**
     * Given a {@code resultsMapper} {@link Function}, this method applies this callback on each item of the results
     * from {@link this} instance's {@link #baseQuery} and returns a future containing a list containing the results
     * of this {@code resultsMapper} on each item.
     *
     * @param client        the CTP client that the query is run on.
     * @param resultsMapper the callback that gets called on each item of results.
     * @param <S>           the type of the result mapped values of the callback on each requested {@code T} item.
     * @return a future containing a list of results of the {@code resultsMapper} on each item.
     */
    @Nonnull
    <S> CompletionStage<List<S>> run(final SphereClient client, final Function<T, S> resultsMapper) {
        return queryPage(client, 0)
                .thenApply(result -> concatPagedResultToList(result, client, resultsMapper));
    }

    /**
     * Given a {@link Consumer resultsConsumer}, this method applies this consumer on each item of the results from
     * {@link this} instance's {@link #baseQuery}.
     *
     * @param client          the CTP client that the query is run on.
     * @param resultsConsumer the consumer that gets called on each item of results.
     * @return an empty future which when is completed the {@code resultsConsumer} is executed on every item from the
     * {@link #baseQuery}
     */
    @Nonnull
    CompletionStage<Void> run(final SphereClient client, final Consumer<T> resultsConsumer) {
        return queryPage(client, 0)
                .thenAccept(result -> consumePagedResult(result, client, resultsConsumer));
    }

    private <S> List<S> concatPagedResultToList(@Nonnull PagedQueryResult<T> result,
                                                @Nonnull SphereClient client,
                                                @Nonnull Function<T, S> resultsMapper) {
        final Stream<S> firstStream = result.getResults().stream().map(resultsMapper);

        Stream<S> nextStream = queryNextPages(client, result.getTotal(), resultsMapper)
                .flatMap(stage -> stage.toCompletableFuture().join());

        return concat(firstStream, nextStream)
                .collect(toList());
    }

    private void consumePagedResult(@Nonnull PagedQueryResult<T> result,
                                    @Nonnull SphereClient client,
                                    @Nonnull Consumer<T> resultsConsumer) {
        result.getResults().forEach(resultsConsumer);
        queryNextPages(client, result.getTotal(), resultsConsumer)
                .forEach(stageStreamS -> stageStreamS.toCompletableFuture().join());
    }

    /**
     * Given the {@code resultsMapper} function, this method first calculates the total number of pages
     * resulting from the query, then it applies this {@code resultsMapper} on each item of the results
     * from {@link this} instance's {@link #baseQuery}.
     *
     * @param client        the CTP client that the query is run on.
     * @param totalElements the total number of elements resulting from the query.
     * @param resultsMapper the callback to apply on each item of results.
     * @return a stream of stages containing stream of mapped results using {@code resultsMapper}
     */
    @Nonnull
    private <S> Stream<CompletionStage<Stream<S>>> queryNextPages(final SphereClient client, final long totalElements,
                                                          final Function<T, S> resultsMapper) {
        final long totalPages = getTotalNumberOfPages(totalElements);
        return LongStream.range(1, totalPages)
                         .mapToObj(page -> queryPage(client, page)
                             .thenApply(results -> results.getResults().stream().map(resultsMapper)));
    }

    /**
     * Given a {@link Consumer resultsConsumer}, this method first calculates the total number of pages
     * resulting from the query, then it accepts the results from {@link this} instance's {@link #baseQuery} on this
     * consumer and returns a stream of stages with accepting all the result items by {@code resultsConsumer}.
     *
     * @param client        the CTP client that the query is run on.
     * @param totalElements the total number of elements resulting from the query.
     * @param resultsConsumer      the consumer to apply on each page of results.
     * @return a list of futures of applying the consumer on each page of results.
     */
    @Nonnull
    private Stream<CompletionStage<Void>> queryNextPages(final SphereClient client, final long totalElements,
                                                         final Consumer<T> resultsConsumer) {
        final long totalPages = getTotalNumberOfPages(totalElements);
        return LongStream.range(1, totalPages)
                .mapToObj(page -> queryPage(client, page)
                        .thenAccept(results -> results.getResults().forEach(resultsConsumer)));
    }

    /**
     * Given a total number of elements {@code totalElements} this method calculates the number of pages needed that
     * would be needed to cover all the elements with set {@link #pageSize} of {@link this} instance.
     *
     * @param totalElements number of elements to get the number of pages for.
     * @return the total number of pages.
     */
    long getTotalNumberOfPages(final long totalElements) {
        return (totalElements - 1 + pageSize) / pageSize;
    }

    /**
     * Gets the results of {@link this} instance's query for a specific page with {@code pageNumber}.
     *
     * @param client     the CTP client that the query is run on.
     * @param pageNumber the page number to get the results for.
     * @return a future containing the results of the requested page of applying the query.
     */
    @Nonnull
    private CompletionStage<PagedQueryResult<T>> queryPage(final SphereClient client, final long pageNumber) {
        final QueryDsl<T, C> query = baseQuery
                .withOffset(pageNumber * pageSize)
                .withLimit(pageSize);
        return client.execute(query);
    }

    @Nonnull
    static <T, C extends QueryDsl<T, C>> QueryAllImpl<T, C> of(@Nonnull final QueryDsl<T, C> baseQuery, final int pageSize) {
        return new QueryAllImpl<>(baseQuery, pageSize);
    }
}
