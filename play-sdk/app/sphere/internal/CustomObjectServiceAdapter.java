package sphere.internal;

import io.sphere.client.SphereResult;
import io.sphere.client.model.CustomObject;
import play.libs.F.Promise;
import sphere.CustomObjectService;
import sphere.DeleteRequest;
import sphere.FetchRequest;
import sphere.util.Async;

public class CustomObjectServiceAdapter implements CustomObjectService{

    private final io.sphere.client.shop.CustomObjectService service;

    public CustomObjectServiceAdapter(io.sphere.client.shop.CustomObjectService service) {
        this.service = service;
    }


    @Override
    public FetchRequest<CustomObject> get(String container, String key) {
        return Async.adapt(service.get(container, key));
    }

    @Override
    public <T> Promise<SphereResult<CustomObject>> set(String container, String key, T value) {
        return Async.execute(service.set(container, key, value));
    }

    @Override
    public DeleteRequest<CustomObject> delete(String container, String key) {
        return Async.adapt(service.delete(container, key));
    }
}
