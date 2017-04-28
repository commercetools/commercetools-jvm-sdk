package io.sphere.sdk.subscriptions.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.subscriptions.MessageSubscription;
import io.sphere.sdk.subscriptions.Subscription;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Sets the messages of the subscription.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.subscriptions.commands.SubscriptionUpdateCommandIntegrationTest#setMessagesIronMq()}
 *
 * @see Subscription#getMessages()
 */
public final class SetMessages extends UpdateActionImpl<Subscription> {
    @Nullable
    private final List<MessageSubscription> messages;

    private SetMessages(@Nullable final List<MessageSubscription> messages) {
        super("setMessages");
        this.messages = messages;
    }

    @Nullable
    public List<MessageSubscription> getMessages() {
        return messages;
    }

    /**
     * Creates a update action to set the messages.
     *
     * @param messages the messages {@link Subscription#getMessages()}
     *
     * @return new set messages update action
     */
    public static SetMessages of(@Nullable final List<MessageSubscription> messages) {
        return new SetMessages(messages);
    }

    public static SetMessages ofUnset() {
        return new SetMessages(null);
    }
}
