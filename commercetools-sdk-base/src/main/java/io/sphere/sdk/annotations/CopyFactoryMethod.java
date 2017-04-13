package io.sphere.sdk.annotations;

/**
 * This annotation allows to add a copy factory method for the given {@link #value()}.
 *
 * A typical use case is to copy/convert a resource value (e.g. a Category) to a
 * corresponding resource draft vale (e.g. a CategoryDraft).
 */
public @interface CopyFactoryMethod {
    /**
     * Specifies the type for which an additional copy fectory method should be created.
     *
     * @return the template type
     */
    Class value();
}
