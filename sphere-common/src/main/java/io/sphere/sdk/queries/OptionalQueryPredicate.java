package io.sphere.sdk.queries;

class OptionalQueryPredicate<T> extends QueryModelQueryPredicate<T> {
    private final boolean isPresent;

    public OptionalQueryPredicate(final QueryModel<T> queryModel, final boolean isPresent) {
        super(queryModel);
        this.isPresent = isPresent;
    }

    @Override
    protected String render() {
        return isPresent ? " is defined" : " is not defined";
    }
}
