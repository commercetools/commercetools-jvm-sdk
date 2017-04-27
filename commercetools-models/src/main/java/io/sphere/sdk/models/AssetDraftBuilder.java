package io.sphere.sdk.models;

import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public final class AssetDraftBuilder extends AssetDraftBuilderBase<AssetDraftBuilder> {

    AssetDraftBuilder(@Nullable final CustomFieldsDraft custom, @Nullable final LocalizedString description,
                             final LocalizedString name, final List<AssetSource> sources, @Nullable final Set<String> tags) {
        super(custom, description, name, sources, tags);
    }

    public AssetDraftBuilder tags(final String tag, final String ... moreTags) {
        final HashSet<String> tags = new HashSet<>();
        tags.add(tag);
        tags.addAll(asList(moreTags));
        return tags(tags);
    }
}
