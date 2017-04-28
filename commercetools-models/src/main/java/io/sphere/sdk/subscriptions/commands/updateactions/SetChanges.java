package io.sphere.sdk.subscriptions.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.subscriptions.ChangeSubscription;
import io.sphere.sdk.subscriptions.Subscription;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Sets the changes of the subscription.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.subscriptions.commands.SubscriptionUpdateCommandIntegrationTest#setChangesIronMq()}
 *
 * @see Subscription#getChanges()
 */
public final class SetChanges extends UpdateActionImpl<Subscription> {
    @Nullable
    private final List<ChangeSubscription> changes;

    private SetChanges(@Nullable final List<ChangeSubscription> changes) {
        super("setChanges");
        this.changes = changes;
    }

    @Nullable
    public List<ChangeSubscription> getChanges() {
        return changes;
    }

    /**
     * Creates a update action to set the changes.
     *
     * @param changes the changes {@link Subscription#getChanges()}
     *
     * @return new set changes update action
     */
    public static SetChanges of(@Nullable final List<ChangeSubscription> changes) {
        return new SetChanges(changes);
    }

    public static SetChanges ofUnset() {
        return new SetChanges(null);
    }
}
