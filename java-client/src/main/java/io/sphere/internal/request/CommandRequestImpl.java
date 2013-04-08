package io.sphere.internal.request;

import io.sphere.internal.command.Command;
import io.sphere.internal.util.Util;
import io.sphere.client.CommandRequest;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CommandRequestImpl<T> implements CommandRequest<T>, TestableRequest {
    final RequestHolder<T> requestHolder;
    final Command command;
    final TypeReference<T> jsonParserTypeRef;

    public CommandRequestImpl(
            @Nonnull RequestHolder<T> requestHolder, @Nonnull Command command, @Nonnull TypeReference<T> jsonParserTypeRef)
    {
        if (requestHolder == null) throw new NullPointerException("requestHolder");
        if (command == null) throw new NullPointerException("command");
        if (jsonParserTypeRef == null) throw new NullPointerException("jsonParserTypeRef");
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        try {
            this.requestHolder = requestHolder.setBody(jsonWriter.writeValueAsString(command));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.command = command;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override public T execute() {
        return Util.sync(executeAsync());
    }

    @Override public ListenableFuture<T> executeAsync() {
        return RequestExecutor.executeAndThrowOnError(requestHolder, jsonParserTypeRef);
    }

    /** The command object that will be sent, for testing purposes. */
    public Command getCommand() {
        return this.command;
    }

    // testing purposes
    @Override public TestableRequestHolder getRequestHolder() {
        return requestHolder;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return requestHolder.toString();
    }
}
