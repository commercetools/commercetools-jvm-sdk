package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public interface ReviewUpdateCommand extends UpdateCommandDsl<Review, ReviewUpdateCommand> {
    static ReviewUpdateCommand of(final Versioned<Review> versioned, final UpdateAction<Review> updateAction) {
        return of(versioned, asList(updateAction));
    }

    static ReviewUpdateCommand of(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        return new ReviewUpdateCommandImpl(versioned, updateActions);
    }
}
