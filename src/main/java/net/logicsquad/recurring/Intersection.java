package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link TemporalExpression} that acts as the intersection of its
 * sub-expressions. If this expression is composed of sub-expressions {A, B,
 * ...}, then the value of this expression is A ∩ B ∩ ...
 * 
 * @author paulh
 */
public class Intersection extends CompositeTemporalExpression {
	/**
	 * Constructor
	 * 
	 * @param expressions
	 *            sub-expressions
	 */
	private Intersection(List<TemporalExpression> expressions) {
		super(expressions);
		return;
	}

	/**
	 * Returns an {@code Intersection} with sub-expressions {@code expressions}.
	 * 
	 * @param expressions
	 *            a list of {@link TemporalExpression}s
	 * @return new object
	 */
	public static Intersection of(List<TemporalExpression> expressions) {
		return new Intersection(expressions);
	}

	/**
	 * Returns an {@code Intersection} with sub-expressions {@code expressions}.
	 * 
	 * @param expressions
	 *            {@link TemporalExpression}s
	 * @return new object
	 */
	public static Intersection of(TemporalExpression... expressions) {
		return new Intersection(Arrays.asList(expressions));
	}

	@Override
	public boolean includes(LocalDate date) {
		for (TemporalExpression e : expressions) {
			if (!e.includes(date)) {
				return false;
			}
		}
		return true;
	}
}
