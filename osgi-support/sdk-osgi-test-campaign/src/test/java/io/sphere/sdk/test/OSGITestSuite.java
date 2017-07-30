package io.sphere.sdk.test;

import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.extensions.cpsuite.ClasspathSuite.SuiteTypes;
import org.junit.runner.RunWith;
import org.ops4j.pax.logging.internal.DefaultServiceLog;

import static org.junit.extensions.cpsuite.SuiteType.TEST_CLASSES;
import static org.junit.extensions.cpsuite.ClasspathSuite.*;

/**
 * This is a test suite case that reruns all the tests but in an OSGI container
 */
@RunWith(PaxExamClasspathSuite.class)
@SuiteTypes(TEST_CLASSES)
@ClassnameFilters({"!io.sphere.sdk.subscriptions.*", "io.sphere.sdk.*"})
@IncludeJars(true)
public class OSGITestSuite {
    static {
        System.setProperty("org.ops4j.pax.logging.DefaultServiceLog.level","WARN");
    }


}
