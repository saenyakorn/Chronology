package elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeOfDay {

    private LocalTime getBeginTimeOfDay(Period period) {
        LocalTime time;
        switch (period) {
            case DAWN:
                time = LocalTime.of(0, 0);
                break;
            case MORNING:
                time = LocalTime.of(6, 0);
                break;
            case MIDDAY:
                time = LocalTime.of(10, 0);
                break;
            case AFTERNOON:
                time = LocalTime.of(14, 0);
                break;
            case EVENING:
                time = LocalTime.of(16, 0);
                break;
            case NIGHT:
                time = LocalTime.of(20, 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + period);
        }
        return time;
    }

    private LocalTime getEndTimeOfDay(Period period) {
        LocalTime time;
        switch (period) {
            case DAWN:
                time = LocalTime.of(6, 0);
                break;
            case MORNING:
                time = LocalTime.of(10, 0);
                break;
            case MIDDAY:
                time = LocalTime.of(14, 0);
                break;
            case AFTERNOON:
                time = LocalTime.of(16, 0);
                break;
            case EVENING:
                time = LocalTime.of(20, 0);
                break;
            case NIGHT:
                time = LocalTime.of(0, 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + period);
        }
        return time;
    }

    public TimePeriod getTimePeriodFromPeriod(LocalDate localDate, Period period) {
        LocalTime beginTime = getBeginTimeOfDay(period);
        LocalTime endTime = getEndTimeOfDay(period);
        LocalDateTime beginDateTime = LocalDateTime.of(localDate, beginTime);
        LocalDateTime endDateTime = LocalDateTime.of(localDate, endTime);
        return new TimePeriod(beginDateTime, endDateTime);
    }

    public static class TimePeriod implements Comparable<TimePeriod> {
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
                return other.endDateTime.isAfter(this.endDateTime);
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
    }
}
