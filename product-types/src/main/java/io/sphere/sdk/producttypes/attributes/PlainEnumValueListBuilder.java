package io.sphere.sdk.producttypes.attributes;

import com.google.common.collect.ImmutableList;
import io.sphere.sdk.models.Builder;

import java.util.LinkedList;
import java.util.List;

public final class PlainEnumValueListBuilder implements Builder<List<PlainEnumValue>> {
    private final List<PlainEnumValue> list = new LinkedList<>();

    public PlainEnumValueListBuilder add(final String key, final String label) {
        list.add(PlainEnumValue.of(key, label));
        return this;
    }

    public static PlainEnumValueListBuilder of(final String key, final String label) {
        return new PlainEnumValueListBuilder().add(key, label);
    }

    @Override
    public List<PlainEnumValue> build() {
        return ImmutableList.copyOf(list);
    }
}
