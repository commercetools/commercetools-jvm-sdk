package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.ExperimentalReactiveStream;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.Locale.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class CategoryDocumentationTest extends IntegrationTest {

    private static CategoryTree categoryTree;

    @BeforeClass
    public static void beforeClass() throws IOException {
        deleteAllCategories();
        setUpData();
        categoryTree = fetchCategoryTree();
    }

    private static CategoryTree fetchCategoryTree() {
        final Publisher<Category> publisher = ExperimentalReactiveStream.of(CategoryQuery.of()).publisher(client().getUnderlying());
        final CategorySubscriber subscriber = new CategorySubscriber();
        publisher.subscribe(subscriber);
        final List<Category> categories = subscriber.getFuture().join();
        return CategoryTree.of(categories);
    }

    private static void deleteAllCategories() {
        final Publisher<Category> publisher = ExperimentalReactiveStream.of(CategoryQuery.of().byIsRoot()).publisher(client().getUnderlying());
        final CategoryDeleteSubscriber subscriber = new CategoryDeleteSubscriber();
        publisher.subscribe(subscriber);
        subscriber.getFuture().join();
    }

    private static void setUpData() throws IOException {
        final Map<String, Category> externalIdToCategoryMap = new HashMap<>();//contains all the created categories
        try (final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("category-import-1.csv")) {
            try (final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream)) {
                try (final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    bufferedReader.lines()
                            .skip(1)//first line is headers
                            .map(line -> line.trim())
                            .filter(line -> !"".equals(line))//remove empty lines
                            .map(line -> line.split(","))
                            .map(columns -> {
                                final LocalizedStrings name = LocalizedStrings.of(GERMAN, columns[2]).plus(ENGLISH, columns[4]);
                                final LocalizedStrings slug = LocalizedStrings.of(GERMAN, columns[3]).plus(ENGLISH, columns[5]);
                                final String externalId = columns[0];
                                final CategoryDraftBuilder categoryDraftBuilder = CategoryDraftBuilder.of(name, slug).externalId(externalId);
                                final String externalIdParent = columns[1];
                                if (!"".equals(externalIdParent)) {
                                    final Category parent = externalIdToCategoryMap.get(externalIdParent);
                                    requireNonNull(parent, externalIdParent + " ");
                                    categoryDraftBuilder.parent(parent);
                                }
                                final CategoryDraft draft = categoryDraftBuilder.build();

                                //here is the call to SPHERE.IO
                                final Category category = client().execute(CategoryCreateCommand.of(draft));

                                externalIdToCategoryMap.put(externalId, category);
                                return category;
                            })
                    .collect(toList());
                }
            }
        }
    }

    private static class CategoryDeleteSubscriber implements Subscriber<Category> {
        private final CompletableFuture<Void> future = new CompletableFuture<>();
        private Subscription subscription;

        @Override
        public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;
            requestElements();
        }

        private void requestElements() {
            subscription.request(1);
        }

        @Override
        public void onNext(final Category category) {
            client().getUnderlying().execute(CategoryDeleteCommand.of(category)).whenComplete((c, t) -> {
                requestElements();
            });
        }

        @Override
        public void onError(final Throwable throwable) {
            future.completeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            future.complete(null);
        }

        public CompletableFuture<Void> getFuture() {
            return future;
        }
    }

    private static class CategorySubscriber implements Subscriber<Category> {
        private final CompletableFuture<List<Category>> future = new CompletableFuture<>();
        private final List<Category> categories = new LinkedList<>();
        private Subscription subscription;

        @Override
        public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(final Category category) {
            categories.add(category);
            subscription.request(1);
        }

        @Override
        public void onError(final Throwable throwable) {
            future.completeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            future.complete(categories);
        }

        public CompletableFuture<List<Category>> getFuture() {
            return future;
        }
    }
}