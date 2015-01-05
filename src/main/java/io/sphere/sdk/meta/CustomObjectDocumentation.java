package io.sphere.sdk.meta;

/**
<p>Custom objects are a way to store arbitrary JSON-formatted data on the SPHERE.IO platform.
 It allows you to persist data that does not fit our standard data model.</p>

<h3 id=data-model>Data model</h3>
 A {@link io.sphere.sdk.customobjects.CustomObject} contains an ID, a version, timestamps
 like the other resources in sphere and in addition {@link io.sphere.sdk.customobjects.CustomObject#getContainer()},
 {@link io.sphere.sdk.customobjects.CustomObject#getKey()} and {@link io.sphere.sdk.customobjects.CustomObject#getValue()}.

 <p>Container and key are namespaces like in a key-value store. The value is a JSON structure
 which can hold custom domain models.</p>

 Instead of pure JSON it is encouraged to use Java objects with the Jackson JSON mapper.

 The following class contains a constructor with all field types and names a parameter for deserialization and getters for serialization.

 {@include.example io.sphere.sdk.customobjects.demo.Foo}

 <h3 id=create-custom-objects>Create Custom Objects</h3>

 Create them with a {@link io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand}:

 {@include.example io.sphere.sdk.customobjects.CustomObjectFixtures#createCustomObject()}

 <h3 id=update-custom-objects>Update Custom Objects</h3>

 Herefor you use also {@link io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand}.

 For update operations you can use {@link io.sphere.sdk.customobjects.CustomObjectDraft#ofUnversionedDraft(io.sphere.sdk.customobjects.CustomObject, Object, com.fasterxml.jackson.core.type.TypeReference)} )}
 to use optimimistic concurrency control as in here:

 {@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandTest#updateWithVersion()}

 Or you can just override the value using {@link io.sphere.sdk.customobjects.CustomObjectDraft#ofUnversionedDraft(io.sphere.sdk.customobjects.CustomObject, Object, com.fasterxml.jackson.core.type.TypeReference)}

 {@include.example io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommandTest#updateWithoutVersion()}

 */
public final class CustomObjectDocumentation {
    private CustomObjectDocumentation() {
    }
}
