package io.sphere.sdk.test.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is added to check that when running a test suite, it contains a minimum of test classes,
 * this is especially useful in the case where the classpath isn't resolved correctly, because in that case
 * the suite  succeeds while no test has passed since there are no tests to run.
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MinimumTestClassesInSuite {
    /**
     *
     * @return the minimum number of test classes required in the suite
     */
    int value();

}
