package ca.billweb.constants;

import java.util.Map;
import static java.util.Map.entry;

public class TimerConstants {

    // Basic constraints
    public static final long MS = 1, SECOND = 1000, MINUTE = SECOND * 60, HOUR = MINUTE * 60, DAY = HOUR * 24;
    public static final long WEEK = 7 * DAY;

    public static final String DAILY = "daily", WEEKLY = "weekly", ATTACK = "attack";


    // Timer values in milliseconds
    public static final Map<String, Long> TIMER_VALUES = Map.ofEntries(
            entry(DAILY, DAY),
            entry(WEEKLY, WEEK)
    );

}
