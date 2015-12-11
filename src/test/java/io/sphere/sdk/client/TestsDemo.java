package io.sphere.sdk.client;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class TestsDemo {

    private void modelInstanceFromJson() {
        //deserializes an object stored from JSON
        final SphereClient client = TestDoubleSphereClientFactory.createObjectTestDouble(httpRequest -> {
            final Object result;
            if (httpRequest.getPath().contains("/categories")) {
                //in Play projects the file is in "test/resources/categories.json"
                final PagedQueryResult<Category> queryResult = SphereJsonUtils.readObjectFromResource("categories.json", CategoryQuery.resultTypeReference());
                result = queryResult;
            } else {
                throw new UnsupportedOperationException("I'm not prepared for this request: " + httpRequest);
            }
            return result;
        });
    }

    private void withJson() {
        final SphereClient client = TestDoubleSphereClientFactory.createHttpTestDouble(httpRequest -> {
            final HttpResponse response;
            if (httpRequest.getPath().contains("/categories")) {
                //JSON representation is often useful to deal with errors, but this time again a happy path example
                //alternatively you can provide the String from a file in the classpath
                response = HttpResponse.of(200, "{\n" +
                        "    \"offset\" : 0,\n" +
                        "    \"count\" : 1,\n" +
                        "    \"total\" : 1,\n" +
                        "    \"results\" : [ {\n" +
                        "        \"id\" : \"5ebe6dc9-ba32-4030-9f3e-eee0137a1274\",\n" +
                        "        \"version\" : 1,\n" +
                        "        \"name\" : {\n" +
                        "            \"en\" : \"TestSnowboard equipment\"\n" +
                        "        },\n" +
                        "        \"slug\" : {\n" +
                        "            \"en\" : \"snowboard-equipment\"\n" +
                        "        },\n" +
                        "        \"ancestors\" : [ ],\n" +
                        "        \"orderHint\" : \"0.000014020459255201865700631\",\n" +
                        "        \"createdAt\" : \"2014-06-06T09:12:05.520Z\",\n" +
                        "        \"lastModifiedAt\" : \"2014-06-06T09:12:05.520Z\"\n" +
                        "    }]\n" +
                        "}");
            } else {
                //here you can put if else blocks for further preconfigured responses
                throw new UnsupportedOperationException("I'm not prepared for this request: " + httpRequest);
            }
            return response;
        });
    }

    @Test
    public void replaceValuesInJsonStringDemo() throws Exception {
        final String originalProductProjectionJsonString = ResourceUtil.stringFromResource("product-projection.json");
        final String newProductId = "8b2f45fa-fb1e-48dc-a601-ab0ea9f493df";
        final String newSlug = "whatever-slug";
        final String productProjectionJson = originalProductProjectionJsonString
                .replace("8665d91a-eab0-45a8-a378-e6603d66d084", newProductId)
                .replace("girls-hartbreak-crew1417714797195", newSlug)
                //example for key and value replace
                .replace("\"hasStagedChanges\": true", "\"hasStagedChanges\": false");
        final ProductProjection productProjection = SphereJsonUtils.readObject(productProjectionJson, ProductProjection.typeReference());
        assertThat(productProjection.getId()).isEqualTo(newProductId);
        assertThat(productProjection.getSlug().get(Locale.ENGLISH)).isEqualTo(newSlug);
        assertThat(productProjection.hasStagedChanges()).isFalse();
    }
}
