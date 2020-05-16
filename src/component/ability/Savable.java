package component.ability;

/**
 * Allows an object to be saved and read as JSON.
 */
public interface Savable {

    /**
     * Gets the JSON object or array in string format.
     * @return the JSON object or array converted to a string.
     */
    String getJSONString();

}
