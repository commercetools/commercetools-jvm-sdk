package io.sphere.sdk.test.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is added to check that hwen running a test suite it contains a minimum of tests,
 * this especially useful in the case where the classpath isnt resolved correctly that,
 * the suite has succeeded because no test was passed but not because all the tests succeeded
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MinimumTestClassesInSuite {
    /**
     *
     * @return the minimum number required in the suite
     */
    int value();

}
