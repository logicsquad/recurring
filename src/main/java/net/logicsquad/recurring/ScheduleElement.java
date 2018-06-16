package net.logicsquad.recurring;

import java.time.LocalDate;

/**
 * An element of a {@link Schedule} that links an event with a
 * {@link TemporalExpression} describing how the event recurs.
 * 
 * @author paulh
 */
public class ScheduleElement {
	/**
	 * String representing an event
	 */
	private final String event;

	/**
	 * An expression describing the recurrence of {@link #event}
	 */
	private final TemporalExpression expression;

	/**
	 * Constructor
	 * 
	 * @param event
	 *            string representing some event
	 * @param expression
	 *            {@link TemporalExpression} describing event's recurrence
	 */
	private ScheduleElement(String event, TemporalExpression expression) {
		this.event = event;
		this.expression = expression;
		return;
	}

	/**
	 * Returns {@link ScheduleElement} for {@code event} with recurrence described
	 * by {@code expression}.
	 * 
	 * @param event
	 *            string representing some event
	 * @param expression
	 *            {@link TemporalExpression} describing event's recurrence
	 * @return {@link ScheduleElement}
	 */
	public static ScheduleElement of(String event, TemporalExpression expression) {
		return new ScheduleElement(event, expression);
	}

	/**
	 * Is this element's event occurring on {@code date}?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if this element's event is occurring on {@code date},
	 *         otherwise {@code false}
	 */
	public boolean isOccurring(LocalDate date) {
		return expression.includes(date);
	}

	/**
	 * Returns this {@code ScheduleElement}'s event.
	 * 
	 * @return event
	 */
	public String event() {
		return event;
	}
}
