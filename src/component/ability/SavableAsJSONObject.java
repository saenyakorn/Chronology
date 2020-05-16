package component.ability;

import org.json.simple.JSONObject;

/**
 * Allows an object to be saved and read as a JSON object.
 * @param <T> the type of object being saved to JSONObject.
 */
public interface SavableAsJSONObject<T> extends Savable {

    /**
     * Converts an object into a JSONObject.
     * @return the passed object, in JSONObject form.
     */
    JSONObject writeJSONObject();

    /**
     * Loads data in the JSONObject into an object of type T.
     * @param jsonObject the JSONObject that is to be read.
     * @return an object of type T with data loaded from the jsonObject parameter.
     */
    T readJSONObject(JSONObject jsonObject);

}
