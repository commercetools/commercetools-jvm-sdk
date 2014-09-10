package io.sphere.sdk.queries;

abstract class ComparisonPredicate<T, V, M> extends QueryModelPredicate<M> {
    private final V value;

    ComparisonPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel);
        this.value = value;
    }

    @Override
    protected String render() {
        return " " + sign() + " " + value;
    }

    protected abstract String sign();
}
