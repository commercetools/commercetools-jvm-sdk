package sphere.util;

import java.io.IOException;
import java.io.StringWriter;
import org.codehaus.jackson.map.ObjectMapper;

public class Json {
    private Json() {}

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJsonString(Object value) throws IOException {
      StringWriter sw = new StringWriter();
      mapper.writeValue(sw, value);
      return sw.toString();
    }
}
