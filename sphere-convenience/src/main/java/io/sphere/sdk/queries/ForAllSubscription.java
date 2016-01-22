package io.sphere.sdk.queries;

import io.sphere.sdk.client.QueueSphereClientDecorator;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.lang.String.format;

final class ForAllSubscription<T extends Identifiable<T>, C extends QueryDsl<T, C>> extends Base implements Subscription {
    private static final long ELEMENTS_PER_QUERY = 20;
    private QueryDsl<T, C> seedQuery;
    private SphereClient sphereClient;
    private Subscriber<? super T> subscriber;

    private String lastId;
    private Queue<Long> queue = new LinkedList<>();
    private Queue<T> elementsQueue = new LinkedList<>();
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);//kind of mailbox

    ForAllSubscription(final QueryDsl<T, C> seedQuery, final SphereClient sphereClient, final Subscriber<? super T> subscriber) {
        this.seedQuery = seedQuery.withSort(QuerySort.of("id asc")).withFetchTotal(false).withLimit(ELEMENTS_PER_QUERY);
        this.sphereClient = QueueSphereClientDecorator.of(sphereClient, 1, false);
        this.subscriber = subscriber;
    }

    @Override
    public void cancel() {
        if (subscriptionIsCanceled()) {
            noOp();
        } else {
            executor.shutdownNow();
            executor = null;
            subscriber = null;
            sphereClient.close();
            sphereClient = null;
            seedQuery = null;
            lastId = null;
        }
    }

    @Override
    public void request(final long n) {
        executor.execute(() -> {
            long remainingElements = n;
            boolean valueFound = true;
            while (valueFound && remainingElements > 0) {
                T value = elementsQueue.poll();
                valueFound = value != null;
                if (valueFound) {
                    subscriber.onNext(value);
                    remainingElements--;
                }
            }
            if (remainingElements > 0) {
                fetchNewElements();
                queue.add(remainingElements);
            }
        });
    }

    private void fetchNewElements() {
        final QueryPredicate<T> idIsGreaterThanLastIdPredicate = QueryPredicate.of(format("id > \"%s\"", lastId));
        final Query<T> query = lastId == null ? seedQuery : seedQuery.withPredicates(listOf(seedQuery.predicates(), idIsGreaterThanLastIdPredicate));
        sphereClient.execute(query).whenComplete((r, e) -> {
            executor.execute(() -> {
                final boolean success = e == null;
                if (success) {
                    final List<T> results = r.getResults();
                    if (results.size() == 0) {
                        executor.execute(() -> {
                            subscriber.onComplete();
                            cancel();
                        });
                    } else {
                        executor.execute(() -> {
                            final T t = results.get(results.size() - 1);
                            lastId = t.getId();
                            elementsQueue.addAll(results);
                            queue.forEach(n -> request(n));
                            queue.clear();
                        });
                    }
                } else {
                    executor.execute(() -> {
                        subscriber.onError(e);
                        cancel();
                    });
                }
            });
        });
    }

    private boolean subscriptionIsCanceled() {
        return subscriber == null;
    }

    private void noOp() {

    }
}
