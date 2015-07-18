package io.sphere.sdk.expansion;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ExpandedModel<T> extends ExpansionPathBase<T> {
    protected final Optional<String> parentPath;
    protected final Optional<String> path;

    protected ExpandedModel(final Optional<String> parentPath, final Optional<String> path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    protected ExpandedModel(final String parentPath, final String path) {
        this(Optional.of(parentPath), Optional.of(path));
    }

    public ExpandedModel() {
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
        return new ExpandedModel<>(buildPathExpression(), path);
    }

    protected final String buildPathExpression() {
        return parentPath.filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + path.orElse("");
    }

    protected final Optional<String> pathExpressionOption() {
        final String expression = buildPathExpression();
        return isEmpty(expression) ? Optional.empty() : Optional.of(expression);
    }
}
