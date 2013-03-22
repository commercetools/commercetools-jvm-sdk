package io.sphere.client.model.products;

public class TextAttributeDefinition extends AttributeDefinition {
    TextInputHint inputHint;

    public TextInputHint getInputHint() { return inputHint; }
    public void setInputHint(TextInputHint inputHint) { this.inputHint = inputHint; }

    // for JSON deserializer
    private TextAttributeDefinition() { }
}
