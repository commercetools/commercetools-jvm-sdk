package io.sphere.sdk.search;

import io.sphere.sdk.models.LocalizedStringEntry;

import javax.annotation.Nullable;
import java.util.List;

public interface EntitySearch<T> extends Search<T> {

    @Nullable
    LocalizedStringEntry text();

    @Nullable
    Boolean isFuzzy();

    @Nullable
    Long limit();

    @Nullable
    Long offset();

    String endpoint();
}
