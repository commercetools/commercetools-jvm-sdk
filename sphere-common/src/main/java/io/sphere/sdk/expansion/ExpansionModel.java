package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ExpansionModel<T> extends Base {
    protected final Optional<String> parentPath;
    protected final Optional<String> path;

    protected ExpansionModel(final Optional<String> parentPath, final Optional<String> path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    protected ExpansionModel(final String parentPath, final String path) {
        this(Optional.of(parentPath), Optional.of(path));
    }

    public ExpansionModel() {
        this(Optional.empty(), Optional.empty());
    }

    protected final String buildPathExpression() {
        return parentPath.filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + path.orElse("");
    }

    protected final Optional<String> pathExpressionOption() {
        final String expression = buildPathExpression();
        return isEmpty(expression) ? Optional.empty() : Optional.of(expression);
    }

    protected final ExpansionPath<T> expansionPath(final String path) {
        return new ExpandedModel<>(buildPathExpression(), path);
    }
}
