package io.sphere.internal;

import com.google.common.util.concurrent.AbstractScheduledService;
import io.sphere.client.shop.CategoryTree;

import java.util.concurrent.TimeUnit;

/** Scheduler to rebuild a category tree stored in cache.
 * It is recommended to call the {@link com.google.common.util.concurrent.AbstractScheduledService#stop()} method
 * on application shutdown. */
public class CategoryTreeRebuildService extends AbstractScheduledService {

    private final CategoryTree categoryTree;
    private final long initialDelayMilliseconds;
    private final long delayMilliseconds;

    public CategoryTreeRebuildService(CategoryTree categoryTree, long initialDelayMilliseconds, long delayMilliseconds) {
        this.categoryTree = categoryTree;
        this.initialDelayMilliseconds = initialDelayMilliseconds;
        this.delayMilliseconds = delayMilliseconds;
    }

    @Override
    protected void runOneIteration() throws Exception {
        categoryTree.rebuildAsync();
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(initialDelayMilliseconds, delayMilliseconds, TimeUnit.MILLISECONDS);
    }
}
