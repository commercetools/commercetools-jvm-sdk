package io.sphere.sdk.jsonnodes.expansion;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

public interface JsonNodeExpansionModel<T> {
    ExpansionPathContainer<T> paths(List<String> expansionPaths);

    ExpansionPathContainer<T> paths(String path, String... morePaths);

    static JsonNodeExpansionModel<JsonNode> of() {
        return new JsonNodeExpansionModelImpl<>();
    }
}
