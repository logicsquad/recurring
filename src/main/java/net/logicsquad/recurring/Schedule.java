package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Encapsulates a "schedule" which can answer various questions about events it
 * knows about.
 * 
 * @author paulh
 */
public interface Schedule {
	/**
	 * Is {@code event} occurring on {@code date}?
	 * 
	 * @param event
	 *            string representation of an event
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@code event} is occurring on {@code date}, otherwise
	 *         {@code false}
	 */
	boolean isOccurring(String event, LocalDate date);

	/**
	 * Returns a list of {@link LocalDate}s on which {@code event} is occurring
	 * between {@code start} and {@code end} dates (inclusive).
	 * 
	 * @param event
	 *            string representation of an event
	 * @param start
	 *            start date
	 * @param end
	 *            end date
	 * @return list of dates on which {@code event} is occurring
	 */
	List<LocalDate> datesInRange(String event, LocalDate start, LocalDate end);

	/**
	 * Returns a stream of future {@link LocalDate}s on which {@code event} is
	 * occurring beginning at {@code start} (inclusive) and proceeding forward in
	 * time.
	 * 
	 * @param event
	 *            string representation of an event
	 * @param start
	 *            start date
	 * @return stream of dates on which {@code event} is occurring
	 */
	Stream<LocalDate> futureDates(String event, LocalDate start);

	/**
	 * Returns a stream of past {@link LocalDate}s on which {@code event} is
	 * occurring beginning at {@code start} (inclusive) and proceeding backwards in
	 * time.
	 * 
	 * @param event
	 *            string representation of an event
	 * @param start
	 *            start date
	 * @return stream of dates on which {@code event} is occurring
	 */
	Stream<LocalDate> pastDates(String event, LocalDate start);

	/**
	 * Returns the next date on which {@code event} is occurring, on or after
	 * {@code date}.
	 * 
	 * @param event
	 *            string representation of an event
	 * @param date
	 *            an arbitrary date
	 * @return date of next occurrence of {@code event}
	 */
	LocalDate nextOccurrence(String event, LocalDate date);

	/**
	 * Returns the previous date on which {@code event} is occurring, on or before
	 * {@code date}.
	 * 
	 * @param event
	 *            string representation of an event
	 * @param date
	 *            an arbitrary date
	 * @return date of previous occurrence of {@code event}
	 */
	LocalDate previousOccurrence(String event, LocalDate date);

	/**
	 * Returns a {@code Schedule} composed of the supplied {@code elements}.
	 * 
	 * @param elements
	 *            {@link ScheduleElement}s comprising the {@link Schedule}
	 * @return a {@code Schedule}
	 */
	static Schedule of(ScheduleElement... elements) {
		return new BasicSchedule(Arrays.asList(elements));
	}

	/**
	 * Returns a {@code Schedule} composed of the {@link ScheduleElement}s in
	 * {@code elements}.
	 * 
	 * @param elements
	 *            a {@link List} of {@link ScheduleElement}s
	 * @return a {@code Schedule}
	 */
	static Schedule of(List<ScheduleElement> elements) {
		return new BasicSchedule(elements);
	}
}
