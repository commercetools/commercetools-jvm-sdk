package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;

import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setMaxApplications()}
 */
public class SetMaxApplications extends UpdateAction<DiscountCode> {
    private final Optional<Long> maxApplications;

    private SetMaxApplications(final Optional<Long> maxApplications) {
        super("setMaxApplications");
        this.maxApplications = maxApplications;
    }

    public static SetMaxApplications of(final long maxApplications) {
        return of(Optional.of(maxApplications));
    }

    public static SetMaxApplications of(final Optional<Long> maxApplications) {
        return new SetMaxApplications(maxApplications);
    }

    public Optional<Long> getMaxApplications() {
        return maxApplications;
    }
}
