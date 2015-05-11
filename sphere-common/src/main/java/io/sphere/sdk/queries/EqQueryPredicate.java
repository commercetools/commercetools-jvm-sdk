package io.sphere.sdk.queries;

public class EqQueryPredicate<T, V> extends QueryModelQueryPredicate<T> {
    private final V value;
    private final String startQuote;
    private final String endQuote;

    private EqQueryPredicate(QueryModel<T> queryModel, V value, final String startQuote, final String endQuote) {
        super(queryModel);
        this.value = value;
        this.startQuote = startQuote;
        this.endQuote = endQuote;
    }

    public static <T> EqQueryPredicate<T, String> of(final QueryModel<T> queryModel, final String value) {
        return new EqQueryPredicate<>(queryModel, value, "\"", "\"");
    }

    public static <T, V> EqQueryPredicate<T, V> of(final QueryModel<T> queryModel, final V value) {
        return new EqQueryPredicate<>(queryModel, value, "", "");
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
