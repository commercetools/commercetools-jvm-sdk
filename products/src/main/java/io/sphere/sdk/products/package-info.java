/**
 * Products are the sellable goods in an e-commerce project on SPHERE.IO.
 *
 * <h3 id="create-product">Create a Product</h3>
 *
 * A {@link io.sphere.sdk.products.Product} must belong to a {@link io.sphere.sdk.producttypes.ProductType},
 * so you need to <a href="../producttypes/package-summary.html#create-product-types">create a product type</a> first.
 *
 * Products can be created in the backend by executing a {@link io.sphere.sdk.products.commands.ProductCreateCommand}:
 *
 * {@include.example example.CreateProductExamples#createWithClient()}
 *
 * {@link io.sphere.sdk.products.commands.ProductCreateCommand} requires one instance of {@link io.sphere.sdk.products.NewProduct}
 * as constructor parameter. For type-safe attribute setting consult also {@link io.sphere.sdk.attributes.AttributeGetterSetter}.
 * A product can be created with {@link io.sphere.sdk.products.NewProductBuilder}:
 *
 * {@include.example io.sphere.sdk.suppliers.SimpleCottonTShirtNewProductSupplier}
 *
 * <h3 id="query-product">Query Products</h3>
 *
 * Use {@link io.sphere.sdk.products.queries.ProductQueryModel} to query for products:
 *
 * {@include.example io.sphere.sdk.suppliers.ByEnglishNameProductQuerySupplier}
 *
 * <h3 id="update-product">Update a Product</h3>
 * <h3 id="delete-product">Delete a Product</h3>
 *
 * Use {@link io.sphere.sdk.products.commands.ProductDeleteByIdCommand} to delete a product:
 *
 * {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
package io.sphere.sdk.products;