package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class ReturnInfo extends Base {
    private final List<ReturnItem> items;
    private final Optional<String> returnTrackingId;
    private final Optional<ZonedDateTime> returnDate;

    @JsonCreator
    private ReturnInfo(final List<ReturnItem> items, final Optional<String> returnTrackingId, final Optional<ZonedDateTime> returnDate) {
        this.items = items;
        this.returnTrackingId = returnTrackingId;
        this.returnDate = returnDate;
    }

    public static ReturnInfo of(final List<ReturnItem> items, final Optional<String> returnTrackingId, final Optional<ZonedDateTime> returnDate) {
        return new ReturnInfo(items, returnTrackingId, returnDate);
    }

    public List<ReturnItem> getItems() {
        return items;
    }

    public Optional<String> getReturnTrackingId() {
        return returnTrackingId;
    }

    public Optional<ZonedDateTime> getReturnDate() {
        return returnDate;
    }
}
