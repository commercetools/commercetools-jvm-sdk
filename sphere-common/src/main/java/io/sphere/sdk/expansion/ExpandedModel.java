package io.sphere.sdk.expansion;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    protected static String collection(final String segmentName, final Integer index) {
        return String.format("%s[%d]", segmentName, index);
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return buildPathExpression().stream().map(ExpansionPath::<T>of).collect(toList());
    }
}
