package io.sphere.sdk.jsonnodes.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.Base;

import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.stream.Collectors.toList;

final class JsonNodeExpansionModelImpl<T> extends ExpansionModelImpl<T> implements JsonNodeExpansionModel<T> {
    @Override
    public ExpansionPathContainer<T> paths(final List<String> expansionPaths) {
        final List<ExpansionPath<T>> expansionPathList = expansionPaths.stream()
                .map(ExpansionPath::<T>of)
                .collect(toList());
        return new ExpansionPathContainerImpl<>(expansionPathList);
    }

    @Override
    public ExpansionPathContainer<T> paths(final String path, final String... morePaths) {
        return paths(listOf(path, morePaths));
    }

    private static class ExpansionPathContainerImpl<T> extends Base implements ExpansionPathContainer<T> {
        private final List<ExpansionPath<T>> expansionPaths;

        public ExpansionPathContainerImpl(final List<ExpansionPath<T>> expansionPaths) {
            this.expansionPaths = expansionPaths;
        }

        @Override
        public List<ExpansionPath<T>> expansionPaths() {
            return expansionPaths;
        }
    }
}
