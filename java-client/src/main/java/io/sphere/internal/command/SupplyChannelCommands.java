package io.sphere.internal.command;

public class SupplyChannelCommands {
    public static abstract class SupplyChannelUpdateAction extends UpdateAction {
        public SupplyChannelUpdateAction(final String action) {
            super(action);
        }
    }

    public static class ChangeKey extends SupplyChannelUpdateAction {
        private final String key;

        public ChangeKey(final String key) {
            super("changeKey");
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static class CreateSupplyChannel implements Command {
        private final String key;

        public CreateSupplyChannel(final String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
