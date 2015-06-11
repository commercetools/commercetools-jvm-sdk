package io.sphere.sdk.meta;

/**
 <h3 id="intro">Introduction</h3>
 <h3 id="product-type-creation">ProductType Creation</h3>
 <p>A {@link io.sphere.sdk.producttypes.ProductType} is like a schema how the product attributes are structured.</p>
 <p>{@link io.sphere.sdk.producttypes.ProductType}s contain a list of the attribute name and the corresponding type of the attribute.
 In one SPHERE.IO project the name type pair is global, so if you create an attribute "foo" of type String, you cannot create
 another {@link io.sphere.sdk.producttypes.ProductType} where "foo" has another type like {@link io.sphere.sdk.models.LocalizedStrings}. If you do it anyway you get an error message like:</p>

<pre>"The attribute with name 'foo' has a different type on product type 'exampleproducttype'."</pre>

 <p>{@link io.sphere.sdk.producttypes.ProductType}s have a name (String)
 which can be used as key to logically identify {@link io.sphere.sdk.producttypes.ProductType}s. Beware that the name has no unique constraint,
 so it is possible to create multiple product types with the same name which will cause confusion.
 Before creating a product type it's better to check if it already exists:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#demoCheckingIfProductTypeExist()}

<!--

 -->

 <h3 id="product-creation">Product Creation</h3>
 <h3 id="attribute-access-without-product-type">Reading Attributes</h3>
 <h3 id="attribute-table-creation">Creating a table of attributes</h3>
 <h3 id="attribute-update">Update attributes in a product</h3>
 <h3 id="attribute-stubs-for-tests">Create attribute stubs for unit tests</h3>
 <h3 id="attributes-in-order-import">Create attributes for importing orders</h3>
 <h3 id="nested-attributes">Nested attributes (experimental)</h3>
 */
public class ProductAttributeDocumentation {
    private ProductAttributeDocumentation() {
    }
}
