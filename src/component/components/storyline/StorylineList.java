package component.components.storyline;

import java.util.ArrayList;

public class StorylineList {
    private ArrayList<Storyline> storylines;

    public StorylineList() {
        this.storylines = new ArrayList<>();
    }

    public ArrayList<Storyline> getStorylines() {
        return storylines;
    }

    public void addStoryline(Storyline storyline) {
        storylines.add(storyline);
    }

    public void addAllStoryline(ArrayList<Storyline> storylines) {
        this.storylines.addAll(storylines);
    }

    public int removeStoryline(Storyline storyline) {
        if (storylines.contains(storyline)) {
            storylines.remove(storyline);
            return 1;
        } else {
            System.out.println("This storyline does not exist");
            return 0;
        }
    }
}
