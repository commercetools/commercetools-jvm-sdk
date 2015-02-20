package io.sphere.sdk.client;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryBuilder;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.JsonUtils;

import java.util.Locale;

public class TestsDemo {
    private void withInstanceResults() {
        //provide directly a model instance without JSON from SPHERE.IO
        final SphereClientFactory factory = SphereClientFactory.of();
        final SphereClient client = factory.createObjectTestDouble(httpRequest -> {
            final String expectedPath = CategoryQuery.of().byName(Locale.ENGLISH, "cat name").httpRequestIntent().getPath();
            final Object res;
            if(httpRequest.getPath().equals(expectedPath)){
                final LocalizedStrings name = LocalizedStrings.of(Locale.ENGLISH, "cat name");
                final LocalizedStrings slug = LocalizedStrings.of(Locale.ENGLISH, "cat-slug");
                final Category category = CategoryBuilder.of("cat-id", name, slug).build();
                res = PagedQueryResult.of(category);
            } else {
                throw new UnsupportedOperationException("I'm not prepared for this request: " + httpRequest);
            }
            return res;
        });
    }

    private void modelInstanceFromJson() {
        //deserializes an object stored from JSON
        final SphereClientFactory factory = SphereClientFactory.of();
        final SphereClient client = factory.createObjectTestDouble(httpRequest -> {
            final Object result;
            if(httpRequest.getPath().contains("/categories")) {
                //in Play projects the file is in "test/resources/categories.json"
                final PagedQueryResult<Category> queryResult = JsonUtils.readObjectFromResource("categories.json", CategoryQuery.resultTypeReference());
                result = queryResult;
            } else {
                throw new UnsupportedOperationException("I'm not prepared for this request: " + httpRequest);
            }
            return result;
        });
    }

    private void withJson() {
        final SphereClient client = SphereClientFactory.of().createHttpTestDouble(httpRequest -> {
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
}
