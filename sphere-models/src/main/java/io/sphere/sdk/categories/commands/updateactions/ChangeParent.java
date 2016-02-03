package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
 Sets a concrete parent for the category. Currently it is not possible int the API to remove the parent.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#changeParent()}

 <p>Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/CategoryDocumentation.html#category-tree-changes">categories</a> for more information.</p>
 */
public final class ChangeParent extends UpdateActionImpl<Category> {
    private final Reference<Category> parent;

    private ChangeParent(final Referenceable<Category> parent) {
        super("changeParent");
        this.parent = parent.toReference().filled(null);
    }

    public static ChangeParent of(final Referenceable<Category> parent) {
        return new ChangeParent(parent);
    }

    public Reference<Category> getParent() {
        return parent;
    }
}