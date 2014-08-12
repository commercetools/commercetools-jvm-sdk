package io.sphere.sdk.queries;

class EqPredicate<T, V> extends QueryModelPredicate<T> {
    private final V value;

    EqPredicate(QueryModel<T> queryModel, V value) {
        super(queryModel);
        this.value = value;
    }

    @Override
    protected String render() {
        return "=\"" + value + '"';
    }

    @Override
    public String toString() {
        return "EqPredicate{" +
                "value=" + value +
                '}';
    }
}
