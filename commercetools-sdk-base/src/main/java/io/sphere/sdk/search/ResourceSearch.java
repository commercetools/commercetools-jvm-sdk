package io.sphere.sdk.search;

import io.sphere.sdk.models.LocalizedStringEntry;

import javax.annotation.Nullable;

public interface ResourceSearch<T> extends Search<T> {

    @Nullable
    LocalizedStringEntry text();

    @Nullable
    Boolean isFuzzy();

    @Nullable
    Integer fuzzyLevel();

    @Nullable
    Long limit();

    @Nullable
    Long offset();

    String endpoint();
}
