package de.commercetools.internal;

import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.CommandRequestBuilder;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;

public class CommandRequestBuilderImpl<T> implements CommandRequestBuilder<T> {
    RequestHolder<T> requestHolder;
    Command command;
    TypeReference<T> jsonParserTypeRef;

    public CommandRequestBuilderImpl(RequestHolder<T> requestHolder, Command command, TypeReference<T> jsonParserTypeRef) {
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        try {
            this.requestHolder = requestHolder.setBody(jsonWriter.writeValueAsString(command));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.command = command;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** {@inheritDoc}  */
    public T execute() {
        try {
            return executeAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public ListenableFuture<T> executeAsync() {
        return RequestExecutor.execute(requestHolder, jsonParserTypeRef);
    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getRawUrl() {
        return this.requestHolder.getRawUrl();
    }

    /** The body of the request, for debugging purposes. */
    public String getBody() {
        return this.requestHolder.getBody();
    }

    /** The command, for debugging purposes. */
    public Command getCommand() {
        return this.command;
    }
}
