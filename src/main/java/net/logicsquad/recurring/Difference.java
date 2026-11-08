package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A {@link TemporalExpression} that is the difference between two
 * sub-expressions. Specifically, this expression is composed of a
 * sub-expression {@code included}, and another sub-expression {@code excluded}.
 * The value of this expression is then
 * {@code included.includes() && !excluded.includes()}. Two differences are
 * equal if both of their sub-expressions are equal.
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
	 * @throws NullPointerException
	 *             if {@code included} or {@code excluded} is {@code null}
	 */
	public static Difference of(TemporalExpression included, TemporalExpression excluded) {
		Objects.requireNonNull(included);
		Objects.requireNonNull(excluded);
		return new Difference(included, excluded);
	}

	@Override
	public boolean includes(LocalDate date) {
		return included.includes(date) && !excluded.includes(date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Difference)) {
			return false;
		}
		Difference other = (Difference) obj;
		return included.equals(other.included) && excluded.equals(other.excluded);
	}

	@Override
	public int hashCode() {
		return Objects.hash(included, excluded);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": included=").append(included).append(", excluded=").append(excluded).append(']');
		return sb.toString();
	}
}
