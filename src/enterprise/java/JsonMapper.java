package enterprise.java;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Student on 11/22/2015.
 */
public class JsonMapper {
    public static ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Decode any instance to the mapped Java Class
     *
     * @param data JSON string to be converted to Java Object
     * @param <T>  Class file of object to be converted to Object
     *
     * @return Class Instance
     */
    public static <T> T toClassInstance(String data, Class<T> type) {
        try {
            return jsonMapper.readValue(data, type);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
