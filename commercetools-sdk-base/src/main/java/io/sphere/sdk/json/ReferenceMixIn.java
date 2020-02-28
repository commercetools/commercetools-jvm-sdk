package io.sphere.sdk.json;


import com.fasterxml.jackson.annotation.JsonProperty;

abstract class ReferenceMixIn {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    abstract Object getObj();
}
