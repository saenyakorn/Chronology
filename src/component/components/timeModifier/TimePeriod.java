package component.components.timeModifier;

import com.sun.javafx.binding.ExpressionHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class TimePeriod implements Observable, Comparable<TimePeriod> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private final Property<LocalDateTime> beginDateTime;
    private final Property<LocalDateTime> endDateTime;
    private final Set<InvalidationListener> listeners = new HashSet<>();
    private ExpressionHelper<TimePeriod> helper;

    public TimePeriod(LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        this.beginDateTime = new SimpleObjectProperty<>(beginDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime.getValue();
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime.setValue(beginDateTime);
        invalidate();
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime.getValue();
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime.setValue(endDateTime);
        invalidate();
    }

    public boolean isBefore(Object o) {
        if (o instanceof TimePeriod) {
            TimePeriod other = (TimePeriod) o;
            return other.getBeginDateTime().isBefore(getBeginDateTime());
        } else {
            throw new IllegalArgumentException("Type cannot be compared");
        }
    }

    public boolean isAfter(Object o) {
        if (o instanceof TimePeriod) {
            TimePeriod other = (TimePeriod) o;
            return other.getBeginDateTime().isAfter(getBeginDateTime());
        } else {
            throw new IllegalArgumentException("Type cannot be compared");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimePeriod)) return false;
        TimePeriod that = (TimePeriod) o;
        return getBeginDateTime().equals(that.getBeginDateTime()) &&
                getEndDateTime().equals(that.getEndDateTime());
    }

    @Override
    public int compareTo(TimePeriod o) {
        if (isBefore(o)) {
            return 1;
        } else if (isAfter(o)) {
            return -1;
        } else {
            return -1;
        }
    }

    public String toString() {
        return getBeginDateTime().format(formatter) + " " + getEndDateTime().format(formatter);
    }

    public static TimePeriod stringToTimePeriod(String timePeriodString) {
        String[] splitString = timePeriodString.split(" ");
        LocalDateTime beginDateTime = LocalDateTime.parse(splitString[0], formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(splitString[1], formatter);
        return new TimePeriod(beginDateTime, endDateTime);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    public void invalidate() {
        for (var listener : listeners) {
            listener.invalidated(this);
        }
    }
}
