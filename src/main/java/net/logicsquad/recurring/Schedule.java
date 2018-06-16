package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	List<LocalDate> dates(String event, LocalDate start, LocalDate end);

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
	 * Returns a {@link Schedule} composed of the supplied {@code elements}.
	 * 
	 * @param elements
	 *            {@link ScheduleElement}s comprising the {@link Schedule}
	 * @return a {@link Schedule}
	 */
	static Schedule of(ScheduleElement... elements) {
		BasicSchedule result = new BasicSchedule();
		for (ScheduleElement e : elements) {
			result.addElement(e);
		}
		return result;
	}

	/**
	 * A basic implementation of {@link Schedule} for use by the static factory
	 * method.
	 */
	static class BasicSchedule implements Schedule {
		/**
		 * {@link ScheduleElement}s comprising this {@code Schedule}
		 */
		private List<ScheduleElement> elements = new ArrayList<>();

		/**
		 * Adds a {@link ScheduleElement} to this {@link Schedule}.
		 * 
		 * @param element
		 *            {@link ScheduleElement}
		 */
		public void addElement(ScheduleElement element) {
			elements.add(element);
			return;
		}

		@Override
		public boolean isOccurring(String event, LocalDate date) {
			for (ScheduleElement e : elements) {
				if (e.event().equals(event) && e.isOccurring(date)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public List<LocalDate> dates(String event, LocalDate start, LocalDate end) {
			List<LocalDate> result = new ArrayList<>();
			LocalDate cursor = start;
			while (cursor.equals(end) || cursor.isBefore(end)) {
				if (isOccurring(event, cursor)) {
					result.add(cursor);
				}
				cursor = cursor.plusDays(1);
			}
			return result;
		}

		@Override
		public LocalDate nextOccurrence(String event, LocalDate date) {
			LocalDate cursor = date;
			while (!isOccurring(event, cursor)) {
				cursor = cursor.plusDays(1);
			}
			return cursor;
		}
	}
}
