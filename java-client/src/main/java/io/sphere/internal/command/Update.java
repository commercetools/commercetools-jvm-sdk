package io.sphere.internal.command;

import java.util.ArrayList;
import java.util.List;

/** Update object which accumulates update actions and is used to construct a {@link UpdateCommand}.
 * The actions will be applied to the object in the order they were added. If one action fails
 * to be executed on the server, the whole update fails and none of the actions will be applied.  */
public abstract class Update<A extends UpdateAction> {
    private List<A> actions = new ArrayList<A>();

    /** Adds an {@link UpdateAction} to the update. */
    protected void add(A action) {
        this.actions.add(action);
    }

    protected List<A> getActions() { return actions; }
    
    /** @return true if the update contains no actions. */
    public boolean isEmpty() { return actions.isEmpty(); }
}
