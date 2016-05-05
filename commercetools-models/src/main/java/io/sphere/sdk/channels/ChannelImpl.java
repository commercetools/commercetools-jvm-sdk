package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

class ChannelImpl extends ResourceImpl<Channel> implements Channel {
    private final String key;
    private final Set<ChannelRole> roles;
    @Nullable
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    @Nullable
    private final ReviewRatingStatistics reviewRatingStatistics;
    @Nullable
    private final CustomFields custom;

    @JsonCreator
    ChannelImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                final String key, final Set<ChannelRole> roles, @Nullable final LocalizedString name,
                @Nullable final LocalizedString description, @Nullable final ReviewRatingStatistics reviewRatingStatistics,
                @Nullable final CustomFields custom) {
        super(id, version, createdAt, lastModifiedAt);
        this.key = key;
        this.roles = roles;
        this.name = name;
        this.description = description;
        this.reviewRatingStatistics = reviewRatingStatistics;
        this.custom = custom;
    }

    public String getKey() {
        return key;
    }

    public Set<ChannelRole> getRoles() {
        return roles;
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    @Nullable
    public ReviewRatingStatistics getReviewRatingStatistics() {
        return reviewRatingStatistics;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }
}
