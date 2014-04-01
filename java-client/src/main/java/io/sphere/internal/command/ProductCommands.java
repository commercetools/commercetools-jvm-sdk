package io.sphere.internal.command;

public class ProductCommands {

    // -------------------------
    // Update
    // -------------------------

    public static abstract class ProductUpdateAction extends UpdateAction {
        private final boolean staged;
        public boolean isStaged() { return staged; }

        public ProductUpdateAction(String action, boolean staged) { 
            super(action); 
            this.staged = staged;
        }
    }
    
    public static final class SetAttribute extends ProductUpdateAction {
        private final int variantId;
        private final String name;
        private final Object value;

        public int getVariantId() { return variantId; }
        public String getName() { return name; }
        public Object getValue() { return value; }

        public SetAttribute(int variantId, String name, Object value, boolean staged) {
            super("setAttribute", staged);
            this.variantId = variantId;
            this.name = name;
            this.value = value;
        }
    }
}
