package io.sphere.sdk.client;

import io.sphere.sdk.test.IntegrationTest;

public class MainMethodThreadLeakTest extends IntegrationTest {

    /**
     * This is a test if no threads are blocking the the termination of the application. Needs to be executed on the ci server.
     * In case it has a leak, it just hangs.
     * This is necessary, since a unit test cannot cover this termination problem.
     * @param args unused command line parameters
     */
    public static void main(String[] args) {
        final SphereClient client = SphereClientFactory.of().createClient(getSphereClientConfig());
        client.close();
    }
}
