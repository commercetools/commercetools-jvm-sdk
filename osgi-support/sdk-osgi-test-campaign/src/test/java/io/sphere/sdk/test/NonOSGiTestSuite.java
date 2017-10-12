package io.sphere.sdk.test;


import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.RunWith;

import static org.junit.extensions.cpsuite.SuiteType.TEST_CLASSES;

@RunWith(ClasspathSuite.class)
@ClasspathSuite.SuiteTypes(TEST_CLASSES)
@ClasspathSuite.ClassnameFilters({
        "io.sphere.sdk.subscriptions.*___.*",
        "io.sphere.sdk.products.search.*___.*",
        "io.sphere.sdk.reviews.___ReviewProductProjectionSearchIntegrationTest",
        "io.sphere.sdk.products.queries.___SuggestQueryIntegrationTest",
        "io.sphere.sdk.products.___ProductCategoryOrderHintIntegrationTest",
        "io.sphere.sdk.payments.commands.___ProductCreateCommandIntegrationTest",
        "io.sphere.sdk.products.commands.___ProductCreateCommandIntegrationTest",
        "io.sphere.sdk.queries.___IsInQueryPredicateTest",
        "io.sphere.sdk.search.___PagedSearchResultTest",
        "io.sphere.sdk.client.___TokensSupplierTest",
        "io.sphere.sdk.client.___SphereClientTest"
})
@ClasspathSuite.IncludeJars(true)
public class NonOSGiTestSuite {

}
