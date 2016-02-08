package io.sphere.sdk.search;

final class SimpleSortExpression<T> extends SimpleBaseExpression implements SortExpression<T> {
    private final String sphereSortExpression;

    SimpleSortExpression(final String sphereSortExpression) {
        this.sphereSortExpression = sphereSortExpression;
    }

    @Override
    public String expression() {
        return sphereSortExpression;
    }

    @Override
    protected String pattern() {
        return "^(?<attribute>[^\\s]*) (?<value>(asc|desc)(\\.(max|min))?)$";
    }
}
