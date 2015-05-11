package io.sphere.sdk.queries;

class NotEqQueryPredicate<T, V> extends QueryModelQueryPredicate<T> {
    private final V value;
    private final String startQuote;
    private final String endQuote;

    private NotEqQueryPredicate(QueryModel<T> queryModel, V value, final String startQuote, final String endQuote) {
        super(queryModel);
        this.value = value;
        this.startQuote = startQuote;
        this.endQuote = endQuote;
    }

    public static <T> NotEqQueryPredicate<T, String> of(final QueryModel<T> queryModel, final String value) {
        return new NotEqQueryPredicate<>(queryModel, value, "\"", "\"");
    }

    public static <T, V> NotEqQueryPredicate<T, V> of(final QueryModel<T> queryModel, final V value) {
        return new NotEqQueryPredicate<>(queryModel, value, "", "");
    }

    @Override
    protected String render() {
        return " <> " + startQuote + value + endQuote;
    }

    @Override
    public String toString() {
        return "NotEqPredicate{" +
                "value=" + value +
                '}';
    }
}
