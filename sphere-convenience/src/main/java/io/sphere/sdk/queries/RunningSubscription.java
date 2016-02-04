package io.sphere.sdk.queries;

import io.sphere.sdk.client.QueueSphereClientDecorator;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Base;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.lang.String.format;

class RunningSubscription<T, C extends QueryDsl<T, C>> extends Base implements SubscriptionsState {
    private static final long MIN_ELEMENTS_PER_QUERY = 20;
    private static final long MAX_ELEMENTS_PER_QUERY = 500;
    private final Function<T, String> idExtractor;
    private final SubscriptionImpl<T, C> tcSubscription;
    private QueryDsl<T, C> seedQuery;
    private SphereClient sphereClient;
    private Subscriber<? super T> subscriber;

    private String lastId;
    private Queue<T> elementsQueue = new LinkedList<>();
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);//kind of mailbox


    RunningSubscription(final QueryDsl<T, C> seedQuery, final Function<T, String> idExtractor, final SphereClient sphereClient, final Subscriber<? super T> subscriber, final SubscriptionImpl<T, C> tcSubscription) {
        this.idExtractor = idExtractor;
        this.tcSubscription = tcSubscription;
        this.seedQuery = seedQuery.withSort(QuerySort.of("id asc")).withFetchTotal(false);
        this.sphereClient = QueueSphereClientDecorator.of(sphereClient, 1, false);
        this.subscriber = subscriber;
    }

    @Override
    public void cancel() {
        tcSubscription.state = new NoOpSubscription();
        executor.shutdownNow();
        executor = null;
        subscriber = null;
        sphereClient = null;
        seedQuery = null;
        lastId = null;
    }

    @Override
    public void request(final long n) {
        executor.execute(() -> {
            final long remainingElements = feedFromCache(n);
            if (remainingElements > 0) {
                fetchNewElements(remainingElements);
            }
        });
    }

    private void fetchNewElements(final long initialRemainingElements) {
        final long bulkSize = bulkSize(initialRemainingElements);
        final Query<T> query = (lastId == null ? seedQuery : seedQuery.withPredicates(listOf(seedQuery.predicates(), QueryPredicate.of(format("id > \"%s\"", lastId))))).withLimit(bulkSize);
        try {
            final List<T> results = sphereClient.execute(query).toCompletableFuture().join().getResults();
            if (results.size() == 0) {
                subscriber.onComplete();
                cancel();
            } else {
                final T t = results.get(results.size() - 1);
                lastId = idExtractor.apply(t);

                final Queue<T> queue = new LinkedList<>(results);
                final long stillmissingCount = feedFromQueue(initialRemainingElements, queue);
                elementsQueue = queue;
                if (stillmissingCount > 0) {
                    fetchNewElements(stillmissingCount);
                }
            }
        } catch (final CompletionException e) {
            cancel();
            subscriber.onError(e);
        }
    }

    private long bulkSize(final long remainingElements) {
        return Math.min(Math.max(MIN_ELEMENTS_PER_QUERY, remainingElements), MAX_ELEMENTS_PER_QUERY);
    }

    private long feedFromCache(final long n) {
        return feedFromQueue(n, elementsQueue);
    }

    private long feedFromQueue(final long n, final Queue<T> queue) {
        long remainingElements = Math.max(n, 0);
        boolean valueFound = true;
        while (valueFound && remainingElements > 0) {
            final T value = queue.poll();
            valueFound = value != null;
            if (valueFound) {
                subscriber.onNext(value);
                remainingElements--;
            }
        }
        return remainingElements;
    }
}
