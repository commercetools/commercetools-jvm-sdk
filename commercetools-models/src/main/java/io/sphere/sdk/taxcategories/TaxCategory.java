package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.CartShippingInfo;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Tax Categories define how products are to be taxed in different countries.
 *
 * @see io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand
 * @see io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommand
 * @see io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand
 * @see io.sphere.sdk.taxcategories.queries.TaxCategoryByIdGet
 * @see io.sphere.sdk.taxcategories.queries.TaxCategoryQuery
 * @see CartShippingInfo#getTaxCategory()
 * @see io.sphere.sdk.orders.OrderShippingInfo#getTaxCategory()
 * @see CustomLineItem#getTaxCategory()
 * @see Product#getTaxCategory()
 * @see io.sphere.sdk.products.ProductProjection#getTaxCategory()
 * @see io.sphere.sdk.shippingmethods.ShippingMethod#getTaxCategory()
 */
@JsonDeserialize(as = TaxCategoryImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "\n" +
        "    default TaxCategoryQuery byName(final String name) {\n" +
        "        return withPredicates(m -> m.name().is(name));\n" +
        "    }\n" +
        "    \n" +
        "    default TaxCategoryQuery byKey(final String key) { \n" +
        "        return withPredicates(m -> m.key().is(key));\n" +
        "    }")
@ResourceInfo(pluralName = "tax categories", pathElement = "tax-categories")
@HasByIdGetEndpoint(javadocSummary = "Fetches a tax category by a known ID.", includeExamples = "io.sphere.sdk.taxcategories.queries.TaxCategoryByIdGetIntegrationTest#execution()")
@HasByKeyGetEndpoint(javadocSummary = "Fetches a tax category by a known Key.", includeExamples = "io.sphere.sdk.taxcategories.queries.TaxCategoryByKeyGetIntegrationTest#execution()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#createTaxCategory()")
@HasUpdateCommand
@HasUpdateActions
@HasDeleteCommand(
        deleteWith = "key",
        javadocSummary = "Deletes a tax category.")
@HasQueryModel
public interface TaxCategory extends Resource<TaxCategory>, WithKey {
    String getName();

    @Nullable
    @IgnoreInQueryModel
    String getDescription();

    @Nullable
    String getKey();

    /**
     * Gets the tax rates. Alias for {@link #getRates()}.
     *
     * @return rates
     */
    @IgnoreInQueryModel
    default List<TaxRate> getTaxRates() {
        return getRates();
    }

    @IgnoreInQueryModel
    List<TaxRate> getRates();

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @return type reference
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     */
    static TypeReference<TaxCategory> typeReference() {
        return new TypeReference<TaxCategory>() {
            @Override
            public String toString() {
                return "TypeReference<TaxCategory>";
            }
        };
    }

    @Override
    default Reference<TaxCategory> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     *
     * @return type hint
     * @see Reference#getTypeId()
     */
    static String referenceTypeId() {
        return "tax-category";
    }

    static String resourceTypeId() {
        return "tax-category";
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     * <p>
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     * <p>
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     * <p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<TaxCategory> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
