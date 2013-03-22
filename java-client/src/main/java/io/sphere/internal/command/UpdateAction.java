package io.sphere.internal.command;

/** Update actions are a part of update commands. */
public abstract class UpdateAction {
    public final String action;

    public UpdateAction(String action) {
        this.action = action;
    }
}
