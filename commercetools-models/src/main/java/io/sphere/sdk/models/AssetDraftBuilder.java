package io.sphere.sdk.models;

import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

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

    public AssetDraftBuilder tags(final String tag, final String ... moreTags) {
        final HashSet<String> tags = new HashSet<>();
        tags.add(tag);
        tags.addAll(asList(moreTags));
        return tags(tags);
    }

    public AssetDraftBuilder custom(final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    @Override
    public AssetDraft build() {
        return new AssetDraftImpl(sources, name, description, tags, custom);
    }
}
