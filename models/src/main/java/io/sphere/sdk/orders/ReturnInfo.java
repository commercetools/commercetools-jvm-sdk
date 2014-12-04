package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class ReturnInfo extends Base {
    private final List<ReturnItem> items;
    private final Optional<String> returnTrackingId;
    private final Optional<Instant> returnDate;

    private ReturnInfo(final List<ReturnItem> items, final Optional<String> returnTrackingId, final Optional<Instant> returnDate) {
        this.items = items;
        this.returnTrackingId = returnTrackingId;
        this.returnDate = returnDate;
    }

    public static ReturnInfo of(final List<ReturnItem> items, final Optional<String> returnTrackingId, final Optional<Instant> returnDate) {
        return new ReturnInfo(items, returnTrackingId, returnDate);
    }

    public List<ReturnItem> getItems() {
        return items;
    }

    public Optional<String> getReturnTrackingId() {
        return returnTrackingId;
    }

    public Optional<Instant> getReturnDate() {
        return returnDate;
    }
}
