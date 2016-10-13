package io.sphere.sdk.client;

import java.util.function.Supplier;

/**
 *
 * Marker interface to generate a solution info for the JVM SDK user agent.
 *
 * <p>A User-Agent header with a solution information looks like this:
 * {@code commercetools-jvm-sdk/1.4.1 (AHC/2.0) Java/1.8.0_92-b14 (Mac OS X; x86_64) SOLUTION_NAME/SOLUTION_VERSION (+https://website.tld; +info@SOLUTION.com)}</p>
 *
 * <p>To add a solution information to the JVM SDK create a resource file {@code src/main/resources/META-INF/services/io.sphere.sdk.client.SolutionInfoSupplier}
 * which contains a fully qualified class name like (replace at least SOLUTION with your solution name)
 * {@code tld.SOLUTION.client.SOLUTIONSolutionInfoSupplier}</p>
 *
 * Then create a class {@code tld.SOLUTION.client.SOLUTIONSolutionInfoSupplier}:
 *
<pre>
{@code
    public class TestSolutionInfoSupplier extends Base implements SolutionInfoSupplier {
        public SolutionInfo get() {
            final SolutionInfo solutionInfo = new SolutionInfo();
            solutionInfo.setName("SOLUTION_NAME");//no whitespaces!
            solutionInfo.setVersion("SOLUTION_VERSION");//no whitespaces!
            solutionInfo.setWebsite("https://website.tld");
            solutionInfo.setEmergencyContact("info@SOLUTION.com");
            return solutionInfo;
        }
    }
}
</pre>

 This class will be loaded via reflection.
 *
 */
@FunctionalInterface
public interface SolutionInfoSupplier extends Supplier<SolutionInfo> {
}
