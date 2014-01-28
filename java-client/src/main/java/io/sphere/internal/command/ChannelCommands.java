package io.sphere.internal.command;

import com.google.common.collect.Sets;
import io.sphere.client.shop.model.ChannelRoles;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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

    public static class RemoveRoles extends ChannelUpdateAction {
        private final Set<ChannelRoles> roles;

        public RemoveRoles(final Set<ChannelRoles> roles) {
            super("removeRoles");
            this.roles = roles;
        }

        public Set<ChannelRoles> getRoles() {
            return roles;
        }
    }

    public static class SetRoles extends ChannelUpdateAction {
        private final Set<ChannelRoles> roles;

        public SetRoles(final Set<ChannelRoles> roles) {
            super("setRoles");
            this.roles = roles;
        }

        public Set<ChannelRoles> getRoles() {
            return roles;
        }
    }

    public static class CreateChannel implements Command {
        private final String key;
        @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
        private final Set<ChannelRoles> roles;

        public CreateChannel(final String key, final Set<ChannelRoles> roles) {
            this.key = key;
            this.roles = roles;
        }

        public CreateChannel(final String key) {
            this(key, Sets.<ChannelRoles>newHashSet());
        }

        public String getKey() {
            return key;
        }

        public Set<ChannelRoles> getRoles() {
            return roles;
        }
    }
}
