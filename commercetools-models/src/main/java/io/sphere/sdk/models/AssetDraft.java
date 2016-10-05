package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Draft for an {@link Asset}.
 *
 * @see AssetDraftBuilder
 */
@JsonDeserialize(as = AssetDraftImpl.class)
public interface AssetDraft extends CustomDraft {
    List<AssetSource> getSources();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    Set<String> getTags();

    @Override
    @Nullable
    CustomFieldsDraft getCustom();
}
