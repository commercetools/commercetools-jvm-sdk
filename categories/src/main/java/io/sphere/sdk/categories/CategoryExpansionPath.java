package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.ExpansionPath;

public class CategoryExpansionPath extends Base implements ExpansionPath {
    private final String path;

    public CategoryExpansionPath(final String path) {
        this.path = path;
    }

    public CategoryExpansionPath ancestors() {
        return new CategoryExpansionPath(path + "." + "ancestors[*]");
    }

    @Override
    public String toSphereExpand() {
        return path;
    }
}
