package io.sphere.client.shop.model;

import io.sphere.internal.command.CommentCommands;
import io.sphere.internal.command.Update;

/** CommentUpdate is used to update a {@link io.sphere.client.shop.model.Comment} in the backend. */
public class CommentUpdate extends Update<CommentCommands.CommentUpdateAction> {
    /** Sets the author of the comment. */
    public CommentUpdate setAuthor(String author) {
        add(new CommentCommands.SetAuthor(author));
        return this;
    }

    /** Sets the title of the comment. */
    public CommentUpdate setTitle(String title) {
        add(new CommentCommands.SetTitle(title));
        return this;
    }

    /** Sets the text of the comment.  */
    public CommentUpdate setText(String text) {
        add(new CommentCommands.SetText(text));
        return this;
    }
}
