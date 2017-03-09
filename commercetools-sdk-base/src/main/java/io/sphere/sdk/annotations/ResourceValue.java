package io.sphere.sdk.annotations;

import io.sphere.sdk.models.Base;

/**
 * This annotation marks an interface as a resource representation.
 */
public @interface ResourceValue {
    /**
     * If set to true, the generated resource value class will be abstract so that it can be further customized.
     *
     * @return true iff. the generated class should be abstract
     */
    boolean abstractResourceClass() default false;

    /**
     * This property allows to specify the base class of the generated resource value class.
     *
     * @return the class that the generated class should extend
     */
    Class<? extends Base> baseClass() default Base.class;
}
