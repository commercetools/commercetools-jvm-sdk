package io.sphere.sdk.client;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

abstract class Actor extends AutoCloseableService {
    private final ScheduledThreadPoolExecutor executor = createExecutor();//kind of mailbox

    private ScheduledThreadPoolExecutor createExecutor() {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.setMaximumPoolSize(1);
        return executor;
    }

    public final void tell(final Object message) {
        executor.execute(() -> receive(message));
    }

    protected final void schedule(final Object message, final long delay, final TimeUnit unit) {
        executor.schedule(() -> tell(message), delay, unit);
    }

    @Override
    protected void internalClose() {
        closeThisActor();
        executor.shutdownNow();
    }

    protected final ReceiveBuilder receiveBuilder(final Object message) {
        return new ReceiveBuilder(message);
    }

    protected static class ReceiveBuilder {
        private final Object message;
        private boolean done = false;

        public ReceiveBuilder(final Object message) {
            this.message = message;
        }

        @SuppressWarnings("unchecked")
        protected <T> ReceiveBuilder when(final Class<T> type, final Consumer<? super T> consumer) {
            if (!done && type.isAssignableFrom(message.getClass())) {
                consumer.accept((T) message);
            }
            return this;
        }
    }

    protected void closeThisActor() {

    }
    protected abstract void receive(final Object message);
}
