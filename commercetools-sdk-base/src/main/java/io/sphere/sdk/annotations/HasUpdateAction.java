package io.sphere.sdk.annotations;


import java.lang.annotation.*;

@Repeatable(HasCustomUpdateActions.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface HasUpdateAction {


    /**
     * Used to specify the action name
     * @return the action name
     */
    String value() default "";

    String className() default "";

    PropertySpec[] fields() default {};

    /**
     * This adds a {code @include.example <i>exampleBaseClass</i>#<i>updateActionTestName</i>()} javadoc tag
     * to all generated update actions.
     * <p>
     * Where {@code <i>updateActionTestName</i>} is derived by un-capitalizing the update action name
     * (e.g. for a {@code SetKey} update action the <i>updateActionTestName</i> is {@code setKey}.
     * <p>
     * This follows our convention to have integration tests for all of our update actions.
     *
     * @return the example base class name
     */

    String exampleBaseClass() default "";
    FactoryMethod[] factoryMethods() default {};
    CopyFactoryMethod[] copyFactoryMethods() default {};
    Class[] superInterfaces() default {};
    boolean makeAbstract() default false;


}
