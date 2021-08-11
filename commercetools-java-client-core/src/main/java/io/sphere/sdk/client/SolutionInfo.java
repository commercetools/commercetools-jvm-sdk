package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

/**
 *
 * Marker interface to generate a solution info for the JVM SDK user agent.
 *
 * <p>A User-Agent header with a solution information looks like this:
 * {@code commercetools-sdk-java-v1/1.4.1 (AHC/2.0) Java/1.8.0_92-b14 (Mac OS X; x86_64) SOLUTION_NAME/SOLUTION_VERSION (+https://website.tld; +info@SOLUTION.com)}</p>
 *
 * <p>To add a solution information to the JVM SDK create a resource file {@code src/main/resources/META-INF/services/io.sphere.sdk.client.SolutionInfo}
 * which contains a fully qualified class name like (replace at least SOLUTION with your solution name)
 * {@code tld.SOLUTION.client.SOLUTIONSolutionInfo}</p>
 *
 * Then create a class {@code tld.SOLUTION.client.SOLUTIONSolutionInfo}:
 *
 <pre>
 {@code
     public class SOLUTIONSolutionInfo extends SolutionInfo {
         public SOLUTIONSolutionInfo() {
             setName("JVM-SDK-integration-tests");
             setVersion(BuildInfo.version());
             setWebsite("https://github.com/commercetools/commercetools-jvm-sdk");
             setEmergencyContact("helpdesk@commercetools.com");
         }
     }
 }
 </pre>

 This class will be loaded via reflection.
 *
 */
public class SolutionInfo extends Base {
    private String name;
    private String version;
    private String website;
    private String emergencyContact;

    public SolutionInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(final String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
