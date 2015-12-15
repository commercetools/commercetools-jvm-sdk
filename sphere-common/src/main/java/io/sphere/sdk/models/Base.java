package io.sphere.sdk.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * A base class which implements {@link Object#toString()}, {@link Object#hashCode()} and {@link Object#equals(Object)} by reflection.
 */
public abstract class Base {

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, SdkDefaults.TO_STRING_STYLE)
                //important to not log passwords
                .setExcludeFieldNames("password", "newPassword", "currentPassword", "oldPassword", "access_token", "accessToken")
                .build();
    }
}
