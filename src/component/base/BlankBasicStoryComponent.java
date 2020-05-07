package component.base;

public class BlankBasicStoryComponent extends BasicStoryComponent {

    public BlankBasicStoryComponent() {
        super();
    }

    public BlankBasicStoryComponent(String title, String description) {
        super(title, description);
    }

    @Override
    public String toString() {
        return title;
    }
}
