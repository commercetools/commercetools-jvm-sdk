package io.sphere.sdk.queries;

class QueryPredicateConnector<T> extends QueryPredicateBase<T> {
    private final String connectorWord;
    private final QueryPredicate<T> leftPredicate;
    private final QueryPredicate<T> rightPredicate;

    QueryPredicateConnector(String connectorWord, QueryPredicate<T> leftPredicate, QueryPredicate<T> rightPredicate) {
        this.connectorWord = connectorWord;
        this.leftPredicate = leftPredicate;
        this.rightPredicate = rightPredicate;
    }

    @Override
    public String toSphereQuery() {
        return leftPredicate.toSphereQuery() + " " + connectorWord + " " + rightPredicate.toSphereQuery();
    }
}