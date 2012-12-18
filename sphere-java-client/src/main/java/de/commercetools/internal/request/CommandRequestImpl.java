package de.commercetools.internal.request;

import de.commercetools.internal.command.Command;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;

public class CommandRequestImpl<T> implements CommandRequest<T> {
    final RequestHolder<T> requestHolder;
    final Command command;
    final TypeReference<T> jsonParserTypeRef;

    public CommandRequestImpl(RequestHolder<T> requestHolder, Command command, TypeReference<T> jsonParserTypeRef) {
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
        try {
            return executeAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    @Override public ListenableFuture<T> executeAsync() {
        return RequestExecutor.executeAndThrowOnError(requestHolder, jsonParserTypeRef);
    }

    @Override public String getUrl() {
        return this.requestHolder.getRawUrl();
    }

    @Override public String getBody() {
        return this.requestHolder.getBody();
    }

    /** The command object that will be sent, for testing purposes. */
    public Command getCommand() {
        return this.command;
    }
}
