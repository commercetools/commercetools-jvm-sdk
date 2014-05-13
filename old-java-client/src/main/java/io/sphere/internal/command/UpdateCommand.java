package io.sphere.internal.command;

import java.util.List;

/** Update command for updating a versioned aggregate root. 
 *  The command does not contain the id because the id is encoded into the command endpoint url. */
public class UpdateCommand<A extends UpdateAction> implements Command {
    private final int version;
    private final List<A> actions;

    private UpdateCommand(int version, List<A> actions) {
        this.version = version;
        this.actions = actions;
    }

    public UpdateCommand(int version, Update<A> update) {
        this(version, update.getActions());
    }

    public int getVersion() { return version; }

    public List<A> getActions() { return actions; }
}
