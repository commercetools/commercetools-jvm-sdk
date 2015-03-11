package io.sphere.sdk.queries;

public class EqPredicate<T, V> extends QueryModelPredicate<T> {
    private final V value;
    private final String startQuote;
    private final String endQuote;

    public EqPredicate(QueryModel<T> queryModel, V value, final String startQuote, final String endQuote) {
        super(queryModel);
        this.value = value;
        this.startQuote = startQuote;
        this.endQuote = endQuote;
    }

    public static <T, V> EqPredicate<T, V> ofUnquotet(final QueryModel<T> queryModel, final V value) {
        return new EqPredicate<>(queryModel, value, "", "");
    }

    public EqPredicate(QueryModel<T> queryModel, V value) {
        this(queryModel, value, "\"", "\"");
    }

    @Override
    protected String render() {
        return "=" + startQuote + value + endQuote;
    }

    @Override
    public String toString() {
        return "EqPredicate{" +
                "value=" + value +
                '}';
    }
}
