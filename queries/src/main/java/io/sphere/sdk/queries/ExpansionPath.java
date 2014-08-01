package io.sphere.sdk.queries;

/**
 * An entry for reference expansion.
 */
public interface ExpansionPath {

    /**
     * Returns a SPHERE.IO reference expansion path expression.
     * @return String with unescaped expand path expression.
     */
    String toSphereExpand();
}
