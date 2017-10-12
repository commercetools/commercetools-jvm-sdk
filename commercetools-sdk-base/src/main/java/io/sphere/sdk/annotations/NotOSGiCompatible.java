package io.sphere.sdk.annotations;

import java.lang.annotation.*;

/**
 * This annotation marks the classes that are selected to run outside an OSGi container
 * either for latency reason or for dependency issue ...,the runner used in that case
 * is {@link org.junit.runners.BlockJUnit4ClassRunner}
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface NotOSGiCompatible {
}
