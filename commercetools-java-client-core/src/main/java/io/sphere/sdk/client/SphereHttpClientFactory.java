package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;


public abstract class SphereHttpClientFactory extends Base{

     public abstract HttpClient getClient();

}
