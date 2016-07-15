package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;

final class MessagesConfigurationImpl implements MessagesConfiguration {
    private Boolean enabled;

    @JsonCreator
    public MessagesConfigurationImpl(final Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }
}
