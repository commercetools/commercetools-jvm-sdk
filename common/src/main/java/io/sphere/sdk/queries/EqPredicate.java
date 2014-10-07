package io.sphere.sdk.queries;

public class EqPredicate<T, V> extends QueryModelPredicate<T> {
    private final V value;

    public EqPredicate(QueryModel<T> queryModel, V value) {
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
