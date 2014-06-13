package io.sphere.sdk.queries;

import io.sphere.sdk.categories.*;
import io.sphere.sdk.client.PlayJavaClient;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Locale;

public class QueryDemo extends Controller {
    private PlayJavaClient client;
    private Query<Category, CategoryImpl> query;
    private F.Promise<PagedQueryResult<Category>> promise;



    private void formulatingAQuery() {
        Query<Category, CategoryImpl> query = Categories.query().byName(Locale.ENGLISH, "demo cat");
    }

    private void executeQuery() {
        F.Promise<PagedQueryResult<Category>> promise = client.execute(query);
    }

    private void mapResult() {
         promise.map(pagedQueryResult -> {
            List<Category> categories = pagedQueryResult.getResults();
            return ok(categoriesTemplate.render(categories));
        });
    }


    private void createQueryWithCompanionClass() {
        Query<Category, CategoryImpl> query = Categories.query().byName(Locale.ENGLISH, "demo cat");
    }


    private F.Promise<Result> introduction2() {
        final Query<Category, CategoryImpl> query = Categories.query().byName(Locale.ENGLISH, "demo category");
        return client.execute(query).map(
                pagedQueryResult -> ok(categoriesTemplate.render(pagedQueryResult.getResults()))
        );
    }
}
