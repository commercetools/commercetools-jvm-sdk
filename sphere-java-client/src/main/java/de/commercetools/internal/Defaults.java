package de.commercetools.internal;

import de.commercetools.sphere.client.FilterType;

/** Default values of some constants. */
public class Defaults {
    /** Default page size when paging through results. */
    public static final int pageSize = 25;
    /** Amount of time indication that an OAuth token is about to expire and should be refreshed.  */
    public static final long tokenAboutToExpireMs = 60*1000L;  // 1 minute
    /** Filter type used if not explicitly specified. */
    public static final FilterType filterType = FilterType.RESULTS_AND_FACETS;
}
