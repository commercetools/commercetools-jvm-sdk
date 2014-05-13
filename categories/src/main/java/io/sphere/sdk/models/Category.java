package io.sphere.sdk.models;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import java.util.List;

public interface Category {
    String getId();

    long getVersion();

    DateTime getCreatedAt();

    DateTime getLastModifiedAt();

    LocalizedString getName();

    LocalizedString getSlug();

    Optional<LocalizedString> getDescription();

    List<Reference<CategoryImpl>> getAncestors();

    Optional<Reference<CategoryImpl>> getParent();

    Optional<String> getOrderHint();

    List<CategoryImpl> getChildren();
}
