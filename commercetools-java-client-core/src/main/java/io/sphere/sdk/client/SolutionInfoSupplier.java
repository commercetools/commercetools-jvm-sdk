package io.sphere.sdk.client;

import java.util.function.Supplier;

/**
 *
 * Marker interface to generate a solution info for the JVM SDK user agent.
 *
 * example:
 * {@code commercetools-jvm-sdk/1.4.1 (AHC/2.0) Java/1.8.0_92-b14 (Mac OS X; x86_64) SOLUTION_NAME/SOLUTION_VERSION (+https://website.tld; +info@SOLUTION.com)}
 *
 * Create a resource file "src/main/resources/META-INF/services/io.sphere.sdk.client.SolutionInfoSupplier" which contains for the line
 * tld.SOLUTION.client.SOLUTIONSolutionInfoSupplier
 *
 * Then create a class "tld.SOLUTION.client.SOLUTIONSolutionInfoSupplier"
 *
 * {@code public class TestSolutionInfoSupplier extends Base implements SolutionInfoSupplier {
@Override
public SolutionInfo get() {
    final SolutionInfo solutionInfo = new SolutionInfo();
    solutionInfo.setName("SOLUTION_NAME");//no whitespaces!
    solutionInfo.setVersion("SOLUTION_VERSION");
    solutionInfo.setWebsite("https://website.tld; +info@SOLUTION.com");
    solutionInfo.setEmergencyContact("info@SOLUTION.com");
    return solutionInfo;
}
}}
 *
 */
@FunctionalInterface
public interface SolutionInfoSupplier extends Supplier<SolutionInfo> {
}
