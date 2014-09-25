/**
 * <p>{@link io.sphere.sdk.producttypes.ProductType}s are used to describe common characteristics, most importantly common custom attributes, of many concrete products.</p>
 *
 *
 * <h3 id="create-product-types">Create a ProductType</h3>
 *
 * <p>{@link io.sphere.sdk.producttypes.ProductType}s can be created in the backend by executing a {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}:</p>
 *
 * {@include.example example.CreateTShirtProductTypeExample#createBackend()}
 *
 * {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand} requires one instance of {@link io.sphere.sdk.producttypes.NewProductType}
 * as constructor parameter. It can be created by static factory methods:
 *
 * {@include.example suppliers.TShirtNewProductTypeSupplier}
 *
 * For creating {@link io.sphere.sdk.producttypes.ProductType} instances for unit tests you can use {@link io.sphere.sdk.producttypes.ProductTypeBuilder}:
 *
 * {@include.example example.CreateTShirtProductTypeExample#createProductTypeForUnitTest()}
 *
 * <p>To create attribute definitions refer to {@link io.sphere.sdk.attributes.AttributeDefinition}.</p>
 *
 * <h3 id="query-product-types">Query ProductTypes</h3>
 *
 * Query all product types:
 *
 * {@include.example example.QueryProductTypeExamples#queryAll()}
 *
 * Scenario to load a specific product type:
 *
 * {@include.example example.QueryByProductTypeNameExample}
 *
 * With {@link io.sphere.sdk.producttypes.queries.ProductTypeQueryModel} you can query for product types containing specific attributes:
 *
 * {@include.example example.QueryProductTypeExamples#queryByAttributeName()}
 *
 * <h3 id="update-product-types">Update a ProductType</h3>
 * <h3 id="delete-product-types">Delete a ProductType</h3>
 *
 * Use {@link io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand} to delete product types:
 *
 * {@include.example example.QueryProductTypeExamples#delete()}
 *
 */
package io.sphere.sdk.producttypes;