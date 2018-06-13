package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;

/**
 Sets a concrete parent for the category. Currently it is not possible int the API to remove the parent.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#changeParent()}

 <p>Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/CategoryDocumentation.html#category-tree-changes">categories</a> for more information.</p>
 */
public final class ChangeParent extends UpdateActionImpl<Category> {
    private final ResourceIdentifier<Category> parent;

    private ChangeParent(final ResourceIdentifier<Category> parent) {
        super("changeParent");
        this.parent = parent.toResourceIdentifier();
    }

    public static ChangeParent of(final Referenceable<Category> parent) {
        return new ChangeParent(parent.toResourceIdentifier());
    }

    public static ChangeParent of(final ResourceIdentifier<Category> parent) {
        return new ChangeParent(parent);
    }

    public ResourceIdentifier<Category> getParent() {
        return parent;
    }
}