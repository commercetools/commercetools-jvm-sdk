package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import java.util.List;

public interface Category extends DefaultModel {

    LocalizedString getName();

    LocalizedString getSlug();

    Optional<LocalizedString> getDescription();

    List<Reference<Category>> getAncestors();

    Optional<Reference<Category>> getParent();

    Optional<String> getOrderHint();

    List<Category> getChildren();

    /**
     * The path to this category in the category tree, starting with the root and ending with this category.
     */
    List<Category> getPathInTree();
}
