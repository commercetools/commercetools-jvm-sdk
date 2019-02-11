package io.sphere.sdk.orderedits.commands.stagedactions;


public final class ChangeCustomLineItemQuantity extends OrderEditStagedUpdateActionBase {

    private final String customLineItemId;
    private final Long quantity;

    private ChangeCustomLineItemQuantity(final String customLineItemId, final Long quantity) {
        super("changeCustomLineItemQuantity");
        this.customLineItemId = customLineItemId;
        this.quantity = quantity;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public static ChangeCustomLineItemQuantity of(final String customLineItemId, final long quantity) {
        return new ChangeCustomLineItemQuantity(customLineItemId, quantity);
    }
}
