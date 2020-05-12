package component.ability;

import org.json.simple.JSONArray;

public interface SavableAsJSONArray<T> extends Savable {

    JSONArray writeJSONArray();

    T readJSONArray(JSONArray jsonArray);

}
