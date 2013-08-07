package io.sphere.internal;

import com.google.common.base.Optional;
import io.sphere.client.CommandRequest;
import io.sphere.client.DeleteRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.customobjects.CustomObjectService;
import io.sphere.client.customobjects.model.CustomObject;
import io.sphere.client.shop.ApiMode;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class CustomObjectServiceImpl extends ProjectScopedAPI implements CustomObjectService {
    private final RequestFactory requestFactory;
    private final TypeReference<CustomObject> typeReference = new TypeReference<CustomObject>() {};

    public CustomObjectServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    @Override
    public FetchRequest<CustomObject> get(String container, String key) {
        return requestFactory.createFetchRequest(
                endpoints.customObjects.get(container, key),
                Optional.<ApiMode>absent(),
                typeReference);
    }

    @Override
    public <T> CommandRequest<CustomObject> set(String container, String key, T value) {
        final String url = endpoints.customObjects.post();
        ObjectMapper mapper = new ObjectMapper();
        final CustomObject command = new CustomObject(container, key, mapper.valueToTree(value));
        return requestFactory.createCommandRequest(url, command, typeReference);
    }

    @Override
    public DeleteRequest<CustomObject> delete(String container, String key) {
        final String url = endpoints.customObjects.get(container, key);
        return requestFactory.createDeleteRequest(url, typeReference);
    }
}
