package io.sphere.sdk.queries;

import io.sphere.sdk.annotations.Internal;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Internal
public class ExpansionModel<T> extends Base implements Builder<ExpansionPath<T>> {
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

    protected ExpansionPath<T> pathWithRoots(final String path) {
        return new ExpansionModel<T>(buildPathExpression(), path).build();
    }

    @Override
    public final ExpansionPath<T> build() {
        return ExpansionPath.of(buildPathExpression());
    }

    protected final String buildPathExpression() {
        return parentPath.filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + path.orElse("");
    }

    protected final Optional<String> pathExpressionOption() {
        final String expression = buildPathExpression();
        return isEmpty(expression) ? Optional.empty() : Optional.of(expression);
    }
}
