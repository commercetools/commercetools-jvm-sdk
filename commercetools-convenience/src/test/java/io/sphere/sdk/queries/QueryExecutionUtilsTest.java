package io.sphere.sdk.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereApiConfig;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.stream.LongStream;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryExecutionUtilsTest {
    private static final int PAGE_SIZE = 5;
    private static final Comparator<Category> categoryComparator = Comparator.comparing(c -> c.getSlug().get(Locale.ENGLISH));

    @Test
    public void onEmptyResult() throws Exception {
        withClient(clientWithResults(0), list -> assertThat(list).isSortedAccordingTo(categoryComparator).isEmpty());
    }

    @Test
    public void onOnePageResult() throws Exception {
        withClient(clientWithResults(3), list -> assertThat(list).isSortedAccordingTo(categoryComparator).hasSize(3));
    }

    @Test
    public void onMultiplePagesResult() throws Exception {
        withClient(clientWithResults(16), list -> assertThat(list).isSortedAccordingTo(categoryComparator).hasSize(16));
    }

    @Test
    public void onWrongTotalResult() throws Exception {
        withClient(clientWithWrongResults(16), list -> assertThat(list).isSortedAccordingTo(categoryComparator).hasSize(16));
    }

    @Test
    public void onUnsortedResponses() throws Exception {
        withClient(clientWithDelays(16), list -> assertThat(list).isSortedAccordingTo(categoryComparator).hasSize(16));
    }

    private void withClient(final SphereClient client, final Consumer<List<Category>> test) {
        final List<List<Category>> elementPages = QueryExecutionUtils.queryAll(client, CategoryQuery.of(), (categories -> categories), PAGE_SIZE)
                .toCompletableFuture().join();
        final List<Category> elements = elementPages.stream()
                                                    .flatMap(List::stream)
                                                    .collect(toList());
        test.accept(elements);
    }

    private SphereClient clientWithResults(final int totalResults) {
        return client(totalResults, 0, false);
    }

    private SphereClient clientWithWrongResults(final int totalResults) {
        return client(totalResults, 10, false);
    }

    private SphereClient clientWithDelays(final int totalResults) {
        return client(totalResults, 0, true);
    }

    private SphereClient client(final int totalResults, final int deviation, final boolean withDelays) {
        return new SphereClient() {

            @SuppressWarnings("unchecked")
            @Override
            public <T> CompletionStage<T> execute(final SphereRequest<T> request) {
                final CategoryQuery query = (CategoryQuery) request;
                final int offset = query.offset().intValue();
                return CompletableFuture.supplyAsync(() -> {
                    if (withDelays) {
                        try {
                            Thread.sleep(100 / (offset + 1));
                        } catch (InterruptedException e) {
                            throw new CompletionException(e);
                        }
                    }
                    final T pagedQueryResult = (T) generatePagedQueryResult(offset);
                    return pagedQueryResult;
                });
            }

            @Override
            public void close() {

            }

            @SuppressWarnings("unchecked")
            private <T> PagedQueryResult<T> generatePagedQueryResult(final long offset) {
                final long total = totalResults + deviation;
                final long count = min(PAGE_SIZE, max(totalResults - offset, 0));
                final List<T> results = (List<T>) generateSortedResultList(offset, count);
                return PagedQueryResult.of(offset, total, results);
            }

            private List<Category> generateSortedResultList(final long offset, final long count) {
                return LongStream.range(offset, offset + count)
                        .mapToObj(i -> SphereJsonUtils.readObject(String.format("{ \"slug\" : {\"en\": \"category-%04d\"} }", i), Category.class))
                        .collect(toList());
            }

            @Override
            public SphereApiConfig getConfig() {
                return null;
            }
        };
    }
}