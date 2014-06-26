package io.sphere.sdk.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import io.sphere.sdk.utils.JsonUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class CommandImpl<I, R extends I> implements Command<I> {
    @Override
    public Function<HttpResponse, I> resultMapper() {
        return httpResponse -> JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody());
    }

    protected abstract TypeReference<R> typeReference();

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
