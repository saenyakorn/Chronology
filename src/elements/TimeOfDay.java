package elements;

import elements.basic.TimePeriod;

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
}
