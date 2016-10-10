package io.sphere.sdk.models;

import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

final class AssetDraftImpl implements AssetDraft {
    private final List<AssetSource> sources;
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    @Nullable
    private final Set<String> tags;
    @Nullable
    private final CustomFieldsDraft custom;

    AssetDraftImpl(final List<AssetSource> sources, final LocalizedString name, final LocalizedString description, final Set<String> tags, final CustomFieldsDraft custom) {
        this.sources = sources;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.custom = custom;
    }

    @Override
    public List<AssetSource> getSources() {
        return sources;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    @Nullable
    public Set<String> getTags() {
        return tags;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }
}
