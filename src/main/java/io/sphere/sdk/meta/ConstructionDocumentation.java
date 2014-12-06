package io.sphere.sdk.meta;

/**
 * This documentation is about creating objects locally and in SPHERE.IO.
 *
 * SPHERE.IO saves e-commerce data in the cloud. The JVM SDK is one way to read and write the data.
 * There can be other tools like the Merchant Center or product sync tools which also can change data.
 * As a result the data loaded with the JVM SDK is a record of the present or the
 * past since directly after loading the data another tool might changed the data.
 * Therefore <strong>data</strong> containers are <strong>immutable</strong> and contain <strong>no setters</strong>.
 *
 *  <h3 id=static-of-method>Static of method</h3>
 *  For flexibility in the implementation the SDK mostly don't let objects be instantiated with the {@code new} operator.
 *
 *  Simple objects with few optional parameters have typically a static method called {@code of} to create an object.
 *
 *  <p> For example use {@link io.sphere.sdk.models.LocalizedStrings#of(java.util.Locale, String, java.util.Locale, String)}
 *   to create a translation for different locales:</p>
 *
 *   {@include.example io.sphere.sdk.meta.ConstructionDocumentationTest#ofMethodExample()}
 *  <h3 id=builders>Builders</h3>
 *
 *  For objects with a lot of optional parameters the SDK provides builders. The builder is initialized with a
 *  static of method and the mandatory parameters for creating the target object. The builder has methods
 *  to fill each optional field of the target object.
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
 *  <!--
 *  show withMethods, address.withCity(), Price
 *  -->
 *  <h3 id=create-commands>Create commands</h3>
 *
 */
public class ConstructionDocumentation {
    private ConstructionDocumentation() {
    }
}
