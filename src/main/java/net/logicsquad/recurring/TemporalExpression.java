package net.logicsquad.recurring;

import java.time.LocalDate;

/**
 * A {@code TemporalExpression} describes some set of days in a compact way. An
 * implementation might model, for example, "first Tuesday in June" or "every
 * day from 1 January to 31 March". A {@code TemporalExpression} can report
 * whether an arbitrary date matches it.
 * 
 * @author paulh
 */
public interface TemporalExpression {
	/**
	 * Is {@code date} included by this {@code TemporalExpression}?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if this object includes {@code date}, otherwise
	 *         {@code false}
	 */
	boolean includes(LocalDate date);
}
