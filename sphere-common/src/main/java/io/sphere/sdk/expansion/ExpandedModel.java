package io.sphere.sdk.expansion;

import java.util.Optional;

public class ExpandedModel<T> extends ExpansionModel<T> implements ExpansionPath<T> {

    protected ExpandedModel(final Optional<String> parentPath, final Optional<String> path) {
        super(parentPath, path);
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

    @Override
    public final int hashCode() {
        return toSphereExpand().hashCode();
    }

    @Override
    public final boolean equals(final Object o) {
        return ExpansionPathBase.equals(this, o);
    }
}
