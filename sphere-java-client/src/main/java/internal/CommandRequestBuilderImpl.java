package de.commercetools.internal;

import de.commercetools.sphere.client.BackendException;
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
        this.requestHolder = requestHolder;
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
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        try {
            return RequestHolders.execute(requestHolder.setBody(jsonWriter.writeValueAsString(command)), jsonParserTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getRawUrl() {
        return this.requestHolder.getRawUrl();
    }

    /** The body of the request, for debugging purposes. */
    public String getBody() {
        return this.requestHolder.getBody();
    }
}
