package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.taxcategories.TaxCategory;

final class TaxCategoriesEndpoint {
    static JsonEndpoint<TaxCategory> ENDPOINT = JsonEndpoint.of(TaxCategory.typeReference(), "/tax-categories");
}
