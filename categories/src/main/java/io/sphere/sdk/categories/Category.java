package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Versioned;
import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.List;

public interface Category extends Versioned {
    String getId();

    long getVersion();

    DateTime getCreatedAt();

    DateTime getLastModifiedAt();

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
