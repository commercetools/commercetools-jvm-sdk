package io.sphere.sdk.http;

final class NameValuePairImpl extends Base implements NameValuePair {
  private final String name;
  private final String value;

    public NameValuePairImpl(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
