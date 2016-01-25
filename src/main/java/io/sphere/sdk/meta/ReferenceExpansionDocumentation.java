package io.sphere.sdk.meta;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.expansion.ReferenceExpansionDsl;

/**
 <h3 id=introduction>Introduction</h3>
 Reference expansion is a feature of certain endpoints implementing {@link io.sphere.sdk.expansion.ReferenceExpansionSupport} that enables clients to request
 server-side expansion of Reference resources, thereby reducing the number of required
 client-server roundtrips to obtain the data that a client needs for a specific use-case.
 A query endpoint that supports reference expansion does so by providing an expand query
 parameter which can be used to specifiy one or more expansion paths.

 <p>For example, {@link io.sphere.sdk.customers.Customer} contains a member
 {@link Customer#getCustomerGroup()}, which may has a reference to a {@link io.sphere.sdk.customergroups.CustomerGroup}.

 <p>By default, the field {@link io.sphere.sdk.models.Reference#getObj()} will return null and also the reference itself can be null if the customer does not have a customer group.</p>

 {@include.example io.sphere.sdk.models.ReferenceIntegrationTest#referencesAreNotByDefaultExpanded()}

 <p>
 If you specify an expansion path with {@link ReferenceExpansionDsl#withExpansionPaths(java.util.List)}
 it is possible that the product type will be filled so that you can work with it:
 </p>

 {@include.example io.sphere.sdk.models.ReferenceIntegrationTest#howToExpandReferences()}

 <h3 id=guarantees>Guarantees</h3>
 Reference expansion is a feature without any guarantees. So, if you provide a mal-formatted
 reference expansion path, the platform will not inform you about the problem; but just fulfills the query.

 Even if the expansion path is correct, it is possible that it will not be expanded. This can happen, for example,
 if too many resources are expanded at the same time.

 <h3 id=meta-model-dsl>Use Expansion Models</h3>
 <p>To prevent you from constructing invalid paths, endpoints implementing {@link MetaModelReferenceExpansionDsl} provide methods to generate valid paths.</p>

 If you want to expand the product type of a product you can query like this:

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#expandProductTypeForProduct()}

 Expansion paths are not limited to the next element; for example, you can expand the
 categories for a product projection plus you can expand the parent category of the category you have expanded before:

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#expandCategoryAndCategoryParentForProduct()}

 <h3 id=expansion-path-by-string>Expansion Path by String</h3>
 If the SDK does not provide a type-safe way to construct a reference path, you can create it by string:

 {@include.example io.sphere.sdk.meta.QueryDocumentationTest#createExpansionPathByString()}


 */
public final class ReferenceExpansionDocumentation {
    private ReferenceExpansionDocumentation() {
    }
}
