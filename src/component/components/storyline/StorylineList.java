package component.components.storyline;

import component.components.eventCard.EventCard;

import java.util.ArrayList;
import java.util.Iterator;

public class StorylineList implements Iterable<Storyline> {
    private final ArrayList<Storyline> storylines;

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

    public void addEventCard(EventCard eventCard) {
        int index = storylines.indexOf(eventCard.getStoryline());
        Storyline selectedStoryLine = storylines.get(index);
        selectedStoryLine.addEventCard(eventCard);
        storylines.set(index, selectedStoryLine);
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

    public int size() {
        return storylines.size();
    }

    @Override
    public Iterator<Storyline> iterator() {
        return storylines.iterator();
    }
}
