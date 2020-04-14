package component.base;

public class OnlyBodyBasicStoryComponents extends BasicStoryComponent {

    public OnlyBodyBasicStoryComponents() {
        super();
    }

    public OnlyBodyBasicStoryComponents(String title, String description) {
        super(title, description);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected void loadFXML() {
    }
}
