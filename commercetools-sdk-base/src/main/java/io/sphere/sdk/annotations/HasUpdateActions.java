package io.sphere.sdk.annotations;

/**
 * This annotation is used to generate update actions for a resource - only for its primitive types.
 */
public @interface HasUpdateActions {
    /**
     * This adds a {code @include.example <i>exampleBaseClass</i>#<i>updateActionTestName</i>()} javadoc tag
     * to all generated update actions.
     *
     * Where {@code <i>updateActionTestName</i>} is derived by un-capitalizing the update action name
     * (e.g. for a {@code SetKey} update action the <i>updateActionTestName</i> is {@code setKey}.
     *
     * This follows our convention to have integration tests for all of our update actions.
     *
     * @return the example base class name
     */
    String exampleBaseClass() default "";

    /**
     * This allows to disable the generation of the {code @include.example <i>exampleBaseClass</i>#<i>updateActionTestName</i>()}
     * javadoc tag within the update actions.
     *
     * @return the example javadoc tag generation preference. If false, the tag is not created. By default it is true and the tag is created
     */
    boolean deriveExampleBaseClass() default true;
}
