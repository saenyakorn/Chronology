package ablity;

import org.json.simple.JSONArray;

public interface SavableAsJSONArray extends Savable {

    JSONArray writeJSONArray();

}
