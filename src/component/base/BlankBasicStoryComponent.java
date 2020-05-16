package component.base;

import utils.ApplicationUtils;

/**
 * A blank instance of BasicStoryComponent. Used for context menu initialization.
 */
public class BlankBasicStoryComponent extends BasicStoryComponent {

    /**
     * Constructor for BlankBasicStoryComponent that requires title and description.
     * @param title
     * @param description
     */
    public BlankBasicStoryComponent(String title, String description) {
        setTitle(title);
        setDescription(description);
        ApplicationUtils.removeItemToCurrentHashMap(getComponentId());
    }

    /**
     * Overrides toString method.
     * @return title.
     */
    @Override
    public String toString() {
        return getTitle();
    }
}
