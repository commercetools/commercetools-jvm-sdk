package io.sphere.sdk.client;

import io.sphere.sdk.meta.BuildInfo;

public class TestSolutionInfo extends SolutionInfo {
    public TestSolutionInfo() {
        setName("JVM-SDK-integration-tests");
        setVersion(BuildInfo.version());
        setWebsite("https://github.com/commercetools/commercetools-jvm-sdk");
        setEmergencyContact("helpdesk@commercetools.com");
    }
}
