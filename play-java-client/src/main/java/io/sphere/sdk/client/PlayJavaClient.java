package io.sphere.sdk.client;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.AtMostOneResultQuery;
import play.libs.F;

public interface PlayJavaClient {
    <I, R> F.Promise<Optional<I>> execute(final Fetch<I, R> fetch);

    <I, R> F.Promise<PagedQueryResult<I>> execute(final Query<I, R> query);

    <I, R> F.Promise<Optional<I>> execute(final AtMostOneResultQuery<I, R> query);

    <T, V> F.Promise<T> execute(final Command<T, V> command);

    void close();
}
