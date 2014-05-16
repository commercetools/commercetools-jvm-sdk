package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.common.models.LocalizedString;
import io.sphere.sdk.common.models.Reference;
import io.sphere.sdk.common.models.Versioned;
import org.joda.time.DateTime;

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
}
