package io.sphere.sdk.search;

public class EqFacetExpression<T, V> extends SearchModelFacetExpression<T> {
    private final V value;

    public EqFacetExpression(SearchModel<T> searchModel, V value) {
        super(searchModel);
        this.value = value;
    }

    @Override
    protected String render() {
        return "=\"" + value + '"';
    }

    @Override
    public String toString() {
        return "EqFacetExpression{" +
                "value=" + value +
                '}';
    }
}
