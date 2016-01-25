package io.sphere.sdk.jsonnodes.expansion;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.Base;

import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.stream.Collectors.toList;

public class JsonNodeExpansionModel<T> extends ExpansionModel<T> {
    public static JsonNodeExpansionModel<JsonNode> of() {
        return new JsonNodeExpansionModel<>();
    }

    public ExpansionPathContainer<T> paths(final List<String> expansionPaths) {
        final List<ExpansionPath<T>> expansionPathList = expansionPaths.stream()
                .map(ExpansionPath::<T>of)
                .collect(toList());
        return new ExpansionPathContainerImpl<>(expansionPathList);
    }

    public ExpansionPathContainer<T> paths(final String path, final String ... morePaths) {
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
