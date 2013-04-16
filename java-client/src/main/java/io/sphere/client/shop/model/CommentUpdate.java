package io.sphere.client.shop.model;

import io.sphere.internal.command.CommentCommands;
import io.sphere.internal.command.Update;

/** CommentUpdate is used to update a {@link io.sphere.client.shop.model.Comment} in the backend. */
public class CommentUpdate extends Update<CommentCommands.CommentUpdateAction> {
    
    /** Sets the author name of the comment. */
    public CommentUpdate setAuthorName(String authorName) {
        addAction(new CommentCommands.SetAuthorName(authorName));
        return this;
    }

    /** Sets the title of the comment. */
    public CommentUpdate setTitle(String title) {
        addAction(new CommentCommands.SetTitle(title));
        return this;
    }

    /** Sets the text of the comment.  */
    public CommentUpdate setText(String text) {
        addAction(new CommentCommands.SetText(text));
        return this;
    }

}
