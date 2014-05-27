package io.sphere.sdk.queries;

import io.sphere.sdk.client.Query;

/**
 * Query that returns only 0 to 1 results.
 * Example: Querying categories by slug.
 */
public interface AtMostOneResultQuery<I, R> extends Query<I, R> {
}
