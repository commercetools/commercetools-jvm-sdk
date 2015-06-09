package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnItemDraft;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#addReturnInfo()}
 */
public class AddReturnInfo extends UpdateAction<Order> {
    private final Optional<ZonedDateTime> returnDate;
    private final Optional<String> returnTrackingId;
    private final List<ReturnItemDraft> items;

    private AddReturnInfo(final List<ReturnItemDraft> items, final Optional<ZonedDateTime> returnDate, final Optional<String> returnTrackingId) {
        super("addReturnInfo");
        this.returnDate = returnDate;
        this.returnTrackingId = returnTrackingId;
        this.items = items;
    }

    public static AddReturnInfo of(final List<ReturnItemDraft> items, final Optional<ZonedDateTime> returnDate, final Optional<String> returnTrackingId) {
        return new AddReturnInfo(items, returnDate, returnTrackingId);
    }

    public static AddReturnInfo of(final List<ReturnItemDraft> items) {
        return of(items, Optional.empty(), Optional.empty());
    }

    public AddReturnInfo withReturnDate(final ZonedDateTime returnDate) {
        return of(items, Optional.of(returnDate), returnTrackingId);
    }

    public AddReturnInfo withReturnTrackingId(final String returnTrackingId) {
        return of(items, returnDate, Optional.of(returnTrackingId));
    }

    public Optional<ZonedDateTime> getReturnDate() {
        return returnDate;
    }

    public Optional<String> getReturnTrackingId() {
        return returnTrackingId;
    }

    public List<ReturnItemDraft> getItems() {
        return items;
    }
}
