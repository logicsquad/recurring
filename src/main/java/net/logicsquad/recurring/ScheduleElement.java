package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Objects;

/**
 * An element of a {@link Schedule} that links an event with a {@link TemporalExpression} describing how the event recurs. Two elements
 * are equal if their events and expressions are equal.
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
	 * @param <T>        type for event
	 * @param event      object representing an event
	 * @param expression {@link TemporalExpression} describing event's recurrence
	 * @return {@link ScheduleElement}
	 * @throws NullPointerException if {@code event} or {@code expression} is {@code null}
	 */
	public static <T> ScheduleElement<T> of(T event, TemporalExpression expression) {
		Objects.requireNonNull(event);
		Objects.requireNonNull(expression);
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

	/**
	 * Returns this {@code ScheduleElement}'s expression.
	 *
	 * @return expression describing how this element's event recurs
	 * @since 0.6
	 */
	public TemporalExpression expression() {
		return expression;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ScheduleElement)) {
			return false;
		}
		ScheduleElement<?> other = (ScheduleElement<?>) obj;
		return event.equals(other.event) && expression.equals(other.expression);
	}

	@Override
	public int hashCode() {
		return Objects.hash(event, expression);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": event='").append(event).append("' expression=").append(expression).append(']');
		return sb.toString();
	}
}
