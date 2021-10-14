package json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonManager {


    public static JsonObject get(String jsonBody)
    {
        JsonObject jsonObject = new JsonParser().parse(jsonBody).getAsJsonObject();

        return jsonObject;
    }


}
