package component.base;

import utils.ApplicationUtils;

public class BlankBasicStoryComponent extends BasicStoryComponent {

    public BlankBasicStoryComponent(String title, String description) {
        setTitle(title);
        setDescription(description);
        ApplicationUtils.removeItemToCurrentHashMap(getComponentId());
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
