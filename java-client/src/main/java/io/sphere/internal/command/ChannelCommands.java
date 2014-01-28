package io.sphere.internal.command;

import io.sphere.client.shop.model.ChannelRoles;

import java.util.Set;

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

    public static class AddRoles extends ChannelUpdateAction {
        private final Set<ChannelRoles> roles;

        public AddRoles(final Set<ChannelRoles> roles) {
            super("addRoles");
            this.roles = roles;
        }

        public Set<ChannelRoles> getRoles() {
            return roles;
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
