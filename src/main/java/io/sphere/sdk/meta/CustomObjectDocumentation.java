package io.sphere.sdk.meta;

/**
<p>Custom objects are a way to store arbitrary JSON-formatted data on the SPHERE.IO platform.
 It allows you to persist data that does not fit our standard data model.</p>

 A {@link io.sphere.sdk.customobjects.CustomObject} contains an ID, a version, timestamps
 like the other resources in sphere and in addition {@link io.sphere.sdk.customobjects.CustomObject#getContainer()},
 {@link io.sphere.sdk.customobjects.CustomObject#getKey()} and {@link io.sphere.sdk.customobjects.CustomObject#getValue()}.

 <p>Container and key are namespaces like in a key-value store. The value is a JSON structure
 which can hold custom domain models.</p>

 Instead of pure JSON it is encouraged to use Java objects with the Jackson JSON mapper.

 <h3 id=pojo-mapping>Custom Objects with POJO JSON mapping</h3>

 <p>Custom objects can be used with custom Java classes and the Jackson mapper used by the SDK.</p>



 The following class contains a constructor with all field types and names a parameter for deserialization and getters for serialization.

 {@include.example io.sphere.sdk.customobjects.demo.Foo}

 <h4 id=pojo-create-custom-objects>Create Custom Objects</h4>

 Create them with a {@link io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand}:

 {@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

 <h4 id=pojo-update-custom-objects>Update Custom Objects</h4>

 Herefor you use also {@link io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand}.

 For update operations you can use {@link io.sphere.sdk.customobjects.CustomObjectDraft#ofUnversionedDraft(io.sphere.sdk.customobjects.CustomObject, Object, com.fasterxml.jackson.core.type.TypeReference)}
 to use optimistic concurrency control as in here:

 {@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandTest#updateWithVersion()}

 Or you can just override the value using {@link io.sphere.sdk.customobjects.CustomObjectDraft#ofUnversionedDraft(io.sphere.sdk.customobjects.CustomObject, Object, com.fasterxml.jackson.core.type.TypeReference)}

 {@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandTest#updateWithoutVersion()}

 <h3 id=pure-json-mapping>Custom objects with direct Jackson JSON</h3>

 <h4 id=direct-json-creation>Create and update custom objects</h4>

 {@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandTest#pureJson()}

 <h4 id=direct-json-fetch>Fetch custom objects</h4>

 {@include.example io.sphere.sdk.customobjects.queries.CustomObjectFetchByKeyTest#executionForPureJson()}

 <h4 id=direct-json-query>Query custom objects</h4>

 {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryTest#queryPureJson()}

 <h3 id=custom-json-mapper>Custom Objects with a custom JSON mapper</h3>

 <p>If you need to create your own models and prefer another JSON tool over Jackson you can use it at your own risk.</p>
 <h4>The models for the demo</h4>
 {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectDraft}
 {@include.example io.sphere.sdk.customobjects.demo.GsonFoo}

 <h4>Create and update with a custom JSON mapper</h4>
 <p>Extend {@link io.sphere.sdk.customobjects.commands.CustomObjectCustomJsonMappingUpsertCommand} to create or update a custom object with <a href="https://code.google.com/p/google-gson/">Google Gson</a>:</p>

 {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectUpsertCommand}

 <h4>Fetch by container and key</h4>

 {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectFetchByKey}
 {@include.example io.sphere.sdk.customobjects.queries.CustomObjectCustomJsonMappingFetchByKeyTest#execution()}

 <h3 id=increment-example>Using Optimistic Concurrency Control</h3>

 <p>In this example we want to create unique readable IDs (successive numbers)
 for customers since SPHERE.IO returns IDs like 8547b810-9dad-11d1-80b4-44c04fd430c8.</p>

 The data model contains the last number and the last sphere ID (just in there to have more than one field).

 {@include.example io.sphere.sdk.customobjects.occexample.CustomerNumberCounter}

 <p>Image the customer number should start at 1000 so we do the following request for initialization of the counter:</p>

 {@include.example io.sphere.sdk.customobjects.occexample.FlowTest#setupInitialValue()}


 */
public final class CustomObjectDocumentation {
    private CustomObjectDocumentation() {
    }
}
