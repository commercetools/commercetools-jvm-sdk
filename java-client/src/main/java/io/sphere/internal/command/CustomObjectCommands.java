package io.sphere.internal.command;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.JsonNode;

public class CustomObjectCommands {

    @Immutable
    public static class CreateOrUpdateCustomObject implements Command {
        private String container;
        private String key;
        private JsonNode value;

        public CreateOrUpdateCustomObject(String container, String key, JsonNode value) {
            this.container = container;
            this.key = key;
            this.value = value;
        }

        public String getContainer() { return container; }
        public String getKey() { return key; }
        public JsonNode getValue() { return value; }
    }

    @Immutable
    public static final class CreateOrUpdateVersionedCustomObject extends CreateOrUpdateCustomObject {
        private int version;

        public CreateOrUpdateVersionedCustomObject(String container, String key, JsonNode value, int version) {
            super(container, key, value);
            this.version = version;
        }

        public int getVersion() { return version; }
    }
}
