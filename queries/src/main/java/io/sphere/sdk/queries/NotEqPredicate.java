package io.sphere.sdk.queries;

public class NotEqPredicate<T, V> extends QueryModelPredicate<T> {
    private final V value;

    NotEqPredicate(QueryModel<T> queryModel, V value) {
        super(queryModel);
        this.value = value;
    }

    @Override
    protected String render() {
        return " <> \"" + value + '"';
    }

    @Override
    public String toString() {
        return "NotEqPredicate{" +
                "value=" + value +
                '}';
    }
}
