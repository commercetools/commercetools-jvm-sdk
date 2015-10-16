package io.sphere.sdk.search;

import java.util.List;

public interface SortDsl<T, C> extends SortSupport<T> {

    /**
     * Returns a new object with the new sort expressions.
     * @param sortExpressions the new sort expression list
     * @return a new object with sort {@code sortExpressions}
     */
    C withSort(final List<SortExpression<T>> sortExpressions);

    /**
     * Returns a new object with the new sort expressions.
     * @param sortExpression the new sort expression list
     * @return a new object with sort {@code sortExpressions}
     */
    C withSort(final SortExpression<T> sortExpression);

    /**
     * Returns a new object with the new sort expression list appended to the existing sort expressions.
     * @param sortExpressions the new sort expression list
     * @return a new object with the existing sort expressions plus the new {@code sortExpressions}.
     */
    C plusSort(final List<SortExpression<T>> sortExpressions);

    /**
     * Returns a new object with the new sort expression list appended to the existing sort expressions.
     * @param sortExpression the new sort expression
     * @return a new object with the existing sort expressions plus the new {@code sortExpressions}.
     */
    C plusSort(final SortExpression<T> sortExpression);

}
