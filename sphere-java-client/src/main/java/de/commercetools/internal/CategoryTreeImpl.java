package de.commercetools.internal;

import de.commercetools.sphere.client.model.products.BackendCategory;
import de.commercetools.internal.util.Log;
import de.commercetools.internal.util.Validation;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.model.Category;
import net.jcip.annotations.GuardedBy;

import java.util.*;
import java.util.concurrent.*;

/** Fetches and builds the category tree in the background.
 *  Block on first read if the tree is still being fetched.  */
public class CategoryTreeImpl implements CategoryTree {
    Categories categoryService;
    private final Object cacheLock = new Object();
    @GuardedBy("cacheLock")
    private Validation<CategoryCache> categoriesCache = null; // absent

    /** Allows at most one rebuild operation running in the background. */
    private final Executor refreshExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    private CategoryTreeImpl(Categories categoryService) {
        this.categoryService = categoryService;
    }

    public static CategoryTreeImpl createAndBeginBuildInBackground(Categories categoryService) {
        CategoryTreeImpl categoryTree = new CategoryTreeImpl(categoryService);
        categoryTree.beginRebuild();
        return categoryTree;
    }

    /** {@inheritDoc} */
    @Override public List<Category> getRoots() { return getCache().getRoots(); }
    /** {@inheritDoc} */
    @Override public Category getById(String id) { return getCache().getById(id); }
    /** {@inheritDoc} */
    @Override public Category getBySlug(String slug) { return getCache().getBySlug(slug); }
    /** {@inheritDoc} */
    @Override public List<Category> getAsFlatList() { return getCache().getAsFlatList(); }

    /** Root categories (the ones that have no parent).*/
    private CategoryCache getCache() {
        synchronized (cacheLock) {
            while (categoriesCache == null) {
                try {
                    cacheLock.wait();
                } catch (InterruptedException e) { }
            }
            if (categoriesCache.isError()) {
                // beginRebuild();   // try to rebuild again if rebuild failed (could flood the backend)
                throw categoriesCache.exception();
            }
            return categoriesCache.value();
        }
    }

    /** Starts asynchronous rebuild in the background. */
    private void beginRebuild() {
        try {
            Log.debug("Refreshing category tree.");
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
        if (e != null) {
            Log.error("Couldn't initialize categories", e);
        }
        CategoryCache categoriesCache = CategoryCache.create(Category.buildTree(backendCategories));
        synchronized (cacheLock) {
            if (e == null) {
                this.categoriesCache = Validation.success(categoriesCache);
                Log.debug("Refreshed category tree.");
            } else {
                this.categoriesCache = Validation.<CategoryCache>error(new SphereException(e));
                Log.error("Failed to refresh category tree.", e);
            }
            cacheLock.notifyAll();
        }
    }
}