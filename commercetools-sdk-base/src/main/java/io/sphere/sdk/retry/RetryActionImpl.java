package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.builder.ToStringBuilder;

abstract class RetryActionImpl extends Base implements RetryAction {

    protected abstract String getDescription();

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("description", getDescription()).build();
    }

}
