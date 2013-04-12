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

    @Immutable
    public static final class UpdateComment implements Command {
        private final int version;
        private String title;
        private String text;
        private Double score;

        public UpdateComment(int version, String title, String text) {
            this.version = version;
            this.title = title;
            this.text = text;
        }

        public int getVersion() { return version; }

        public String getTitle() { return title; }

        public String getText() { return text; }
    }
}
