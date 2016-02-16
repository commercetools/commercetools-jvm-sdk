package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets/unsets the target of a review.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#setTarget()}
 */
public final class SetTarget extends UpdateActionImpl<Review> {
    @Nullable
    private final ResourceIdentifier<?> target;

    private SetTarget(@Nullable final ResourceIdentifier<?> target) {
        super("setTarget");
        this.target = target;
    }

    public static SetTarget of(@Nullable final ResourceIdentifiable<?> target) {
        final ResourceIdentifier<?> resourceIdentifier = Optional.ofNullable(target)
                .map(ResourceIdentifiable::toResourceIdentifier)
                .orElse(null);
        return new SetTarget(resourceIdentifier);
    }


    @Nullable
    public ResourceIdentifier<?> getTarget() {
        return target;
    }
}
