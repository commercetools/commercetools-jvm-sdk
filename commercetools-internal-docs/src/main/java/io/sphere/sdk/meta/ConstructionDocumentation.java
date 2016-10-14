package io.sphere.sdk.meta;

import io.sphere.sdk.models.LocalizedString;

/**
 * This documentation is about creating objects.
 *
 * The commercetools platform saves e-commerce data in the cloud. The JVM SDK is one way to read and write the data.
 * There can be other tools like the Merchant Center or product sync tools which can also change data.
 * As a result the data loaded with the JVM SDK is a record of the present or the
 * past since another tool might have changed the data just after loading the data .
 * Therefore, <strong>data</strong> containers are <strong>immutable</strong> and contain <strong>no setters</strong>.
 *
 *  <h3 id=static-of-method>Static 'of' method</h3>
 *  To achieve flexibility in the implementation the SDK mostly doesn't support object instantiation by the {@code new} operator.
 *
 *  Simple objects with few optional parameters typically have a static method called {@code of} to create an object.
 *
 *  <p> For example use {@link LocalizedString#of(java.util.Locale, String, java.util.Locale, String)}
 *   to create a translation for different locales:</p>
 *
 *   {@include.example io.sphere.sdk.meta.ConstructionDocumentationTest#ofMethodExample()}
 *
 *   <p>The {@code of} method is inspired by {@link java.util.Optional#of(Object)} and the Java date and time classes such as {@link java.time.format.DateTimeFormatter#ofPattern(String)}.</p>
 *
 *
 *  <h3 id=builders>Builders</h3>
 *
 *  For objects with a lot of optional parameters the SDK provides builders. The builder is initialized with a
 *  static {@code of} method and the mandatory parameters for creating the target object. The builder provides methods
 *  to fill each target object's optional field.
 *  To create the target object a method called {@link io.sphere.sdk.models.Builder#build()} has to be executed.
 *
 *  <p>The {@link io.sphere.sdk.models.Address} object with the {@link io.sphere.sdk.models.AddressBuilder}
 *  is a good example since it contains only the country as mandatory parameter and a lot of optional fields:</p>
 *
 *  {@include.example io.sphere.sdk.meta.ConstructionDocumentationTest#builderExample()}
 *
 * <p>The invariants of builders in the SDK:</p>
 *  {@include.example io.sphere.sdk.meta.ConstructionDocumentationTest#builderIsMutableExample()}
 *
 * <p>Some builders support a model as prototype:</p>
 * {@include.example io.sphere.sdk.meta.ConstructionDocumentationTest#builderWithTemplateInput()}
 *
 *  <h3 id=copy-method>Copy methods</h3>
 *
 *  Sometimes you need to clone objects and to update one field since most objects are immutable;
 *  you can create updated copies with a method starting with "with":
 *
 *  {@include.example io.sphere.sdk.meta.ConstructionDocumentationTest#possibilities1()}
 *
 *  The 'with copy'-methods are inspired by Scala and the Java date and time API: {@link java.time.OffsetDateTime#withMinute(int)}.
 *
 *  <h3 id=persisting-changes>Persisting Changes</h3>
 *
 *
 *  <p>For persistent changes you need to use a commercetools platform
 *  client and to execute a {@link io.sphere.sdk.commands.Command}. Available commands for the commercetools platform resources
 *  are listed in {@link io.sphere.sdk.meta.SphereResources}</p>
 *
 *  Typically there are three kinds of commands:
 *
 *  <ul>
 *      <li>{@link io.sphere.sdk.commands.CreateCommand}: command to create a resource</li>
 *      <li>{@link io.sphere.sdk.commands.UpdateCommand}: command to update an existing resource</li>
 *      <li>{@link io.sphere.sdk.commands.DeleteCommand}: command to delete resources</li>
 </ul>
 *
 */
public final class ConstructionDocumentation {
    private ConstructionDocumentation() {
    }
}
