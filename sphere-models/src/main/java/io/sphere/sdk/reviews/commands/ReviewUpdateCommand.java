package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class ReviewUpdateCommand extends UpdateCommandDslImpl<Review> {
    private ReviewUpdateCommand(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        super(versioned, updateActions, ReviewsEndpoint.ENDPOINT);
    }

    public static ReviewUpdateCommand of(final Versioned<Review> versioned, final UpdateAction<Review> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static ReviewUpdateCommand of(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        return new ReviewUpdateCommand(versioned, updateActions);
    }
}
