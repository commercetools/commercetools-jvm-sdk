package io.sphere.sdk.client;

import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.Base;

public class TestSolutionInfoSupplier extends Base implements SolutionInfoSupplier {
    @Override
    public SolutionInfo get() {
        final SolutionInfo solutionInfo = new SolutionInfo();
        solutionInfo.setName("JVM-SDK-integration-tests");
        solutionInfo.setVersion(BuildInfo.version());
        solutionInfo.setWebsite("https://github.com/commercetools/commercetools-jvm-sdk");
        solutionInfo.setEmergencyContact("helpdesk@commercetools.com");
        return solutionInfo;
    }
}
