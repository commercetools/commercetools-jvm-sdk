package sphere.util;

import com.google.common.base.Strings;
import de.commercetools.internal.util.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import play.mvc.Http;

import java.io.IOException;

public class SessionUtil {
    public static void putInt(Http.Session session, String key, int value) {
        session.put(key, String.valueOf(value));
    }

    public static Integer getIntOrNull(Http.Session session, String key) {
        String value = session.get(key);
        if (value == null) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public static void clear(Http.Session session, String key) {
        session.remove(key);
    }

    public static IdWithVersion getIdOrNull(Http.Session session, String idKey, String versionKey) {
        String id = session.get(idKey);
        String version = session.get(versionKey);
        if (Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(version))
            return null;
        try {
            return new IdWithVersion(id, Integer.parseInt(version));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
    public static void putId(Http.Session session, IdWithVersion idWithVersion, String idKey, String versionKey) {
        session.put(idKey, idWithVersion.id());
        session.put(versionKey, Integer.toString(idWithVersion.version()));
    }
    public static void clearId(Http.Session session, String idKey, String versionKey) {
        session.remove(idKey);
        session.remove(versionKey);
    }

//    private <T> T tryGetObjectFromSession(Http.Session session, String key, TypeReference<T> jsonParserTypeRef) {
//        String json = session.get(key);
//        if (json == null)
//            return null;
//        ObjectMapper jsonParser = new ObjectMapper();
//        try {
//            Log.trace(String.format("[cart] Session value %s, %s.", key, json));
//            return jsonParser.readValue(json, jsonParserTypeRef);
//        } catch (IOException e) {
//            Log.error("Cannot parse " + key + " from session", e);
//            return null;
//        }
//    }
//    private <T> void putObjectToSession(Http.Session session, String key, T obj) {
//        ObjectWriter jsonWriter = new ObjectMapper().writer();
//        Log.trace(String.format("[cart] Putting to session: key %s, value %s.", key, obj));
//        try {
//            session.put(key, jsonWriter.writeValueAsString(obj));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
