package io.sphere.sdk.queries;

abstract class ComparisonQueryPredicate<T, V, M> extends QueryModelQueryPredicate<M> {
    private final V value;

    ComparisonQueryPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel);
        this.value = value;
    }

    @Override
    protected String render() {
        return " " + sign() + " " + value;
    }

    protected abstract String sign();
}
