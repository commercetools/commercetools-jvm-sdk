package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.ExperimentalForAll;
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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.Locale.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class CategoryDocumentationTest extends IntegrationTest {
    @BeforeClass
    public static void beforeClass() throws IOException {
        deleteAllCategories();
        setUpData();
    }

    private static void deleteAllCategories() {
        final Publisher<Category> publisher = ExperimentalForAll.of(CategoryQuery.of().byIsRoot()).publisher(client().getUnderlying());
        final CategoryDeleteSubscriber subscriber = new CategoryDeleteSubscriber();
        publisher.subscribe(subscriber);
        subscriber.getFuture().join();
    }

    private static void setUpData() throws IOException {
        final Map<String, Category> externalIdToCategoryMap = new HashMap<>();
        try (final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sunrise-category-import.csv")) {
            try (final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream)) {
                try (final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    bufferedReader.lines()
                            .skip(1)//first line is headers
                            .map(line -> line.split(","))
                            .map(columns -> {
                                final LocalizedStrings name = LocalizedStrings.of(GERMAN, columns[1], ENGLISH, columns[3]).plus(ITALIAN, columns[5]);
                                final LocalizedStrings slug = LocalizedStrings.of(GERMAN, columns[2], ENGLISH, columns[4]).plus(ITALIAN, columns[6]);
                                final String externalId = columns[0];
                                final CategoryDraftBuilder categoryDraftBuilder = CategoryDraftBuilder.of(name, slug).externalId(externalId);
                                if (columns.length >= 8) {
                                    final String externalIdParent = columns[7];
                                    final Category parent = externalIdToCategoryMap.get(externalIdParent);
                                    requireNonNull(parent, externalIdParent + " ");
                                    categoryDraftBuilder.parent(parent);
                                }
                                final CategoryDraft draft = categoryDraftBuilder.build();
                                final Category category = execute(CategoryCreateCommand.of(draft));
                                externalIdToCategoryMap.put(externalId, category);
                                return category;
                            })
                    .collect(toList());
                }
            }
        }
    }

    @Test
    public void foo() throws Exception {


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
}