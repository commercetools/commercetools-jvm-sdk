package io.sphere.internal.command;

import net.jcip.annotations.Immutable;

/** Commands issued against the HTTP endpoints for working product reviews. */
public class ReviewCommands {
    @Immutable
    public static final class CreateReview implements Command {
        private String productId;
        private String customerId;
        private String authorName;
        private String title;
        private String text;
        private Double score;

        public CreateReview(String productId, String customerId, String authorName, String title, String text, Double score) {
            this.productId = productId;
            this.customerId = customerId;
            this.authorName = authorName;
            this.title = title;
            this.text = text;
            this.score = score;
        }

        public String getProductId() { return productId; }

        public String getCustomerId() { return customerId; }

        public String getAuthorName() { return authorName; }

        public String getTitle() { return title; }

        public String getText() { return text; }

        public Double getScore() { return score; }
    }

    @Immutable
    public static final class UpdateReview implements Command {
        private int version;
        private String title;
        private String text;
        private Double score;

        public UpdateReview(int version, String title, String text, Double score) {
            this.version = version;
            this.title = title;
            this.text = text;
            this.score = score;
        }

        public int getVersion() { return version; }

        public String getTitle() { return title; }

        public String getText() { return text; }

        public Double getScore() { return score; }
    }
}
