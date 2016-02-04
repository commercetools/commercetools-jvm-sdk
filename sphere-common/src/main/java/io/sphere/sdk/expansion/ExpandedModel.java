package io.sphere.sdk.expansion;

import javax.annotation.Nullable;
import java.util.List;

public class ExpandedModel<T> extends ExpansionModel<T> implements ExpansionPathContainer<T> {

    protected ExpandedModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpandedModel() {
        this((String) null, null);
    }

    public ExpandedModel(final List<String> pathExpression, final String path) {
        super(pathExpression, path);
    }
}
