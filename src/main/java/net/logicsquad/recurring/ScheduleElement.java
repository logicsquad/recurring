package net.logicsquad.recurring;

import java.time.LocalDate;

/**
 * An element of a {@link Schedule} that links an event with a {@link TemporalExpression} describing how the event recurs.
 *
 * @param <T> type for {@code event} objects
 * @author paulh
 */
public final class ScheduleElement<T> {
	/**
	 * Object representing an event
	 */
	private final T event;

	/**
	 * An expression describing the recurrence of {@link #event}
	 */
	private final TemporalExpression expression;

	/**
	 * Constructor
	 *
	 * @param event      object representing an event
	 * @param expression {@link TemporalExpression} describing event's recurrence
	 */
	private ScheduleElement(T event, TemporalExpression expression) {
		this.event = event;
		this.expression = expression;
		return;
	}

	/**
	 * Returns {@link ScheduleElement} for {@code event} with recurrence described by {@code expression}.
	 *
	 * @param event      object representing an event
	 * @param expression {@link TemporalExpression} describing event's recurrence
	 * @return {@link ScheduleElement}
	 */
	public static <T> ScheduleElement<T> of(T event, TemporalExpression expression) {
		return new ScheduleElement<T>(event, expression);
	}

	/**
	 * Is this element's event occurring on {@code date}?
	 *
	 * @param date a {@link LocalDate}
	 * @return {@code true} if this element's event is occurring on {@code date}, otherwise {@code false}
	 */
	public boolean isOccurring(LocalDate date) {
		return expression.includes(date);
	}

	/**
	 * Returns this {@code ScheduleElement}'s event.
	 *
	 * @return event
	 */
	public T event() {
		return event;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": event='").append(event).append("' expression=").append(expression).append(']');
		return sb.toString();
	}
}
