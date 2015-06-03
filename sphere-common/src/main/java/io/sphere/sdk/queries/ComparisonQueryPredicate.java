package io.sphere.sdk.queries;

final class ComparisonQueryPredicate<V, M> extends QueryModelQueryPredicate<M> {
    private final V value;
    private final String sign;
    private final String startQuote;
    private final String endQuote;

    ComparisonQueryPredicate(final QueryModel<M> queryModel, final V value, final String sign, final String startQuote, final String endQuote) {
        super(queryModel);
        this.value = value;
        this.sign = sign;
        this.startQuote = startQuote;
        this.endQuote = endQuote;
    }

    @Override
    protected String render() {
        return sign + startQuote + value + endQuote;
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsLessThan(final QueryModel<M> queryModel, final V value) {
        return of(queryModel, value, "<");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsGreaterThan(final QueryModel<M> queryModel, final V value) {
        return of(queryModel, value, ">");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofGreaterThanOrEqualTo(final QueryModel<M> queryModel, final V value) {
        return of(queryModel, value, ">=");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsLessThanOrEqualTo(final QueryModel<M> queryModel, final V value) {
        return of(queryModel, value, "<=");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsEqualTo(final QueryModel<M> queryModel, final V value) {
        return of(queryModel, value, "=");
    }

    public static <M> ComparisonQueryPredicate<String, M> ofIsEqualTo(final QueryModel<M> queryModel, final String value) {
        return new ComparisonQueryPredicate<>(queryModel, value, "=", "\"", "\"");
    }

    public static <T, V> ComparisonQueryPredicate<V, T> ofIsNotEqualTo(final QueryModel<T> queryModel, final V value) {
        return of(queryModel, value, "<>");
    }

    public static <T> ComparisonQueryPredicate<String, T> ofIsNotEqualTo(final QueryModel<T> queryModel, final String value) {
        return new ComparisonQueryPredicate<>(queryModel, value, "<>", "\"", "\"");
    }

    private static <V, M> ComparisonQueryPredicate<V, M> of(final QueryModel<M> queryModel, final V value, final String sign) {
        return new ComparisonQueryPredicate<>(queryModel, value, sign, "", "");
    }
}
