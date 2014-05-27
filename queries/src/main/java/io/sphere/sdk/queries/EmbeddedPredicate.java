package io.sphere.sdk.queries;

public class EmbeddedPredicate<T, C> extends QueryModelPredicate<T> {
    private final Predicate<C> embedded;

    protected EmbeddedPredicate(QueryModel<T> queryModel, Predicate<C> embedded) {
        super(queryModel);

        this.embedded = embedded;
    }

    @Override
    protected String render() {
        return "(" + embedded.toSphereQuery() + ")";
    }
}