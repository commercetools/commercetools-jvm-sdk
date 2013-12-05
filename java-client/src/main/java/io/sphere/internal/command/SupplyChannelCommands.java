package io.sphere.internal.command;

public class SupplyChannelCommands {
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
