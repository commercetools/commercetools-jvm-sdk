package io.sphere.client.shop.model;

import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import java.util.List;

public class SupplyChannel {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    private String key;
    private List<SupplyChannelRoles> roles;

    protected SupplyChannel() {}

    public String getKey() {
        return key;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public List<SupplyChannelRoles> getRoles() {
        return roles;
    }

    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    @Override
    public String toString() {
        return "SupplyChannel{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", key='" + key + '\'' +
                ", roles=" + roles +
                '}';
    }
}
