package io.sphere.sdk.search;

import java.util.List;

public interface SortSupport<T> {

    List<SortExpression<T>> sort();
}
