package io.sphere.sdk.models;

/**
 * This interface a hook for enhancing constructors in generated classes with additional
 * initialization code.
 */
public interface PostConstructHook<T> {
    /**
     * This method is called at the end of a generated constructor.
     *
     * @param instance the instance
     */
    void postConstruct(T instance);
}
