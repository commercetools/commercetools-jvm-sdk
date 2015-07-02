package io.sphere.sdk.queries;

import java.util.List;

interface ReferenceQueryModelLike<T> extends QueryModel<T> {
    QueryPredicate<T> isInIds(List<String> ids);

    StringQueryModel<T> id();

    StringQueryModel<T> typeId();
}
