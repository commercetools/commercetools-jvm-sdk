package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class CategoryExpansionModel extends Base {
    private final Optional<String> parentPath;
    private final Optional<String> path;

    CategoryExpansionModel(final Optional<String> parentPath, final Optional<String>  path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    CategoryExpansionModel(final Optional<String> parentPath, final String path) {
        this(parentPath, Optional.ofNullable(path));
    }

    CategoryExpansionModel(final String parentPath, final String path) {
        this(Optional.ofNullable(parentPath), path);
    }

    CategoryExpansionModel(final String path) {
        this(Optional.empty(), path);
    }

    CategoryExpansionModel() {
        this(Optional.empty(), Optional.empty());
    }

    public CategoryExpansionPath ancestors() {
        return new CategoryExpansionPath(path, "ancestors[*]");
    }

    public CategoryExpansionPath parent() {
        return new CategoryExpansionPath(path, "parent");
    }

    protected String internalToSphereExpand() {
        return parentPath.map(p -> p + ".").orElse("") + path.get();
    }
}
