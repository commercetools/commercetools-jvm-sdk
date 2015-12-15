package io.sphere.sdk.expansion;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ExpandedModel<T> extends ExpansionModel<T> implements ExpansionPath<T>, ExpansionPathsHolder<T> {

    protected ExpandedModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpandedModel() {
        this(null, null);
    }

    protected static String collection(final String segmentName, final Integer index) {
        return String.format("%s[%d]", segmentName, index);
    }

    @Override
    public String toSphereExpand() {
        return buildPathExpression();
    }

    @Override
    public final int hashCode() {
        return toSphereExpand().hashCode();
    }

    @Override
    public final boolean equals(final Object o) {
        return ExpansionPathBase.equals(this, o);
    }

    @Override
    public List<ExpansionPath<T>> getExpansionPaths() {
        return Collections.singletonList(this);
    }
}
