package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
	 * Number of days in 400 years of the Gregorian calendar, after which it repeats
	 */
	private static final int SEARCH_LIMIT = 146_097;

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
		Objects.requireNonNull(date);
		for (ScheduleElement<T> e : elements) {
			if (e.event().equals(event) && e.isOccurring(date)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<T> eventsOn(LocalDate date) {
		Objects.requireNonNull(date);
		Set<T> seen = new HashSet<>();
		Set<T> result = new LinkedHashSet<>();
		for (ScheduleElement<T> e : elements) {
			if (seen.add(e.event()) && isOccurring(e.event(), date)) {
				result.add(e.event());
			}
		}
		return result;
	}

	@Override
	public List<LocalDate> datesInRange(T event, LocalDate start, LocalDate end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
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
	public Optional<LocalDate> nextOccurrence(T event, LocalDate date) {
		return search(event, date, 1);
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
		return occurrences(event, start, 1);
	}

	@Override
	public Stream<LocalDate> pastDates(T event, LocalDate start) {
		return occurrences(event, start, -1);
	}

	@Override
	public Optional<LocalDate> previousOccurrence(T event, LocalDate date) {
		return search(event, date, -1);
	}

	/**
	 * Returns the first date on which {@code event} is occurring, searching a day at a time from {@code date} (inclusive) in the direction of
	 * {@code step}, for at most {@link #SEARCH_LIMIT} days.
	 *
	 * @param event object representing an event
	 * @param date  date to search from
	 * @param step  1 to search forward in time, or -1 to search backwards
	 * @return first date found, or an empty {@link Optional} if there's none within the limit, or {@code event} isn't in this schedule
	 */
	private Optional<LocalDate> search(T event, LocalDate date, int step) {
		Objects.requireNonNull(date);
		if (elements.stream().noneMatch(e -> e.event().equals(event))) {
			return Optional.empty();
		}
		LocalDate cursor = date;
		for (int i = 0; i < SEARCH_LIMIT; i++) {
			if (isOccurring(event, cursor)) {
				return Optional.of(cursor);
			}
			cursor = cursor.plusDays(step);
		}
		return Optional.empty();
	}

	/**
	 * Returns the dates on which {@code event} is occurring, from {@code start} (inclusive) in the direction of {@code step}, ending when a
	 * search finds no further date.
	 *
	 * @param event object representing an event
	 * @param start date to start from
	 * @param step  1 to go forward in time, or -1 to go backwards
	 * @return stream of dates on which {@code event} is occurring
	 */
	private Stream<LocalDate> occurrences(T event, LocalDate start, int step) {
		return Stream.iterate(search(event, start, step), Optional::isPresent, found -> search(event, found.get().plusDays(step), step))
				.map(Optional::get);
	}
}
