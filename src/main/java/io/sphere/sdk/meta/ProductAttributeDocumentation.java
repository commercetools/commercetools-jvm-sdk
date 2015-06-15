package io.sphere.sdk.meta;

import io.sphere.sdk.products.ProductVariantDraftBuilder;

/**
 <h3 id="intro">Introduction</h3>
 <h3 id="product-type-creation">ProductType Creation</h3>
 <p>A {@link io.sphere.sdk.producttypes.ProductType} is like a schema how the product attributes are structured.</p>
 <p>{@link io.sphere.sdk.producttypes.ProductType}s contain a list of the attribute name and the corresponding type of the attribute.
 In one SPHERE.IO project the name type pair is global, so if you create an attribute "foo" of type String, you cannot create
 another {@link io.sphere.sdk.producttypes.ProductType} where "foo" has another type like {@link io.sphere.sdk.models.LocalizedStrings}. If you do it anyway you get an error message like:</p>

<pre>"The attribute with name 'foo' has a different type on product type 'exampleproducttype'."</pre>

 <p>In this scenario we provide a {@link io.sphere.sdk.producttypes.ProductType} tshirt with the following attributes:</p>

 <ul>
    <li>color: {@link io.sphere.sdk.models.LocalizedEnumValue}</li>
    <li>size: {@link io.sphere.sdk.models.PlainEnumValue}</li>
    <li>laundrySymbols: set of {@link io.sphere.sdk.models.LocalizedEnumValue}</li>
    <li>matchingProducts: set of product {@link io.sphere.sdk.models.Reference}s</li>
    <li>rrp: {@link javax.money.MonetaryAmount}</li>
    <li>availableSince: {@link java.time.LocalDateTime}</li>
 </ul>



 <p>The possible attribute types you can find here: {@link io.sphere.sdk.attributes.AttributeType}.</p>

<p>The code for the creation of the {@link io.sphere.sdk.producttypes.ProductType}:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#createProductType()}

 <p>{@link io.sphere.sdk.producttypes.ProductType}s have a name (String)
 which can be used as key to logically identify {@link io.sphere.sdk.producttypes.ProductType}s. Beware that the name has no unique constraint,
 so it is possible to create multiple product types with the same name which will cause confusion.
 Before creating a product type it's better to check if it already exists:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#demoCheckingIfProductTypeExist()}





 <h3 id="product-creation">Product Creation</h3>

 <p>To create a product you need the product type. Since the {@link io.sphere.sdk.producttypes.ProductType}
 ID of the development system will not be the ID of the production system it is necessary to find the product type by name:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#fetchProductTypeByName()}

 The simplest way of adding attributes to a {@link io.sphere.sdk.products.ProductVariant} is to use
 {@link ProductVariantDraftBuilder#plusAttribute(java.lang.String, java.lang.Object)} which enables you to directly
 put the value of the attribute to the draft. But it cannot check if you put the right objects and types in it.

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#createProduct()}

 A wrong value for a field or an invalid type will cause an {@link io.sphere.sdk.client.ErrorResponseException}
 with an error code of {@value io.sphere.sdk.models.errors.InvalidField#CODE}.

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#invalidTypeCausesException()}

 As Alternative you could declare the types of your attributes at one place and use these to read and write
 attribute values:

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#productCreationWithGetterSetter()}


 <h3 id="attribute-access-without-product-type">Reading Attributes</h3>

 <p>To get a value out of an attribute you need an instance of {@link io.sphere.sdk.attributes.AttributeAccess}
 which keeps the type info to deserialize the attribute.</p>

 <p>You can reuse the {@link io.sphere.sdk.attributes.NamedAttributeAccess} declaration if you want to:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithNamedAccess()}

 <p>Or you can access it on the fly:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithName()}

 <p>Or you can access it as {@link com.fasterxml.jackson.databind.JsonNode}, for example if you don't know the type or the SDK does not support it yet:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithJson()}


 <p>If the attribute is not present then the {@link java.util.Optional} will be empty:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#notPresentAttributeRead()}

 <p>If you provide a wrong type, the code will throw a {@link io.sphere.sdk.json.JsonException}:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithNamedAccessWithWrongType()}



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
