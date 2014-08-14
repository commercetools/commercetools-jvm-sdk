package io.sphere.sdk.client;

import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryBuilder;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.http.HttpClientTestDouble;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.Requestable;
import io.sphere.sdk.utils.JsonUtils;

import java.util.Locale;

public class TestsDemo {

    @SuppressWarnings("unchecked")//necessary for testing
    private void withInstanceResults() {
        //provide directly a model instance or more as result, sadly needs unchecked castings
        PlayJavaClient client = new PlayJavaClientImpl(ConfigFactory.load(), new SphereRequestExecutorTestDouble() {

            @Override
            protected <T> T result(final ClientRequest<T> requestable) {
                final T res;
                if(requestable.httpRequest().getPath().contains("/categories")){
                    final LocalizedString name = LocalizedString.of(Locale.ENGLISH, "cat name");
                    final LocalizedString slug = LocalizedString.of(Locale.ENGLISH, "cat-slug");
                    final Category category = CategoryBuilder.of("cat-id", name, slug).build();
                    res = (T) PagedQueryResult.of(category);
                } else {
                    res = super.result(requestable);
                }
                return res;
            }
        });
    }

    @SuppressWarnings("unchecked")//necessary for testing
    private void modelInstanceFromJson() {
        //provide model instances by parsing json, sadly needs unchecked castings
        PlayJavaClient client = new PlayJavaClientImpl(ConfigFactory.load(), new SphereRequestExecutorTestDouble() {
            @Override
            protected <T> T result(final ClientRequest<T> requestable) {
                final T res;
                if(requestable.httpRequest().getPath().contains("/categories")) {
                    //in Play projects the file is in "test/resources/categories.json"
                    res = (T) JsonUtils.readObjectFromJsonFileInClasspath("categories.json", CategoryQuery.resultTypeReference());
                } else {
                    res = super.result(requestable);
                }
                return res;
            }
        });
    }

    private void withJson() {
        //just return JSON
        PlayJavaClient client = new PlayJavaClientImpl(ConfigFactory.load(), new HttpClientTestDouble() {
            @Override
            public HttpResponse testDouble(Requestable requestable) {
                final HttpResponse response;
                if (requestable.httpRequest().getPath().contains("/categories")) {
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
                    throw new RuntimeException("I'm not prepared for this request: " + requestable);
                }
                return response;
            }
        });
    }
}
