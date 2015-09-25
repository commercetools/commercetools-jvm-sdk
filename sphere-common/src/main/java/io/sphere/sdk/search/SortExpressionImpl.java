package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

public class SortExpressionImpl<T> extends Base implements SortExpression<T> {
    private final SearchModel<T> searchModel;
    private final SearchSortDirection direction;

    protected SortExpressionImpl(final SearchModel<T> searchModel, final SearchSortDirection direction) {
        this.searchModel = searchModel;
        this.direction = direction;
    }

    @Override
    public String expression() {
        return renderPath() + " " + directionToString();
    }

    @Override
    public final String attributePath() {
        return toStream(searchModel.buildPath()).collect(joining("."));
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof SortExpression && expression().equals(((SortExpression) o).expression());
    }

    @Override
    public final int hashCode() {
        return expression().hashCode();
    }

    private String directionToString() {
        return direction.toString().toLowerCase().replace("_", ".");
    }

    private String renderPath() {
        return toStream(searchModel.buildPath()).collect(joining("."));
    }
}