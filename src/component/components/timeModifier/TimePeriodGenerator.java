package component.components.timeModifier;

import utils.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Generates a TimePeriod from a PredefinedTimePeriod.
 */
public class TimePeriodGenerator {

    /**
     * Gets beginTime of PredefinedTimePeriod.
     * @param predefinedTimePeriod the PredefinedTimePeriod to be converted.
     * @return beginTime of PredefinedTimePeriod.
     */
    private static LocalTime getBeginTimeOfDay(PredefinedTimePeriod predefinedTimePeriod) {
        LocalTime time;
        switch (predefinedTimePeriod) {
            case DAWN:
                time = LocalTime.of(0, 0);
                break;
            case MORNING:
                time = LocalTime.of(4, 0);
                break;
            case MIDDAY:
                time = LocalTime.of(8, 0);
                break;
            case AFTERNOON:
                time = LocalTime.of(12, 0);
                break;
            case EVENING:
                time = LocalTime.of(16, 0);
                break;
            case NIGHT:
                time = LocalTime.of(20, 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + predefinedTimePeriod);
        }
        return time;
    }

    /**
     * Gets endTime of PredefinedTimePeriod.
     * @param predefinedTimePeriod the PredefinedTimePeriod to be converted.
     * @return beginTime of PredefinedTimePeriod.
     */
    private static LocalTime getEndTimeOfDay(PredefinedTimePeriod predefinedTimePeriod) {
        LocalTime time;
        switch (predefinedTimePeriod) {
            case DAWN:
                time = LocalTime.of(4, 0);
                break;
            case MORNING:
                time = LocalTime.of(8, 0);
                break;
            case MIDDAY:
                time = LocalTime.of(12, 0);
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
                throw new IllegalStateException("Unexpected value: " + predefinedTimePeriod);
        }
        return time;
    }

    /**
     * Converts a PredefinedTimePeriod to a TimePeriod.
     * @param localDate the date this TimePeriod will be set in.
     * @param predefinedTimePeriod the PredefinedTimePeriod to be converted.
     * @return the converted TimePeriod.
     */
    public static TimePeriod getTimePeriodFromPeriod(LocalDate localDate, PredefinedTimePeriod predefinedTimePeriod) {
        LocalTime beginTime = getBeginTimeOfDay(predefinedTimePeriod);
        LocalTime endTime = getEndTimeOfDay(predefinedTimePeriod);
        LocalDateTime beginDateTime = LocalDateTime.of(localDate, beginTime);
        LocalDateTime endDateTime = LocalDateTime.of(localDate, endTime);
        return new TimePeriod(beginDateTime, endDateTime);
    }
}
