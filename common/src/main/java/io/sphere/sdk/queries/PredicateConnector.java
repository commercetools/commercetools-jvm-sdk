package io.sphere.sdk.queries;

class PredicateConnector<T> extends PredicateBase<T> {
    private final String connectorWord;
    private final Predicate<T> leftPredicate;
    private final Predicate<T> rightPredicate;

    PredicateConnector(String connectorWord, Predicate<T> leftPredicate, Predicate<T> rightPredicate) {
        this.connectorWord = connectorWord;
        this.leftPredicate = leftPredicate;
        this.rightPredicate = rightPredicate;
    }

    @Override
    public String toSphereQuery() {
        return leftPredicate.toSphereQuery() + " " + connectorWord + " " + rightPredicate.toSphereQuery();
    }
}