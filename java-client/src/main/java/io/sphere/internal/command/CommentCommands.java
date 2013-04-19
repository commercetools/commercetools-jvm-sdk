package io.sphere.internal.command;

import net.jcip.annotations.Immutable;

/** Commands issued against the HTTP endpoints for working product comments. */
public class CommentCommands {
    @Immutable
    public static final class CreateComment implements Command {
        private String productId;
        private String customerId;
        private String authorName;
        private String title;
        private String text;

        public CreateComment(String productId, String customerId, String authorName, String title, String text) {
            this.productId = productId;
            this.customerId = customerId;
            this.authorName = authorName;
            this.title = title;
            this.text = text;
        }

        public String getProductId() { return productId; }

        public String getCustomerId() { return customerId; }

        public String getAuthorName() { return authorName; }

        public String getTitle() { return title; }

        public String getText() { return text; }
    }

    public static abstract class CommentUpdateAction extends UpdateAction {
        
        public CommentUpdateAction(String action) { super(action); }
    }
    
    @Immutable
    public static final class SetAuthorName extends CommentUpdateAction {
        private final String authorName;

        public SetAuthorName(String authorName) {
            super("setAuthorName");
            this.authorName = authorName; 
        }

        public String getAuthorName() { return authorName; }
    }

    @Immutable
    public static final class SetTitle extends CommentUpdateAction {
    private final String title;

    public SetTitle(String title) {
        super("setTitle");
        this.title = title;
    }

    public String getTitle() { return title; }
}

    @Immutable
    public static final class SetText extends CommentUpdateAction {
        private final String text;

        public SetText(String text) {
            super("setText");
            this.text = text;
        }

        public String getText() { return text; }
    }

}
