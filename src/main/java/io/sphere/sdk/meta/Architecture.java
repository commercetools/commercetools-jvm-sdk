package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/**
 * Placeholder for the architecture description in Javadoc.
 *
 * <p>The JVM SDK consists of a lot of <a href="http://www.scala-sbt.org/0.13/tutorial/Multi-Project.html" target="_blank">SBT modules:</a></p>
 *
 * <img src="{@docRoot}/documentation-resources/architecture/deps.png" alt="diagram the shows the dependencies between the SBT modules">
 *
 * <p>(The picture is maybe outdated, but it is good enough to show the idea.)</p>
 *
 * <p>Modules which do not depend on each other can be compiled and tested in parallel. The build is faster since all available processors can be used instead of working with just one processor.</p>
 * <p>The middle area in the graphs of the following CPU diagram shows the CPU usage at the time of executing tests in a clean project:</p>
 *
 * <img src="{@docRoot}/documentation-resources/architecture/cpu-usage-diagram.png" alt="diagram that shows CPU cores are almost equaly used in the middle">
 *
 * <p>A highly modular architecture makes sure that the SDK is easily extensible. The SDK's classes are not unnecessarily coupled to each other.
 * For example, the client modules do not "know" the model modules (containing queries and commands) and vice versa.
 * So it must be possible to create more model packages without changing the client. This was the problem with the old
 * SDK that bound the services directly to the client
 * and made it therefore impossible to extend without touching the original code. Additionally removing the service coupling to
 * the client makes it possible to create different clients for different future implementations.</p>
 *
 */
public final class Architecture extends Base {
    private Architecture() {

    }
}
