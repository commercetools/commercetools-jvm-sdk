package io.sphere.sdk.queries;

public class OptionalPredicate<T> extends QueryModelPredicate<T> {
    private final boolean isPresent;

    public OptionalPredicate(final QueryModel<T> queryModel, final boolean isPresent) {
        super(queryModel);
        this.isPresent = isPresent;
    }

    @Override
    protected String render() {
        return isPresent ? " is defined" : " is not defined";
    }
}
