package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;

/**
 * In order to work, the sdk needs an {@link HttpClient} provider, this can be done by extending {@link SphereHttpClientFactory}
 * and exposing it as a service
 *
 */
public abstract class SphereHttpClientFactory extends Base{

    /**
     * The {@link HttpClient} provider method
     * @return HttpClient implementation
     */
     public abstract HttpClient getClient();

}
