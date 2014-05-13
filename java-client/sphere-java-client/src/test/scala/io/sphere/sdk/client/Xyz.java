package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Xyz {
    private final String id;

    @JsonCreator
    public Xyz(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Xyz xyz = (Xyz) o;

        if (id != null ? !id.equals(xyz.id) : xyz.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Xyz{" +
                "id='" + id + '\'' +
                '}';
    }
}
