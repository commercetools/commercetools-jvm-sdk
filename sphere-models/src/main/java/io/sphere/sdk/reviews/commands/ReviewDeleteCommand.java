package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;


public interface ReviewDeleteCommand extends MetaModelReferenceExpansionDsl<Review, ReviewDeleteCommand, ReviewExpansionModel<Review>>, DeleteCommand<Review> {
    static ReviewDeleteCommand of(final Versioned<Review> versioned) {
        return new ReviewDeleteCommandImpl(versioned);
    }

    static ReviewDeleteCommand ofKey(final String key, final Long version) {
        final Versioned<Review> versioned = Versioned.of("key=" + key, version);//hack for simple reuse
        return of(versioned);
    }
}
