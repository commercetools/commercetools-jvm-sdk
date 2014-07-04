/**
 * <p>{@link io.sphere.sdk.producttypes.ProductType}s are used to describe common characteristics, most importantly common custom attributes, of many concrete products.</p>
 *
 * <ol>
 *     <li><a href="#create-product-types">Create a ProductType</a></li>
 *     <li><a href="#query-product-types">Query ProductTypes</a></li>
 *     <li><a href="#update-product-types">Update a ProductType</a></li>
 *     <li><a href="#delete-product-types">Delete a ProductType</a></li>
 * </ol>
 *
 * <h3 id="create-product-types">Create a ProductType</h3>
 *
 * <p>{@link io.sphere.sdk.producttypes.ProductType}s can be created in the backend by executing a {@link io.sphere.sdk.producttypes.ProductTypeCreateCommand}:</p>
 *
 * {@include.example io.sphere.sdk.producttypes.io.sphere.sdk.example.CreateTShirtProductTypeExample#createBackend()}
 *
 * The required {@link io.sphere.sdk.producttypes.NewProductType} can be created this way:
 *
 * {@include.example io.sphere.sdk.producttypes.io.sphere.sdk.example.CreateTShirtProductTypeExample#createNewProductType()}
 *
 * For creating {@link io.sphere.sdk.producttypes.ProductType} instances for unit tests you can use {@link io.sphere.sdk.producttypes.ProductTypeBuilder}:
 *
 * {@include.example io.sphere.sdk.producttypes.io.sphere.sdk.example.CreateTShirtProductTypeExample#createProductTypeForUnitTest()}
 *
 *
 * <h3 id="query-product-types">Query ProductTypes</h3>
 * <h3 id="update-product-types">Update a ProductType</h3>
 * <h3 id="delete-product-types">Delete a ProductType</h3>
 *
 *
 *
 */
package io.sphere.sdk.producttypes;