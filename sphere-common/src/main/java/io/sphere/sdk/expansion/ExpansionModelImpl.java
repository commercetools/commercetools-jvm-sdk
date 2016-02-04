package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Internal base class
 * @param <T> context type
 */
public class ExpansionModelImpl<T> extends Base implements ExpansionPathContainer<T> {
    private final List<String> pathExpressions;

    protected ExpansionModelImpl(@Nullable final String parentPath, final List<String> paths) {
        this(paths.stream()
                .map(path ->
                Optional.ofNullable(parentPath)
                        .filter(p -> !isEmpty(p)).map(p -> p + ".").orElse("") + Optional.ofNullable(path).orElse(""))
                .collect(toList()), null);
    }

    protected ExpansionModelImpl(@Nullable final String parentPath, @Nullable final String path) {
        this(parentPath, Collections.singletonList(path));
    }

    public ExpansionModelImpl() {
        this((List<String>) null, null);
    }

    public ExpansionModelImpl(final List<String> parentExpressions, final String path) {
        final List<String> nullSafeParentExpressions = parentExpressions == null ? Collections.singletonList("") : parentExpressions;
        this.pathExpressions = !isEmpty(path) ? nullSafeParentExpressions.stream()
                .map(p -> isEmpty(p) ? path : p + "." + path)
                .collect(Collectors.toList()) : nullSafeParentExpressions;
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
