package sphere.model.products;

public class TextAttributeDefinition extends AttributeDefinition {
    TextInputHint inputHint;

    public TextInputHint getInputHint() { return inputHint; }
    public void setInputHint(TextInputHint inputHint) { this.inputHint = inputHint; }

    public TextAttributeDefinition(String name, boolean isRequired, boolean isVariant, TextInputHint inputHint) {
        super(name, isRequired, isVariant);
        this.inputHint = inputHint;
    }

    public TextAttributeDefinition(String name, boolean isRequired, boolean isVariant) {
        this(name, isRequired, isVariant, TextInputHint.SingleLine);
    }

    // for the JSON deserializer
    private TextAttributeDefinition() { }
}
