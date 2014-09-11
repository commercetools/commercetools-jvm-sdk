package io.sphere.sdk.queries;

import io.sphere.sdk.annotations.Internal;
import io.sphere.sdk.models.Base;

import java.util.Optional;

@Internal
public class ExpansionModel extends Base {
    protected final Optional<String> parentPath;
    protected final Optional<String> path;

    public ExpansionModel(final Optional<String> parentPath, final Optional<String>  path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    public ExpansionModel() {
        this(Optional.empty(), Optional.empty());
    }

    protected String internalToSphereExpand() {
        return parentPath.map(p -> p + ".").orElse("") + path.get();
    }
}
