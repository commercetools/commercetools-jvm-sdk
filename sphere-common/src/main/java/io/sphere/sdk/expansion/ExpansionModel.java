package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ExpansionModel<T> extends Base {
    @Nullable
    private final String parentPath;
    @Nullable
    private final String path;

    protected ExpansionModel(@Nullable final String parentPath, final List<String> paths) {

        this.parentPath = null;
        this.path = null;
    }

    protected ExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    public ExpansionModel() {
        parentPath = null;
        path = null;
    }

    protected final String buildPathExpression() {
        return Optional.ofNullable(parentPath)
                .filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + Optional.ofNullable(path)
                .orElse("");
    }

    @Nullable
    protected final String pathExpression() {
        return buildPathExpression();
    }

    protected final ExpansionPathsHolder<T> expansionPath(final String path) {
        return new ExpandedModel<>(buildPathExpression(), path);
    }
}
