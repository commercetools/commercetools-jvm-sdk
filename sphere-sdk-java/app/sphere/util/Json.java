package sphere.util;

import java.io.IOException;
import java.io.StringWriter;
import org.codehaus.jackson.map.ObjectMapper;

public class Json {

    public static String toJsonString(Object value) throws IOException {
      ObjectMapper jsonMapper = new ObjectMapper();
      StringWriter sw = new StringWriter();
      jsonMapper.writeValue(sw, value);
      return sw.toString();
    }
}
