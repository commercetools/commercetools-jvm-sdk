package io.sphere.sdk.errors;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

public class TestSphereRequest extends Base implements SphereRequest<String> {

        private final HttpRequestIntent requestIntent;

        private TestSphereRequest(final HttpRequestIntent requestIntent) {
            this.requestIntent = requestIntent;
        }

        public static TestSphereRequest of(final HttpRequestIntent requestIntent) {
            return new TestSphereRequest(requestIntent);
        }


        @Override
        public String deserialize(final HttpResponse httpResponse) {
            return null;
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            return requestIntent;
        }
    }