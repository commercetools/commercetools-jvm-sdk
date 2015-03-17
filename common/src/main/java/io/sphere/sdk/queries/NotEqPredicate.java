package io.sphere.sdk.queries;

class NotEqPredicate<T, V> extends QueryModelPredicate<T> {
    private final V value;
    private final String startQuote;
    private final String endQuote;

    private NotEqPredicate(QueryModel<T> queryModel, V value, final String startQuote, final String endQuote) {
        super(queryModel);
        this.value = value;
        this.startQuote = startQuote;
        this.endQuote = endQuote;
    }

    public static <T> NotEqPredicate<T, String> of(final QueryModel<T> queryModel, final String value) {
        return new NotEqPredicate<>(queryModel, value, "\"", "\"");
    }

    public static <T, V> NotEqPredicate<T, V> of(final QueryModel<T> queryModel, final V value) {
        return new NotEqPredicate<>(queryModel, value, "", "");
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
