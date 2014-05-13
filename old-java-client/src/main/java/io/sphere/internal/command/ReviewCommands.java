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

    public static abstract class ReviewUpdateAction extends UpdateAction {
        
        protected ReviewUpdateAction(String action) { super(action); }
    }

    @Immutable
    public static final class SetAuthorName extends ReviewUpdateAction {
        private final String authorName;

        public SetAuthorName(String authorName) {
            super("setAuthorName");
            this.authorName = authorName;
        }

        public String getAuthorName() { return authorName; }
    }

    @Immutable
    public static final class SetTitle extends ReviewUpdateAction {
        private final String title;

        public SetTitle(String title) {
            super("setTitle");
            this.title = title;
        }

        public String getTitle() { return title; }
    }

    @Immutable
    public static final class SetText extends ReviewUpdateAction {
        private final String text;

        public SetText(String text) {
            super("setText");
            this.text = text;
        }

        public String getText() { return text; }
    }

    @Immutable
    public static final class SetScore extends ReviewUpdateAction {
        private final Double score;

        public SetScore(Double score) {
            super("setScore");
            this.score = score;
        }

        public Double getScore() { return score; }
    }
}
