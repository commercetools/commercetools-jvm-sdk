package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Internal base class
 * @param <T> context type
 */
public class ExpansionModelImpl<T> extends Base implements ExpansionPathContainer<T> {
    private final List<String> pathExpressions;

    //this is the primary constructor
    protected ExpansionModelImpl(@Nullable final List<String> nullableParentExpressions, @Nullable final List<String> nullablePaths) {
        final List<String> parentExpressions = nullableParentExpressions != null && !nullableParentExpressions.isEmpty() ? nullableParentExpressions : singletonList("");
        final List<String> paths = nullablePaths != null && !nullablePaths.isEmpty() ? nullablePaths : Collections.singletonList("");
        pathExpressions = parentExpressions.stream()
                .flatMap(parent -> paths.stream().map(path -> {
                    final boolean parentPresent = !isEmpty(parent);
                    final boolean pathPresent = !isEmpty(path);
                    if (parentPresent && pathPresent) {
                        return parent + "." + path;
                    } else if (parentPresent) {
                        return parent;
                    } else if (pathPresent) {
                        return path;
                    } else {
                        return "";
                    }
                }).filter(StringUtils::isNotBlank))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }

    protected ExpansionModelImpl(@Nullable final String parentPath, @Nullable final List<String> paths) {
        this(parentPath != null ? singletonList(parentPath) : null, paths);
    }

    protected ExpansionModelImpl(@Nullable final String parentPath, @Nullable final String path) {
        this(parentPath, singletonList(path));
    }

    public ExpansionModelImpl() {
        this((List<String>) null, (List<String>) null);
    }

    public ExpansionModelImpl(@Nullable final List<String> parentExpressions, @Nullable final String path) {
        this(parentExpressions, path != null ? singletonList(path) : null);
    }

    protected final List<String> buildPathExpression() {
        return pathExpressions;
    }

    @Nullable
    protected final List<String> pathExpression() {
        return buildPathExpression();
    }

    protected List<ExpansionPath<T>> buildExpansionPaths() {
        final List<String> paths = ObjectUtils.defaultIfNull(buildPathExpression(), Collections.<String>emptyList());
        return paths.stream()
                .map((sphereExpansionPathExpression) -> ExpansionPath.<T>of(sphereExpansionPathExpression))
                .collect(toList());
    }

    protected final ExpansionPathContainer<T> expansionPath(final String path) {
        return new ExpansionModelImpl<>(buildPathExpression(), path);
    }

    protected static String collection(final String segmentName, final Integer index) {
        return String.format("%s[%d]", segmentName, index);
    }

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return buildPathExpression().stream().map(ExpansionPath::<T>of).collect(toList());
    }
}
