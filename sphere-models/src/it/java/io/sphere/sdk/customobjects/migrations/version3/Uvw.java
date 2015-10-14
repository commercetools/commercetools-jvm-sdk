package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "schemaVersion",
        defaultImpl = UvwSchemaVersion1.class)//here the first version is default implementation, it does not contain "schemaVersion"
@JsonSubTypes({
        @JsonSubTypes.Type(value = UvwSchemaVersion2.class, name = "2") })
public interface Uvw {
    Foo getFoo();

    String getAnotherField();

    @JsonIgnore
    static Uvw of(final Foo foo, final String anotherField) {
        return new UvwSchemaVersion2(foo, anotherField);
    }
}
