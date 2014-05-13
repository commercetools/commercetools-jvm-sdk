package io.sphere.internal.command;

/** Update actions are a part of update commands. */
public abstract class UpdateAction {
    public final String action;

    /** @param action The name of the action. */
    public UpdateAction(String action) { this.action = action; }

    /** The name of the action. */
    public String getAction() { return action; }
}
