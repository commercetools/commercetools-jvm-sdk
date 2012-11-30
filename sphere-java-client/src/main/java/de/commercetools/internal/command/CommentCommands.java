package de.commercetools.internal.command;

import net.jcip.annotations.Immutable;

/** Commands issued against the HTTP endpoints for working product comments. */
public class CommentCommands {
    @Immutable
    public static final class CreateComment implements Command {
        private String productId;
        private String customerId;
        private String title;
        private String text;

        public CreateComment(String productId, String customerId, String title, String text) {
            this.productId = productId;
            this.customerId = customerId;
            this.title = title;
            this.text = text;
        }

        public String getProductId() {
            return productId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

    }

    @Immutable
    public static final class UpdateComment extends CommandBase {
        private String title;
        private String text;
        private Double score;

        public UpdateComment(String id, int version, String title, String text) {
            super(id, version);
            this.title = title;
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }
    }
}
