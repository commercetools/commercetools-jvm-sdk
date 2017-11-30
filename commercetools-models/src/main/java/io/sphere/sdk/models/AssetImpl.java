package io.sphere.sdk.models;

import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

final class AssetImpl extends Base implements Asset {
    private final String id;
    private final List<AssetSource> sources;
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final Set<String> tags;
    @Nullable
    private final CustomFields custom;

    @Nullable
    private final String key;


    AssetImpl(final String id, final String key, final List<AssetSource> sources, final LocalizedString name, @Nullable final LocalizedString description, @Nullable final Set<String> tags, @Nullable final CustomFields custom) {
        this.id = id;
        this.key = key;
        this.sources = sources;
        this.name = name;
        this.description = description;
        this.tags = tags == null ? Collections.emptySet() : tags;
        this.custom = custom;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
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

    /**
     * Gets the tags belonging to this asset or an empty set.
     *
     * @return tags
     */
    @Override
    @Nonnull
    public Set<String> getTags() {
        return tags;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }
}
