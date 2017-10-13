package io.sphere.sdk.test;

import io.sphere.sdk.test.annotations.MinimumTestClassesInSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.extensions.cpsuite.ClasspathSuite.SuiteTypes;
import org.junit.runner.RunWith;

import static org.junit.extensions.cpsuite.SuiteType.TEST_CLASSES;
import static org.junit.extensions.cpsuite.ClasspathSuite.*;

/**
 * This is a test suite case that reruns all the tests but in an OSGI container
 */
@RunWith(PaxExamClasspathSuite.class)
@SuiteTypes(TEST_CLASSES)
@ClassnameFilters({
        "io.sphere.sdk.*___.*"
})
@IncludeJars(true)
@MinimumTestClassesInSuite(338)
public class OSGiTestSuite {

}
