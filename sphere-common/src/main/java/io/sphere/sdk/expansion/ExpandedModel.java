package io.sphere.sdk.expansion;

import javax.annotation.Nullable;

public class ExpandedModel<T> extends ExpansionModel<T> implements ExpansionPath<T> {

    protected ExpandedModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpandedModel() {
        this(null, null);
    }

    protected static String collection(final String segmentName, final int index) {
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
}
