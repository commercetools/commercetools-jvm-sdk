package de.commercetools.internal.request;

import com.google.common.base.Optional;
import de.commercetools.internal.command.Command;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;

public class CommandRequestWithErrorHandling<T> implements CommandRequest<Optional<T>> {
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
        try {
            return executeAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    @Override public ListenableFuture<Optional<T>> executeAsync() {
        return RequestExecutor.executeAndHandleError(requestHolder, handledErrorStatus, jsonParserTypeRef);
    }

    /** The URL the request will be sent to, for debugging purposes. */
    @Override public String getUrl() {
        return this.requestHolder.getRawUrl();
    }

    /** The body of the request, for debugging purposes. */
    @Override public String getBody() {
        return this.requestHolder.getBody();
    }

    /** The command that will be sent, for testing purposes. */
    public Command getCommand() {
        return this.command;
    }
}
