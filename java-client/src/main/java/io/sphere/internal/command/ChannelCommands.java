package io.sphere.internal.command;

public class ChannelCommands {
    public static abstract class ChannelUpdateAction extends UpdateAction {
        public ChannelUpdateAction(final String action) {
            super(action);
        }
    }

    public static class ChangeKey extends ChannelUpdateAction {
        private final String key;

        public ChangeKey(final String key) {
            super("changeKey");
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static class CreateChannel implements Command {
        private final String key;

        public CreateChannel(final String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
