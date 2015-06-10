package io.sphere.sdk.queries;

/**
 * An entry for reference expansion.
 */
public interface ExpansionPath<I> {

    /**
     * Returns a SPHERE.IO reference expansion path expression.
     * @return String with unescaped expand path expression.
     */
    String toSphereExpand();

    static <T> ExpansionPath<T> of(final String sphereExpansionPathExpression) {
        return new SimpleExpansionPath<>(sphereExpansionPathExpression);
    }
}
