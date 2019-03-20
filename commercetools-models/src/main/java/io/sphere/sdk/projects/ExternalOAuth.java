package io.sphere.sdk.projects;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.net.URL;

@ResourceValue
@JsonDeserialize(as = ExternalOAuthImpl.class)
public interface ExternalOAuth {

    URL getUrl();

    String getAuthorizationHeader();

    static ExternalOAuth of(final String authorizationHeader, final URL url){
        return new ExternalOAuthImpl(authorizationHeader,url);
    }

}
