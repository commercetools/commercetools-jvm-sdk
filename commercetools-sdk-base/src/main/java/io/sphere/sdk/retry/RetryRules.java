package io.sphere.sdk.retry;

import java.util.List;

public interface RetryRules {
    List<RetryRule> getRules();
}
