package io.sphere.client.shop.model;

import io.sphere.internal.command.ReviewCommands;
import io.sphere.internal.command.Update;

/** ReviewUpdate is used to update a {@link io.sphere.client.shop.model.Review} in the backend. */
public class ReviewUpdate extends Update<ReviewCommands.ReviewUpdateAction> {
    /** Sets the author name of the review. */
    public ReviewUpdate setAuthor(String author) {
        add(new ReviewCommands.SetAuthorName(author));
        return this;
    }

    /** Sets the title of the review. */
    public ReviewUpdate setTitle(String title) {
        add(new ReviewCommands.SetTitle(title));
        return this;
    }

    /** Sets the text of the review. */
    public ReviewUpdate setText(String text) {
        add(new ReviewCommands.SetText(text));
        return this;
    }

    /** Sets the score of the review. */
    public ReviewUpdate setScore(Double score) {
        add(new ReviewCommands.SetScore(score));
        return this;
    }
}
