package io.sphere.sdk.search;

import java.util.StringJoiner;

import static io.sphere.sdk.utils.IterableUtils.requireNonEmpty;

class IsInFacetExpression<T, V, M> extends SearchModelFacetExpression<M> {
    private final Iterable<V> values;

    /**
     * Creates a facet expression which is a shortcut for multiple or statements.
     * @param searchModel the parent model
     * @param values possible values to search for, if it is for Strings, the may need to be escaped concerning double quotes.
     * @throws IllegalArgumentException if values is empty
     */
    public IsInFacetExpression(final SearchModel<M> searchModel, final Iterable<V> values) {
        super(searchModel);
        requireNonEmpty(values);//SPHERE.IO requires values not to be empty
        this.values = values;
    }

    @Override
    protected String render() {
        final StringJoiner joiner = new StringJoiner("\", \"");
        values.forEach(x -> joiner.add(x.toString()));
        return " in (\"" + joiner.toString() + "\")";
    }
}
