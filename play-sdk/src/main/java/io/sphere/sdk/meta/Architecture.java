package io.sphere.sdk.meta;

/**
 * Placeholder for the architecture description in Javadoc.
 *
 * <p>The JVM SDK consists of a lot of <a href="http://www.scala-sbt.org/0.13/tutorial/Multi-Project.html">SBT modules:</a></p>
 *
 * <img src="../../../../documentation-resources/architecture/deps.png" alt="diagram the shows the dependencies between the SBT modules">
 *
 * <p>(The picture is maybe outdated but is supposed to show the principle.)</p>
 *
 * <p>Modules which do not depend on each other can be compiled and tested in parallel. The build is faster since all available processors can be used instead of working with just one processor.</p>
 * <p>The middle of the of the following CPU diagram shows the CPU usage at the time of executing tests in a clean project:</p>
 *
 * <img src="../../../../documentation-resources/architecture/cpu-usage-diagram.png" alt="diagram that shows CPU cores are almost equaly used in the middle">
 *
 * <p>Another reason to work with a high modularization is to make sure that the SDK is easy extensible and classes are not unnecessarily coupled to each other.
 * For example the client modules do not "know" the model modules (containing queries and commands) and vice versa.
 * So it must be possible to create more model packages without touching the client. This was the problem of the old
 * SDK which bound the services directly to the client
 * and made it impossible to extend without touching the original code. In addition removing the service coupling to
 * the client makes it possible to create different clients for different future implementations.</p>
 *
 */
public final class Architecture {
    private Architecture() {

    }
}
