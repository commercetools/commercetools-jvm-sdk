package io.sphere.internal;

import com.google.common.base.Optional;
import io.sphere.client.model.products.BackendCategory;
import io.sphere.internal.util.Concurrent;
import io.sphere.internal.util.Log;
import io.sphere.client.SphereException;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.Category;
import io.sphere.internal.util.ValidationE;
import net.jcip.annotations.GuardedBy;

import java.util.*;
import java.util.concurrent.*;

/** Fetches and builds the category tree in the background.
 *  Blocks on first read if the tree is still being fetched.  */
public class CategoryTreeImpl implements CategoryTree {
    Categories categoryService;
    private final Object categoriesLock = new Object();

    @GuardedBy("categoriesLock")
    private Optional<ValidationE<CategoryCache>> categoriesResult = Optional.absent();

    /** Allows at most one rebuild operation running in the background. */
    private final ThreadPoolExecutor refreshExecutor = Concurrent.singleTaskExecutor("Sphere-CategoryTree-refresh");

    private CategoryTreeImpl(Categories categoryService) {
        this.categoryService = categoryService;
    }

    public static CategoryTreeImpl createAndBeginBuildInBackground(Categories categoryService) {
        CategoryTreeImpl categoryTree = new CategoryTreeImpl(categoryService);
        categoryTree.beginRebuild();
        return categoryTree;
    }

    @Override public List<Category> getRoots() { return getCache().getRoots(); }
    @Override public Category getById(String id) { return getCache().getById(id); }
    @Override public Category getBySlug(String slug) { return getCache().getBySlug(slug); }
    @Override public List<Category> getAsFlatList() { return getCache().getAsFlatList(); }

    /** Root categories (the ones that have no parent).*/
    private CategoryCache getCache() {
        synchronized (categoriesLock) {
            while (!categoriesResult.isPresent()) {
                try {
                    categoriesLock.wait();
                } catch (InterruptedException e) { }
            }
            if (categoriesResult.get().isError()) {
                beginRebuild();   // retry on error (essential to recover from backend errors!)
                throw categoriesResult.get().getError();
            }
            return categoriesResult.get().getValue();
        }
    }

    /** Starts rebuild in the background. */
    private void beginRebuild() {
        try {
            Log.debug("[cache] Refreshing category tree.");
            refreshExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    List<BackendCategory> categories;
                    try {
                        categories = categoryService.all().
                                page(0).
                                pageSize(Defaults.maxNumberOfCategoriesToFetchAtOnce).
                                fetch().getResults();
                    } catch (Exception e) {
                        update(null, e);
                        return;
                    }
                    update(categories, null);
                }
            });
        } catch (RejectedExecutionException e) {
            // another rebuild is already in progress, ignore this one
        }
    }

    /** Sets result after rebuild. */
    private void update(List<BackendCategory> backendCategories, Exception e) {
        CategoryCache categoriesCache = null;
        if (e != null) {
            Log.error("[cache] Couldn't initialize category tree", e);
        }  else {
            categoriesCache = CategoryCache.create(Category.buildTree(backendCategories));
        }
        synchronized (categoriesLock) {
            if (e == null) {
                this.categoriesResult = Optional.of(ValidationE.success(categoriesCache));
            } else {
                this.categoriesResult = Optional.of(ValidationE.<CategoryCache>error(new SphereException(e)));
            }
            categoriesLock.notifyAll();
        }
        if (e != null) {
            Log.debug("[cache] Refreshed category tree.");
        }
    }

    /** Shuts down internal thread pools. */
    public void shutdown() {
        refreshExecutor.shutdownNow();
    }
}
