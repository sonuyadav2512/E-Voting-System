package utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for date formatting and parsing.
 */
public class DateUtil {

    /** Standard date-time format used across the application. */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** Display-friendly date-time format. */
    public static final String DISPLAY_FORMAT = "MMM dd, yyyy hh:mm a";

    /** Date-only display format. */
    public static final String DATE_ONLY_FORMAT = "MMM dd, yyyy";

    /** Private constructor to prevent instantiation. */
    private DateUtil() { }

    /**
     * Formats a {@link Date} to the standard date-time string.
     *
     * @param date the date to format
     * @return the formatted string, or empty string if date is null
     */
    public static String format(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(DATETIME_FORMAT).format(date);
    }

    /**
     * Formats a {@link Date} to a display-friendly string.
     *
     * @param date the date to format
     * @return the display-formatted string
     */
    public static String formatDisplay(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(DISPLAY_FORMAT).format(date);
    }

    /**
     * Formats a {@link Timestamp} to the standard date-time string.
     *
     * @param timestamp the timestamp to format
     * @return the formatted string
     */
    public static String format(Timestamp timestamp) {
        if (timestamp == null) return "";
        return new SimpleDateFormat(DATETIME_FORMAT).format(timestamp);
    }

    /**
     * Parses a date-time string into a {@link Date}.
     *
     * @param dateStr the string to parse
     * @return the parsed {@link Date}
     * @throws ParseException if the string cannot be parsed
     */
    public static Date parse(String dateStr) throws ParseException {
        return new SimpleDateFormat(DATETIME_FORMAT).parse(dateStr);
    }

    /**
     * Converts a {@link Date} to a {@link Timestamp}.
     *
     * @param date the date to convert
     * @return the corresponding {@link Timestamp}
     */
    public static Timestamp toTimestamp(Date date) {
        if (date == null) return null;
        return new Timestamp(date.getTime());
    }

    /**
     * Checks whether the given date is in the future.
     *
     * @param date the date to check
     * @return {@code true} if the date is after the current time
     */
    public static boolean isFuture(Date date) {
        return date != null && date.after(new Date());
    }

    /**
     * Checks whether the given date is in the past.
     *
     * @param date the date to check
     * @return {@code true} if the date is before the current time
     */
    public static boolean isPast(Date date) {
        return date != null && date.before(new Date());
    }
}
