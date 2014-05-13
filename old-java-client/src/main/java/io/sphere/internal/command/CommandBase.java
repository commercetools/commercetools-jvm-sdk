package io.sphere.internal.command;

/** Common fields for commands working against a versioned aggregate root. */
public abstract class CommandBase implements Command {
    private final String id;
    private final int version;

    protected CommandBase(String id, int version) {
        this.id = id;
        this.version = version;
    }

    public String getId() { return id; }
    public int getVersion() { return version; }
}
