package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

public abstract class Bound<T extends Comparable<? super T>> extends Base {
    protected final T endpoint;
    protected final BoundType type;

    /**
     * Allows to define the bound type of a range.
     * A bound identified with INCLUSIVE type indicates the endpoint is included in the range.
     * A bound identified with EXCLUSIVE type indicates the endpoint is excluded in the range.
     */
    protected enum BoundType {
        INCLUSIVE,
        EXCLUSIVE
    }

    protected Bound(final T endpoint, final BoundType type) {
        this.endpoint = endpoint;
        this.type = type;
    }

    public T endpoint() {
        return endpoint;
    }

    /**
     * Determines whether the endpoint is excluded from the range or included.
     * @return true if the bound is exclusive, false if it is inclusive.
     */
    public boolean isExclusive() {
        return type.equals(BoundType.EXCLUSIVE);
    }

    /**
     * Determines whether the endpoint is included from the range or excluded.
     * @return true if the bound is inclusive, false if it is exclusive.
     */
    public boolean isInclusive() {
        return !isExclusive();
    }

    public abstract String render();
}
