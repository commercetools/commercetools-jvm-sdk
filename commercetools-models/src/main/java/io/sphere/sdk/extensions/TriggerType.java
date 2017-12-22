package io.sphere.sdk.extensions;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TriggerType {

    @JsonProperty("create")
    CREATE,

    @JsonProperty("update")
    UPDATE

}
