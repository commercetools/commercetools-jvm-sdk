package sphere.util;

import com.google.common.base.Strings;
import io.sphere.client.model.VersionedId;
import io.sphere.internal.util.Log;
import play.mvc.Http;

public class SessionUtil {
    public static void putInt(Http.Session session, String key, int value) {
        Log.trace("SessionUtil.putInt: " + key + ":" + Integer.valueOf(value));
        session.put(key, String.valueOf(value));
    }

    public static void putString(Http.Session session, String key, String value) {
        Log.trace("SessionUtil.putString: " + key + ":" + value);
        session.put(key, value);
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
        Log.trace("SessionUtil.clear: " + key);
        session.remove(key);
    }

    public static VersionedId getIdOrNull(Http.Session session, String idKey, String versionKey) {
        String id = session.get(idKey);
        String version = session.get(versionKey);
        if (Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(version))
            return null;
        try {
            return new VersionedId(id, Integer.parseInt(version));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
    public static void putIdAndVersion(Http.Session session, VersionedId versionedId, String idKey, String versionKey) {
        putString(session, idKey, versionedId.getId());
        putString(session, versionKey, Integer.toString(versionedId.getVersion()));
    }
    public static void clearId(Http.Session session, String idKey, String versionKey) {
        clear(session, idKey);
        clear(session, versionKey);
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
