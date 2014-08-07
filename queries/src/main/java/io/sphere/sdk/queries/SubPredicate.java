package io.sphere.sdk.queries;

public class SubPredicate<T> extends QueryModelPredicate<T> {
    private final Predicate<T> predicate;

    public SubPredicate(final EmbeddedQueryModel<T> queryModel, final Predicate<T> predicate) {
        super(queryModel);
        this.predicate = predicate;
    }

    @Override
    protected String render() {
        return "(" + predicate.toSphereQuery() + ")";
    }
}
