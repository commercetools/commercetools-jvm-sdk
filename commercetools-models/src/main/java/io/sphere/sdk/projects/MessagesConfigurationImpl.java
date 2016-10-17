package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class MessagesConfigurationImpl extends Base implements MessagesConfiguration {
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
