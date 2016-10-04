package io.sphere.sdk.models;

import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public final class AssetDraftBuilder extends Base implements Builder<AssetDraft> {
    private List<AssetSource> sources;
    private LocalizedString name;
    @Nullable
    private LocalizedString description;
    @Nullable
    private Set<String> tags;
    @Nullable
    private CustomFieldsDraft custom;

    AssetDraftBuilder(final List<AssetSource> sources, final LocalizedString name) {
        this.sources = sources;
        this.name = name;
    }

    public static AssetDraftBuilder of(final List<AssetSource> sources, final LocalizedString name) {
        return new AssetDraftBuilder(sources, name);
    }

    public AssetDraftBuilder description(final LocalizedString description) {
        this.description = description;
        return this;
    }

    public AssetDraftBuilder tags(final Set<String> tags) {
        this.tags = tags;
        return this;
    }

    public AssetDraftBuilder custom(final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    public List<AssetSource> getSources() {
        return sources;
    }

    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public Set<String> getTags() {
        return tags;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Override
    public AssetDraft build() {
        return new AssetDraftImpl(sources, name, description, tags, custom);
    }
}
