package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Encapsulates a "schedule" which can answer various questions about events it knows about.
 *
 * @param <T> type for {@link ScheduleElement}s and {@code event} objects
 * @author paulh
 */
public interface Schedule<T> {
	/**
	 * Is {@code event} occurring on {@code date}?
	 *
	 * @param event object representing an event
	 * @param date  a {@link LocalDate}
	 * @return {@code true} if {@code event} is occurring on {@code date}, otherwise {@code false}
	 */
	boolean isOccurring(T event, LocalDate date);

	/**
	 * Returns a list of {@link LocalDate}s on which {@code event} is occurring between {@code start} and {@code end} dates (inclusive).
	 *
	 * @param event object representing an event
	 * @param start start date
	 * @param end   end date
	 * @return list of dates on which {@code event} is occurring
	 */
	List<LocalDate> datesInRange(T event, LocalDate start, LocalDate end);

	/**
	 * Returns a stream of future {@link LocalDate}s on which {@code event} is occurring beginning at {@code start} (inclusive) and proceeding
	 * forward in time. The stream ends if {@code event} doesn't occur again within 400 years: see {@link #nextOccurrence(Object, LocalDate)}.
	 *
	 * @param event object representing an event
	 * @param start start date
	 * @return stream of dates on which {@code event} is occurring
	 */
	Stream<LocalDate> futureDates(T event, LocalDate start);

	/**
	 * Returns a stream of past {@link LocalDate}s on which {@code event} is occurring beginning at {@code start} (inclusive) and proceeding
	 * backwards in time. The stream ends if {@code event} didn't occur within the 400 years before: see
	 * {@link #previousOccurrence(Object, LocalDate)}.
	 *
	 * @param event object representing an event
	 * @param start start date
	 * @return stream of dates on which {@code event} is occurring
	 */
	Stream<LocalDate> pastDates(T event, LocalDate start);

	/**
	 * Returns the next date on which {@code event} is occurring, on or after {@code date}. The search covers 400 years, the length of the
	 * Gregorian calendar's cycle, so for an expression that's agnostic to the year, an empty result means that {@code event} never occurs.
	 *
	 * @param event object representing an event
	 * @param date  an arbitrary date
	 * @return date of next occurrence of {@code event}, or an empty {@link Optional} if it doesn't occur within 400 years
	 */
	Optional<LocalDate> nextOccurrence(T event, LocalDate date);

	/**
	 * Returns the previous date on which {@code event} is occurring, on or before {@code date}. As with
	 * {@link #nextOccurrence(Object, LocalDate)}, the search covers 400 years.
	 *
	 * @param event object representing an event
	 * @param date  an arbitrary date
	 * @return date of previous occurrence of {@code event}, or an empty {@link Optional} if it didn't occur within the 400 years before
	 */
	Optional<LocalDate> previousOccurrence(T event, LocalDate date);

	/**
	 * Returns a {@code Schedule} composed of the supplied {@code elements}.
	 *
	 * @param <T>      type for event
	 * @param elements {@link ScheduleElement}s comprising the {@link Schedule}
	 * @return a {@code Schedule}
	 * @throws NullPointerException if any {@link ScheduleElement} in {@code elements} is {@code null}
	 */
	@SafeVarargs
	static <T> Schedule<T> of(ScheduleElement<T>... elements) {
		for (ScheduleElement<T> e : elements) {
			Objects.requireNonNull(e);
		}
		return new BasicSchedule<T>(Arrays.asList(elements));
	}

	/**
	 * Returns a {@code Schedule} composed of the {@link ScheduleElement}s in {@code elements}.
	 *
	 * @param <T>      type for event
	 * @param elements a {@link List} of {@link ScheduleElement}s
	 * @return a {@code Schedule}
	 * @throws NullPointerException if {@code elements} is {@code null}
	 */
	static <T> Schedule<T> of(List<ScheduleElement<T>> elements) {
		Objects.requireNonNull(elements);
		return new BasicSchedule<T>(elements);
	}
}
