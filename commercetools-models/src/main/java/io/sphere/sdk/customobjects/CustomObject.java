package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customobjects.queries.CustomObjectQueryModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceView;

/**
  Custom objects are a way to store arbitrary JSON-formatted data on the platform. It allows you to persist data that does not fit our standard data model.

  The storage can be seen as key value store but with an additional prefix called container to improve querying custom objects and provide an additional namespace.



<hr>

A {@link io.sphere.sdk.customobjects.CustomObject} contains an ID, a version, timestamps
like the other resources in the platform and in addition {@link io.sphere.sdk.customobjects.CustomObject#getContainer()},
{@link io.sphere.sdk.customobjects.CustomObject#getKey()} and {@link io.sphere.sdk.customobjects.CustomObject#getValue()}.

<p>Container and key are namespaces like in a key-value store. The value is a JSON structure
which can hold custom domain models.</p>

Instead of pure JSON it is encouraged to use Java objects (POJO) with the Jackson JSON mapper.

<h3 id=pojo-mapping>Custom Objects with POJO JSON mapping</h3>

<p>Custom objects can be used with custom Java classes and the Jackson mapper used by the SDK.</p>



The following class contains a constructor with all field types and names a parameter for deserialization and getters for serialization.

{@include.example io.sphere.sdk.customobjects.demo.Foo}

<h4 id=pojo-create-custom-objects>Create Custom Objects</h4>

Create them with a {@link io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand}:

{@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

<h4 id=pojo-update-custom-objects>Update Custom Objects</h4>

In this case you use also {@link io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand}.

For update operations you can use {@link io.sphere.sdk.customobjects.CustomObjectDraft#ofVersionedUpdate(io.sphere.sdk.customobjects.CustomObject, Object, com.fasterxml.jackson.core.type.TypeReference)}
to use optimistic concurrency control as in here:

{@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandIntegrationTest#updateWithVersion()}

Or you can just override the value using {@link io.sphere.sdk.customobjects.CustomObjectDraft#ofUnversionedUpdate(io.sphere.sdk.customobjects.CustomObject, Object, com.fasterxml.jackson.core.type.TypeReference)}

{@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandIntegrationTest#updateWithoutVersion()}

<h3 id=pure-json-mapping>Custom objects with direct Jackson JsonNode</h3>

<h4 id=direct-json-creation>Create and update custom objects</h4>

{@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandIntegrationTest#pureJson()}

<h4 id=direct-json-fetch>Fetch custom objects</h4>

{@include.example io.sphere.sdk.customobjects.queries.CustomObjectByKeyGetIntegrationTest#executionForPureJson()}

<h4 id=direct-json-query>Query custom objects</h4>

{@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryIntegrationTest#queryPureJson()}

<h3 id=custom-json-mapper>Custom Objects with a custom JSON mapper</h3>

<p>If you need to create your own models and you prefer another JSON tool over Jackson you can use it at your own risk.</p>
<h4>The models for the demo</h4>
{@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectDraft}
{@include.example io.sphere.sdk.customobjects.demo.GsonFoo}

<h4>Create and update with a custom JSON mapper</h4>
<p>Extend {@link io.sphere.sdk.customobjects.commands.CustomObjectCustomJsonMappingUpsertCommand} to create or update a custom object with <a href="https://code.google.com/p/google-gson/">Google Gson</a>:</p>

{@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectUpsertCommand}

<h4>Fetch by container and key</h4>

{@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectByKeyGet}
{@include.example io.sphere.sdk.customobjects.queries.CustomObjectCustomJsonMappingByKeyGetIntegrationTest#execution()}

<h3 id=increment-example>Using Optimistic Concurrency Control</h3>

<p>In this example we want to create unique readable IDs (successive numbers)
for customers since the platform returns IDs like 8547b810-9dad-11d1-80b4-44c04fd430c8.</p>

The data model contains the last number and the last platform customer ID (just to have more than one field).

{@include.example io.sphere.sdk.customobjects.occexample.CustomerNumberCounter}

<p>Imagine, the customer number should start at 1000, so we do the following request for the initialization of the counter:</p>

{@include.example io.sphere.sdk.customobjects.occexample.FlowIntegrationTest#setupInitialValue()}

<p>An update works this way then:</p>
{@include.example io.sphere.sdk.customobjects.occexample.FlowIntegrationTest#doAnUpdate()}

<h3 id=migrations>Migrations</h3>

<p>Your data model in the custom objects probably will evolve and you should plan for it.</p>

<h4 id=migrations-additional-fields>Adding fields</h4>
For new fields you can add an {@link java.util.Optional} to make it obvious to callers that this value can be unset.
<p>Before adding the field (there are already saved custom objects):</p>

{@include.example io.sphere.sdk.customobjects.migrations.version1.Xyz}

<p>After adding the field:</p>

{@include.example io.sphere.sdk.customobjects.migrations.version2.Xyz}

{@include.example io.sphere.sdk.customobjects.migrations.CustomObjectsMigrationsIntegrationTest#optionalExample()}

<h4 id=migrations-removing-fields>Removing fields</h4>

<p>Removing fields can be equivalent to ignoring fields.</p>

Consider you have a class with a field "foo":

{@include.example io.sphere.sdk.customobjects.migrations.version2.Xyz}

If you don't want to use it anymore, remove it from the JSON mapping:

{@include.example io.sphere.sdk.customobjects.migrations.version3.Xyz}

Next time you'll save this object the field will be no more in the JSON.


<h4 id=migrations-moving-data>Moving data</h4>
Suppose you have this data model:

{@include.example io.sphere.sdk.customobjects.migrations.version1.Uvw}

And you want to make 'foo' not a String, but an object with members 'a' and 'b'.

{@include.example io.sphere.sdk.customobjects.migrations.version2.Uvw}

And let's say, for simplicity you can directly migrate the String to the object by splitting the String the right way.

A possible solution is to create an interface for that purpose:

{@include.example io.sphere.sdk.customobjects.migrations.version3.Uvw}

For the two incarnations you create a class for each. The newest class contains all the field plus the schema version and it implements the interface:

{@include.example io.sphere.sdk.customobjects.migrations.version3.UvwSchemaVersion2}

The previous data model versions contain no fields but extend the most recent class. The old versions get the old data
as constructor parameters and pass the transformed values to the constructor of the superclass.

{@include.example io.sphere.sdk.customobjects.migrations.version3.UvwSchemaVersion1}

As a result you only work with the interface making it transparent if it is an old or new version:

{@include.example io.sphere.sdk.customobjects.migrations.CustomObjectsMigrationsIntegrationTest#exampleForMigrationCall()}


<h3 id="traps">Traps</h3>

<p>Unlike other query models, {@link CustomObjectQueryModel#of()} takes a type parameter of the result type of the custom object.
Notice that it is necessary to explicitly declare the type when requesting the query model, as shown in the following examples:</p>
{@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryIntegrationTest#demoModelTypeParameter()}

<a href="#page-toc" class="theme-btn">back to top</a>

 @param <T> The type of the value of this custom object.
 */
@JsonDeserialize(as = CustomObjectImpl.class)
public interface CustomObject<T> extends ResourceView<CustomObject<T>, CustomObject<JsonNode>>, Referenceable<CustomObject<JsonNode>> {

    /**
     * The container is part of the custom object namespace to find it
     * @return container
     */
    String getContainer();

    /**
     * The key is part of the custom object namespace to find it
     * @return container
     */
    String getKey();

    /**
     * The value stored in the custom object.
     * @return the value
     */
    T getValue();

    @Override
    default Reference<CustomObject<JsonNode>> toReference() {
        return Reference.of(referenceTypeId(), getId());//not possible to provide filled reference since type can be different
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "key-value-document";
    }

    static String validatedKey(final String key) {
        return CustomObjectImpl.validatedKey("key", key);
    }

    static String validatedContainer(final String container) {
        return CustomObjectImpl.validatedKey("container", container);
    }
}
