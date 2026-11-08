package net.logicsquad.recurring;

import java.time.LocalDate;

/**
 * A {@code TemporalExpression} describes some set of days in a compact way. An
 * implementation might model, for example, "first Tuesday in June" or "every
 * day from 1 January to 31 March". A {@code TemporalExpression} can report
 * whether an arbitrary date matches it.
 *
 * <p>
 * The expressions in this package are equal if they're built the same way from
 * equal parts. Equality doesn't compare the dates that expressions match, so
 * {@code RangeEachYear.of(Month.JUNE)} doesn't equal the range from 1 June to
 * 30 June, although both match the same dates. A lambda is equal only to
 * itself.
 * </p>
 *
 * @author paulh
 */
@FunctionalInterface
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
