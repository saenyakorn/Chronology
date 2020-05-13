package component.base;

public class BlankBasicStoryComponent extends BasicStoryComponent {

    public BlankBasicStoryComponent(String title, String description) {
        setTitle(title);
        setDescription(description);
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
