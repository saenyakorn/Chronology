package component.base;

public class BlankBasicStoryComponent extends BasicStoryComponent {

    public BlankBasicStoryComponent(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return title;
    }
}
