package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ExpansionModel<T> extends Base {
    @Nullable
    protected final String parentPath;
    @Nullable
    protected final String path;

    protected ExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    public ExpansionModel() {
        this(null, null);
    }

    protected final String buildPathExpression() {
        return Optional.ofNullable(parentPath).filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + Optional.ofNullable(path).orElse("");
    }

    @Nullable
    protected final String pathExpression() {
        return buildPathExpression();
    }

    protected final ExpansionPath<T> expansionPath(final String path) {
        return new ExpandedModel<>(buildPathExpression(), path);
    }
}
