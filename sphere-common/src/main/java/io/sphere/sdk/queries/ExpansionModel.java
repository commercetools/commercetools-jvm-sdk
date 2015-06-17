package io.sphere.sdk.queries;

import io.sphere.sdk.annotations.Internal;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Internal
public class ExpansionModel<T> extends ExpansionPathBase<T> {
    protected final Optional<String> parentPath;
    protected final Optional<String> path;

    protected ExpansionModel(final Optional<String> parentPath, final Optional<String>  path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    protected ExpansionModel(final String parentPath, final String path) {
        this(Optional.of(parentPath), Optional.of(path));
    }

    public ExpansionModel() {
        this(Optional.empty(), Optional.empty());
    }

    protected static String collection(final String segmentName, final int index) {
        return String.format("%s[%d]", segmentName, index);
    }

    @Override
    public String toSphereExpand() {
        return buildPathExpression();
    }

    protected ExpansionPath<T> expansionPath(final String path) {
        return new ExpansionModel<>(buildPathExpression(), path);
    }

    protected final String buildPathExpression() {
        return parentPath.filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + path.orElse("");
    }

    protected final Optional<String> pathExpressionOption() {
        final String expression = buildPathExpression();
        return isEmpty(expression) ? Optional.empty() : Optional.of(expression);
    }
}
