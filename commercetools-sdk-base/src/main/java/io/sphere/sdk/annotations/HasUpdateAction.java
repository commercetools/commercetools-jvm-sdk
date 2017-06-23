package io.sphere.sdk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This interface will be used for action generation
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface HasUpdateAction {

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


    /**
     * Used to specify the action name
     * @return the action name
     */
    String value() default "";

    /**
     * This attribute can be used to specify a custom action name rather than the auto generated
     * @return the custom action class name
     */
    String actionClassName() default "";

    /**
     * specify the json name of the property of the attribute in the action class
     * @return json property name
     */
    String jsonPropertyName() default "";

}
