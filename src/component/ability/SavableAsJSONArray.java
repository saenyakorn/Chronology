package component.ability;

import org.json.simple.JSONArray;

/**
 * Allows an object to be saved and read as a JSON array.
 * @param <T> the type of object being saved to JSONArray.
 */
public interface SavableAsJSONArray<T> extends Savable {

    /**
     * Converts an object into a JSONArray.
     * @return the passed object, in JSONArray form.
     */
    JSONArray writeJSONArray();

    /**
     * Loads data in the JSONArray into an object of type T.
     * @param jsonArray the JSONArray that is to be read.
     * @return an object of type T with data loaded from the jsonArray parameter.
     */
    T readJSONArray(JSONArray jsonArray);

}
