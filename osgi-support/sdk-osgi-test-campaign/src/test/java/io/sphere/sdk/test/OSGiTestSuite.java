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
        "!io.sphere.sdk.subscriptions.*",
        "!io.sphere.sdk.products.search.*",
        "!io.sphere.sdk.reviews.___ReviewProductProjectionSearchIntegrationTest",
        "!io.sphere.sdk.products.queries.___SuggestQueryIntegrationTest",
        "!io.sphere.sdk.products.___ProductCategoryOrderHintIntegrationTest",
        "!io.sphere.sdk.payments.commands.___ProductCreateCommandIntegrationTest",
        "!io.sphere.sdk.products.commands.___ProductCreateCommandIntegrationTest",
        "!io.sphere.sdk.queries.___IsInQueryPredicateTest",
        "!io.sphere.sdk.search.___PagedSearchResultTest",
        "!io.sphere.sdk.client.___TokensSupplierTest",
        "!io.sphere.sdk.client.___SphereClientTest",
        "io.sphere.sdk.*"
})
@IncludeJars(true)
@MinimumTestClassesInSuite(250)
public class OSGiTestSuite {

}
