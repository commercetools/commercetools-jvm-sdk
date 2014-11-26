package io.sphere.sdk.search;

public class StringTerm extends Term<String> {

    private StringTerm(final String value) {
        super(value);
    }

    @Override
    public String render() {
        return "\"" + value() + "\"";
    }

    public static StringTerm of(String value) {
        return new StringTerm(value);
    }
}
