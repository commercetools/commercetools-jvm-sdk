package de.commercetools.internal.request;

import com.google.common.base.Optional;
import de.commercetools.internal.command.Command;
import de.commercetools.internal.util.Util;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;

public class CommandRequestWithErrorHandling<T> implements CommandRequest<Optional<T>>, TestableRequest {
    RequestHolder<T> requestHolder;
    Command command;
    int handledErrorStatus;
    TypeReference<T> jsonParserTypeRef;

    public CommandRequestWithErrorHandling(RequestHolder<T> requestHolder, Command command, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        try {
            this.requestHolder = requestHolder.setBody(jsonWriter.writeValueAsString(command));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.command = command;
        this.handledErrorStatus = handledErrorStatus;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override public Optional<T> execute() {
        return Util.sync(executeAsync());
    }

    @Override public ListenableFuture<Optional<T>> executeAsync() {
        return RequestExecutor.executeAndHandleError(requestHolder, handledErrorStatus, jsonParserTypeRef);
    }

    /** The command that will be sent, for testing purposes. */
    public Command getCommand() {
        return this.command;
    }

    // testing purposes
    @Override public TestableRequestHolder getRequestHolder() {
        return requestHolder;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return getRequestHolder().toString();
    }
}
