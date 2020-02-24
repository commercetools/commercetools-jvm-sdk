package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for GeoJSON objects that provides the required annotation
 * to map to the correct sub type via the {@code type} property.
 *
 * @see <a href="https://geojson.org/geojson-spec.html#geojson-objects">GeoJSON specification</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = PointImpl.class, name = "Point")})
public interface GeoJSON {
}
