package io.sphere.sdk.retry;

import org.junit.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;

public class AsyncRetrySupervisorTest {

    @Test
    public void superviseMethodPassIfNothingGoesWrong() throws Exception {
        try (final Service service = new ServiceImpl()) {
            final List<RetryRule> retryRules = Collections.emptyList();
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                assertThat(waitAndGet(bar)).isEqualTo(3);
            }
        }
    }

    @Test
    public void superviseMethodPassesTheException() throws Exception {
        try (final Service service = new Failing2TimesServiceImpl()) {
            final List<RetryRule> retryRules = Collections.emptyList();
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                final Throwable throwable = catchThrowable(() -> waitAndGet(bar));
                assertThat(throwable.getCause()).hasMessage(Failing2TimesServiceImpl.ERROR_MESSAGE);
                assertThat(service.isClosed()).isFalse();
            }
        }
    }

    @Test
    public void immediateRetryRule() throws Exception {
        try (final Service service = new Failing2TimesServiceImpl()) {
            final List<RetryRule> retryRules = singletonList(RetryRule.ofOperation(RetryActions.immediateRetries(3)));
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                assertThat(waitAndGet(bar)).isEqualTo(3);
                assertThat(service.isClosed()).isFalse();
            }
        }
    }

    @Test
    public void immediateRetryRuleNonSufficient() throws Exception {
        try (final Service service = new Failing2TimesServiceImpl()) {
            final List<RetryRule> retryRules = singletonList(RetryRule.ofOperation(RetryActions.immediateRetries(1)));
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                final Throwable throwable = catchThrowable(() -> waitAndGet(bar));
                assertThat(throwable.getCause()).hasMessage(Failing2TimesServiceImpl.ERROR_MESSAGE);
                assertThat(service.isClosed()).isFalse();
            }
        }
    }

    @Test
    public void giveUpAndSendFirstException() throws Exception {
        try (final Service service = new Failing2TimesServiceImpl()) {
            final List<RetryRule> retryRules = singletonList(RetryRule.ofOperation(RetryActions.giveUpAndSendFirstException()));
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                final Throwable throwable = catchThrowable(() -> waitAndGet(bar));
                assertThat(throwable.getCause()).hasMessage(Failing2TimesServiceImpl.ERROR_MESSAGE);
                assertThat(service.isClosed()).isFalse();
            }
        }
    }

    @Test
    public void shutdownServiceAndSendFirstException() throws Exception {
        try (final Service service = new Failing2TimesServiceImpl()) {
            final List<RetryRule> retryRules = singletonList(RetryRule.ofOperation(RetryActions.shutdownServiceAndSendFirstException()));
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                final Throwable throwable = catchThrowable(() -> waitAndGet(bar));
                assertThat(throwable.getCause()).hasMessage(Failing2TimesServiceImpl.ERROR_MESSAGE);
                assertThat(service.isClosed()).isTrue();
            }
        }
    }

    @Test
    public void scheduledRetry() throws Exception {
        try (final Service service = new Failing2TimesServiceImpl()) {
            final List<RetryRule> retryRules = singletonList(RetryRule.ofOperation(RetryActions.scheduledRetry(3, o -> Duration.ofMillis(o.getAttempt() * 100))));
            try(final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules)) {
                final CompletionStage<Integer> bar = supervisor.supervise(service, service::apply, "bar");
                assertThat(waitAndGet(bar)).isEqualTo(3);
                assertThat(service.isClosed()).isFalse();
            }
        }
    }

    private Integer waitAndGet(final CompletionStage<Integer> bar) {
        return bar.toCompletableFuture().join();
    }
}