package io.sphere.sdk.projects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = MessagesConfigurationImpl.class)
public interface MessagesConfiguration {
    Boolean isEnabled();

    Long getDeleteDaysAfterCreation();
}
