package net.logicsquad.recurring;

import java.time.LocalDate;

/**
 * A {@link TemporalExpression} that is the difference between two
 * sub-expressions. Specifically, this expression is composed of a
 * sub-expression {@code included}, and another sub-expression {@code excluded}.
 * The value of this expression is then
 * {@code included.includes() && !excluded.includes()}.
 * 
 * @author paulh
 */
public final class Difference implements TemporalExpression {
	/**
	 * Included sub-expression
	 */
	private final TemporalExpression included;

	/**
	 * Excluded sub-expression
	 */
	private final TemporalExpression excluded;

	/**
	 * Constructor
	 * 
	 * @param included
	 *            included sub-expression
	 * @param excluded
	 *            excluded sub-expression
	 */
	private Difference(TemporalExpression included, TemporalExpression excluded) {
		this.included = included;
		this.excluded = excluded;
		return;
	}

	/**
	 * Returns a {@link Difference} with sub-expressions {@code included} and
	 * {@code excluded}.
	 * 
	 * @param included
	 *            included sub-expression
	 * @param excluded
	 *            excluded sub-expression
	 * @return new object
	 */
	public static Difference of(TemporalExpression included, TemporalExpression excluded) {
		return new Difference(included, excluded);
	}

	@Override
	public boolean includes(LocalDate date) {
		return included.includes(date) && !excluded.includes(date);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": included=").append(included).append(", excluded=").append(excluded).append(']');
		return sb.toString();
	}
}
