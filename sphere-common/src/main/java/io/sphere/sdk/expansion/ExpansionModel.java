package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ExpansionModel<T> extends Base {
    private final List<String> pathExpressions;

    protected ExpansionModel(@Nullable final String parentPath, final List<String> paths) {
        pathExpressions = paths.stream()
                .map(path ->
                Optional.ofNullable(parentPath)
                        .filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + Optional.ofNullable(path).orElse(""))
                .collect(toList());
    }

    protected ExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        this(parentPath, Collections.singletonList(path));
    }

    public ExpansionModel() {
        this("", "");
    }

    public ExpansionModel(final List<String> parentExpressions, final String path) {
        this.pathExpressions = !isEmpty(path) ? parentExpressions.stream()
                .map(p -> isEmpty(p) ? path : p + "." + path)
                .collect(Collectors.toList()) : parentExpressions;
    }

    protected final List<String> buildPathExpression() {
        return pathExpressions;
    }

    @Nullable
    protected final List<String> pathExpression() {
        return buildPathExpression();
    }

    protected final ExpansionPathContainer<T> expansionPath(final String path) {
        return new ExpandedModel<>(buildPathExpression(), path);
    }
}
