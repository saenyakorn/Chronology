package component.components.timeModifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimePeriod implements Comparable<TimePeriod> {
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    LocalDateTime beginDateTime;
    LocalDateTime endDateTime;

    public TimePeriod(LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isBefore(Object o) {
        if (o instanceof TimePeriod) {
            TimePeriod other = (TimePeriod) o;
            return other.beginDateTime.isBefore(this.beginDateTime);
        } else {
            throw new IllegalArgumentException("Type cannot be compared");
        }
    }

    public boolean isAfter(Object o) {
        if (o instanceof TimePeriod) {
            TimePeriod other = (TimePeriod) o;
            return other.beginDateTime.isAfter(this.beginDateTime);
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
            return 0;
        }
    }

    public String toString() {
        return beginDateTime.format(formatter) + " " + endDateTime.format(formatter);
    }

    public static TimePeriod stringToTimePeriod(String timePeriodString) {
        String[] splitString = timePeriodString.split(" ");
        LocalDateTime beginDateTime = LocalDateTime.parse(splitString[0], formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(splitString[1], formatter);
        return new TimePeriod(beginDateTime, endDateTime);
    }
}
