package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * Sets the TTL {@link Cart#getDeleteDaysAfterLastModification()} of the cart.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setDeleteDaysAfterLastModification()}
 *
 * @see Cart#getDeleteDaysAfterLastModification()
 */
public final class SetDeleteDaysAfterLastModification extends UpdateActionImpl<Cart> {
    @Nullable
    private final Integer deleteDaysAfterLastModification;

    private SetDeleteDaysAfterLastModification(@Nullable final Integer deleteDaysAfterLastModification) {
        super("setDeleteDaysAfterLastModification");
        this.deleteDaysAfterLastModification = deleteDaysAfterLastModification;
    }

    public static SetDeleteDaysAfterLastModification of(@Nullable final Integer deleteDaysAfterLastModification) {
        return new SetDeleteDaysAfterLastModification(deleteDaysAfterLastModification);
    }

    public static SetDeleteDaysAfterLastModification ofUnset() {
        return new SetDeleteDaysAfterLastModification(null);
    }

    @Nullable
    public Integer getDeleteDaysAfterLastModification() {
        return deleteDaysAfterLastModification;
    }
}
