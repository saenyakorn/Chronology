package ablity;

import org.json.simple.JSONObject;

public interface SavableAsJSONObject extends Savable {

    JSONObject getJSONObject();

}
