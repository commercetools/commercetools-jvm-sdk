package io.sphere.sdk.client;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

final class QueueSphereClientDecoratorActor extends Actor {
    private final int maxParallelRequests;
    private int currentRequests = 0;
    private final Queue<AsyncTask> queuedTasks = new LinkedList<>();

    public QueueSphereClientDecoratorActor(final int maxParallelRequests) {
        this.maxParallelRequests = maxParallelRequests;
    }

    @Override
    protected void receive(final Object message) {
        receiveBuilder(message)
                .when(AsyncTask.class, task -> {
                    final boolean canDoNow = currentRequests < maxParallelRequests;
                    if (canDoNow) {
                        executeTask(task);
                    } else {
                        queuedTasks.add(task);
                    }
                })
                .when(Done.class, done -> {
                    currentRequests--;
                    Optional.ofNullable(queuedTasks.poll())
                            .ifPresent(task -> executeTask(task));
                });
    }

    private void executeTask(final AsyncTask task) {
        currentRequests++;
        CompletionStage<String> execute = task.execute();
        execute.whenCompleteAsync((res, e) -> tell(new Done()));
    }

    public static class AsyncTask {
        private final Supplier<CompletionStage<String>> task;

        public AsyncTask(final Supplier<CompletionStage<String>> task) {
            this.task = task;
        }

        public CompletionStage<String> execute() {
            return task.get();
        }
    }

    private static class Done {

    }
}
