package de.commercetools.internal;

/** Common fields for commands working against a versioned aggregate root. */
abstract class CommandBase implements Command {
    private final String id;
    private final String version;

    protected CommandBase(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public String getId() { return id; }
    public String getVersion() { return version; }
}
