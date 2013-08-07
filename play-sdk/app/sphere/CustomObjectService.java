package sphere;


import io.sphere.client.SphereResult;
import io.sphere.client.model.CustomObject;
import play.libs.F;

import java.util.Locale;

public interface CustomObjectService {

    /**
     * Finds a custom object by container and key.
     */
    FetchRequest<CustomObject> get(String container, String key);

    /**
     * Sets the the custom object identified by container and key
     *
     * Will overwrite all data that already exists under that key.
     */
    <T> F.Promise<SphereResult<CustomObject>> set(String container, String key, T value);

    /**
     *
     * Deletes the object identified by container and key.
     */
    DeleteRequest<CustomObject> delete(String container, String key);


}
