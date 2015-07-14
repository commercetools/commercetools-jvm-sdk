package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.ExperimentalReactiveStreamUtils;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
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
import java.util.concurrent.CompletionStage;

import static java.util.Locale.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDocumentationTest extends IntegrationTest {

    @BeforeClass
    public static void beforeClass() throws IOException {
        deleteAllCategories();
        setUpData();
    }

    @Test
    public void fetchAll() throws Exception {
        final Publisher<Category> categoryPublisher =
                ExperimentalReactiveStreamUtils.publisherOf(CategoryQuery.of(), sphereClient());
        final CompletionStage<List<Category>> categoriesStage =
                ExperimentalReactiveStreamUtils.collectAll(categoryPublisher);
        final List<Category> categories = categoriesStage.toCompletableFuture().join();
        assertThat(categories).matches(cats -> cats.parallelStream().anyMatch(cat -> cat.getSlug().get(ENGLISH).get().equals("boots-women")));
    }

    @Test
    public void fetchRoots() throws Exception {
        final CategoryQuery seedQuery = CategoryQuery.of().withPredicate(m -> m.parent().isNotPresent());
        final Publisher<Category> categoryPublisher =
                ExperimentalReactiveStreamUtils.publisherOf(seedQuery, sphereClient());
        final CompletionStage<List<Category>> categoriesStage =
                ExperimentalReactiveStreamUtils.collectAll(categoryPublisher);
        final List<Category> rootCategories = categoriesStage.toCompletableFuture().join();
        assertThat(rootCategories.stream().allMatch(cat -> !cat.getParent().isPresent()))
                .overridingErrorMessage("fetched categories have no parent")
                .isTrue();
    }

    private static void deleteAllCategories() {
        final Publisher<Category> publisher = ExperimentalReactiveStreamUtils.publisherOf(CategoryQuery.of().byIsRoot(), sphereClient());
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
                            .map(line -> {
                                final String[] split = line.split(",");
                                return split;
                            })
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
}