package ablity;

import org.json.simple.JSONObject;

public interface SavableAsJSONObject<T> extends Savable {

    JSONObject writeJSONObject();
    T readJSONObject(JSONObject jsonObject);

}
