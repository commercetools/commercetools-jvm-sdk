package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

public class ChangeParent extends UpdateAction<Category> {
    private final Reference<Category> parent;

    private ChangeParent(final Referenceable<Category> parent) {
        super("changeParent");
        this.parent = parent.toReference().filled(Optional.empty());
    }

    public static ChangeParent of(final Referenceable<Category> parent) {
        return new ChangeParent(parent);
    }

    public Reference<Category> getParent() {
        return parent;
    }
}