package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Versioned;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class LightweightProduct extends Base implements Versioned<Product>, ProductIdentifiable {
    private final Long version;
    private final String id;
    private final List<String> skus;

    @JsonCreator
    public LightweightProduct(@JsonProperty("id") final String id, @JsonProperty("version") final Long version, @JsonProperty("skus") final List<String> skus) {
        this.id = id;
        this.version = version;
        this.skus = skus;
    }

    @Override
    public String getId() {
        return id;
    }

    public List<String> getSkus() {
        return skus;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public static SphereRequest<List<LightweightProduct>> requestOfSkus(final List<String> skus) {
        return new LightweightProductsBySku(skus);
    }

    private static class LightweightProductsBySku extends Base implements SphereRequest<List<LightweightProduct>> {
        public static final int MAX = 100;
        private final List<String> skus;

        public LightweightProductsBySku(final List<String> skus) {
            Validate.notEmpty(skus);
            Validate.isTrue(skus.size() <= MAX, "skus are limited to " + MAX + " elements");
            this.skus = skus;
        }

        @Nullable
        @Override
        public List<LightweightProduct> deserialize(final HttpResponse httpResponse) {
            final JsonNode rootJsonNode = SphereJsonUtils.parse(httpResponse.getResponseBody());
            final JsonNode results = rootJsonNode.get("data").get("products").get("results");
            return SphereJsonUtils.readObject(results, new TypeReference<List<LightweightProduct>>() {
            });
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            final String queryString = String.format("query ProductBySku($skus: [String!]!) {\n" +
                    "       products(limit: %d, skus: $skus) {\n" +
                    "           results {\n" +
                    "               id\n" +
                    "               version\n" +
                    "               skus\n" +
                    "           }\n" +
                    "       }\n" +
                    "   }", MAX);
            final String body =String.format(
                    "{\n" +
                            "   \"query\": \"%s\",\n" +
                            "   \"variables\": { \"skus\": [%s]}\n" +
                            "}", queryString.replace("\n", "\\n").replace("\"", "\\\""), skus.stream().distinct().collect(joining("\", \"", "\"", "\"")));
            return HttpRequestIntent.of(HttpMethod.POST, "/graphql", body);
        }
    }
}
