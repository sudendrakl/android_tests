package news.agoda.com.sample.model.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by sudendra.kamble on 06/11/16.
 */

public class GsonSerializer implements JsonSerializer<Collection<?>> {

  @Override public JsonElement serialize(Collection<?> src, Type typeOfSrc,
      JsonSerializationContext context) {
    if (src == null || src.isEmpty()) {// exclusion is made here
      return null;
    }

    JsonArray array = new JsonArray();

    for (Object child : src) {
      JsonElement element = context.serialize(child);
      array.add(element);
    }

    return array;
  }
}