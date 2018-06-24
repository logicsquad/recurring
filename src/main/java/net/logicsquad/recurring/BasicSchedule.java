package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A basic implementation of {@link Schedule} for use by
 * {@link Schedule#of(ScheduleElement...) Schedule.of()}.
 * 
 * @author paulh
 */
final class BasicSchedule implements Schedule {
	/**
	 * {@link ScheduleElement}s comprising this {@code Schedule}
	 */
	private final List<ScheduleElement> elements;

	/**
	 * Constructor
	 * 
	 * @param elements
	 *            comprising {@link ScheduleElement}s
	 */
	public BasicSchedule(List<ScheduleElement> elements) {
		this.elements = elements;
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
	public List<LocalDate> datesInRange(String event, LocalDate start, LocalDate end) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(this.getClass().getSimpleName());
		sb.append(": ");
		sb.append(elements.stream().map(e -> e.toString()).collect(Collectors.joining(", ")));
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Stream<LocalDate> futureDates(String event, LocalDate start) {
		return Stream.iterate(nextOccurrence(event, start), seed -> nextOccurrence(event, seed.plusDays(1)));
	}

	@Override
	public LocalDate previousOccurrence(String event, LocalDate date) {
		LocalDate cursor = date;
		while (!isOccurring(event, cursor)) {
			cursor = cursor.minusDays(1);
		}
		return cursor;
	}
}
