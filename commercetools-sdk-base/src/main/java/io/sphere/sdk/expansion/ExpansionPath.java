package io.sphere.sdk.expansion;

/**
 * An entry for reference expansion.
 *
 * Equality for instances should only depend on the complete generates expansion path as String.
 */
public interface ExpansionPath<I> {

    /**
     * Returns a platform reference expansion path expression.
     * @return String with unescaped expand path expression.
     */
    String toSphereExpand();

    static <T> ExpansionPath<T> of(final String sphereExpansionPathExpression) {
        return new SimpleExpansionPath<>(sphereExpansionPathExpression);
    }
}
