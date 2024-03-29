package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A basic implementation of {@link Schedule} for use by {@link Schedule#of(ScheduleElement...) Schedule.of()}.
 *
 * @param <T> type for {@link ScheduleElement}s and {@code event} objects
 * @author paulh
 */
final class BasicSchedule<T> implements Schedule<T> {
	/**
	 * {@link ScheduleElement}s comprising this {@code Schedule}
	 */
	private final List<ScheduleElement<T>> elements;

	/**
	 * Constructor
	 *
	 * @param elements comprising {@link ScheduleElement}s
	 * @throws NullPointerException if {@code elements} is {@code null}
	 */
	BasicSchedule(List<ScheduleElement<T>> elements) {
		Objects.requireNonNull(elements);
		this.elements = Collections.unmodifiableList(new ArrayList<>(elements));
		return;
	}

	@Override
	public boolean isOccurring(T event, LocalDate date) {
		for (ScheduleElement<T> e : elements) {
			if (e.event().equals(event) && e.isOccurring(date)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<LocalDate> datesInRange(T event, LocalDate start, LocalDate end) {
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
	public LocalDate nextOccurrence(T event, LocalDate date) {
		LocalDate cursor = date;
		while (!isOccurring(event, cursor)) {
			cursor = cursor.plusDays(1);
		}
		return cursor;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": ").append(elements.stream().map(e -> e.toString()).collect(Collectors.joining(", ")))
				.append(']');
		return sb.toString();
	}

	@Override
	public Stream<LocalDate> futureDates(T event, LocalDate start) {
		return Stream.iterate(nextOccurrence(event, start), seed -> nextOccurrence(event, seed.plusDays(1)));
	}

	@Override
	public Stream<LocalDate> pastDates(T event, LocalDate start) {
		return Stream.iterate(previousOccurrence(event, start), seed -> previousOccurrence(event, seed.minusDays(1)));
	}

	@Override
	public LocalDate previousOccurrence(T event, LocalDate date) {
		LocalDate cursor = date;
		while (!isOccurring(event, cursor)) {
			cursor = cursor.minusDays(1);
		}
		return cursor;
	}
}
