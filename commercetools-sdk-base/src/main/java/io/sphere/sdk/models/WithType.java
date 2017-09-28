package io.sphere.sdk.models;

/**
 *
 *
 * when performing deserialization of a json document that maps to multiple classes with similar attributes, errors occurs due to mapping ambiguity.
 * in this case adding an attribute "type" help to distinguish to wish class the json object should map,
 * <p>This interface qualify such classes since it provides the <code>getType()</code> method </p>
 *
 *
 */
public interface WithType {

    String getType();

}
