package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;

@ResourceValue
@ResourceInfo(pluralName = "subscriptions", pathElement = "subscriptions")
@HasQueryEndpoint
@HasByIdGetEndpoint(javadocSummary = "Fetches a subscription by id.")
// includeExamples = "io.sphere.sdk.shoppinglists.queries.ShoppingListByIdGetIntegrationTest#byIdGet()")
@HasByKeyGetEndpoint(javadocSummary = "Fetches a subscription by key.")
public interface Subscription extends Resource<ShoppingList>, Custom {

    @Nullable
    String getKey();

    Destination getDestination();

    @Nullable
    @Override
    CustomFields getCustom();
}
