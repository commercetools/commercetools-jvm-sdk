package io.sphere.sdk.meta;

/**
 *
 * <h3>OSGi support</h3>
 * <ul>
 * <li>The JVM SDK is OSGi compatible, the module structure is as follows:
 * <ul>
 * <li>Bundle <code>sdk-http</code> responsible for http client features, this bundle has the following fragments
 * <ul>
 * <li>Fragment <code>sdk-htt-apache-async</code> which provide an implementation of the http clients.</li>
 * </ul>
 * </li>
 * <li>Bundle <code>commercetools-sdk-base</code> that contains the base types used for the sdk models, this bundle has the following fragments
 * <ul>
 * <li><code>commercetools-java-client-core</code></li>
 * <li><code>commercetools-java-client-apache-async</code> with the previous fragment, it allow to publish a service describing the http client implementation for our API.</li>
 * <li><code>commercetools-models</code> contains a description model of the commercetools backend and the different actions that alows interaction with it.</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * </ul>
 */
public final class OSGiSupportDocumentation {
    private OSGiSupportDocumentation() {
    }
}