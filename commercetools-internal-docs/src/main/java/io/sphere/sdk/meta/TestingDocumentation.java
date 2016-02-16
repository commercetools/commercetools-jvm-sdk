package io.sphere.sdk.meta;

/**
 <h3 id=client-test-doubles>Client test doubles for unit tests</h3>

 <p>Since the clients are interfaces you can implement them to provide test doubles.</p>
 <p>Here are some example to provide fake client responses in tests:</p>

 {@include.example io.sphere.sdk.client.TestsDemo#withJson()}

 {@include.example io.sphere.sdk.client.TestsDemo#modelInstanceFromJson()}

 <h3 id="object-test-doubles">Detailed example for creating test doubles</h3>

 <h4>Go to impex playground and get some product data as String</h4>

 <img src="{@docRoot}/resources/images/testing/playground-query-product-projections.png" alt="API playground loading current product projections">

 So you have this:

 <script src="https://gist.github.com/schleichardt/5c54993cc13750543fb2.js"></script>

 And you can extract the data of one product:

 <script src="https://gist.github.com/schleichardt/f5008aff1a2b7ad661d0.js"></script>

 With a function like

 {@include.example io.sphere.sdk.client.ResourceUtil}

 you can obtain a String from a resource file in the classpath of the test and replace values optionally:

 {@include.example io.sphere.sdk.client.TestsDemo#replaceValuesInJsonStringDemo()}



 */
public final class TestingDocumentation {
    private TestingDocumentation() {
    }
}
