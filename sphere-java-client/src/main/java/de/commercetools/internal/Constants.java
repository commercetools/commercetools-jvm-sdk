package de.commercetools.internal;

/** Default values of some constants. */
public class Constants {
    /** Default page size when paging through results. */
    public static final int defaultPageSize = 25;
    /** Amount of time indication that an OAuth token is about to expire and should be refreshed.  */
    public static final long tokenAboutToExpireMs = 60*1000L;  // 1 minute
}
