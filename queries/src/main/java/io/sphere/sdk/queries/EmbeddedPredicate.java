package io.sphere.sdk.queries;

public class EmbeddedPredicate<T, V> extends QueryModelPredicate<T> {
    private final Predicate<V> predicate;

    public EmbeddedPredicate(final EmbeddedQueryModel<T> queryModel, final Predicate<V> predicate) {
        super(queryModel);
        this.predicate = predicate;
    }

    @Override
    protected String render() {
        return "(" + predicate.toSphereQuery() + ")";
    }
}
