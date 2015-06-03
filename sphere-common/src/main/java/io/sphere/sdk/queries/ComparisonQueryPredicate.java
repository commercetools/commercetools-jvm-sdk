package io.sphere.sdk.queries;

final class ComparisonQueryPredicate<V, M> extends QueryModelQueryPredicate<M> {
    private final V value;
    private final String sign;

    ComparisonQueryPredicate(final QueryModel<M> queryModel, final V value, final String sign) {
        super(queryModel);
        this.value = value;
        this.sign = sign;
    }

    @Override
    protected String render() {
        return " " + sign + " " + value;
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsLessThan(final QueryModel<M> queryModel, final V value) {
        return new ComparisonQueryPredicate<>(queryModel, value, "<");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsGreaterThan(final QueryModel<M> queryModel, final V value) {
        return new ComparisonQueryPredicate<>(queryModel, value, ">");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofGreaterThanOrEqualTo(final QueryModel<M> queryModel, final V value) {
        return new ComparisonQueryPredicate<>(queryModel, value, ">=");
    }

    public static <V, M> ComparisonQueryPredicate<V, M> ofIsLessThanOrEqualTo(final QueryModel<M> queryModel, final V value) {
        return new ComparisonQueryPredicate<>(queryModel, value, "<=");
    }
}
