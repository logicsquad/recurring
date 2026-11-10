package net.logicsquad.recurring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Superclass for {@link TemporalExpression}s that work by applying some action
 * over a list of sub-expressions. It's sealed, so {@link Union} and
 * {@link Intersection} are its only subclasses. Two composite expressions are
 * equal if they're of the same class, with equal sub-expressions in the same
 * order.
 *
 * @author paulh
 */
public abstract sealed class CompositeTemporalExpression implements TemporalExpression permits Union, Intersection {
	/**
	 * Collection of sub-expressions
	 */
	final List<TemporalExpression> expressions;

	/**
	 * Constructor
	 *
	 * @param expressions
	 *            a collection of sub-expressions
	 * @throws NullPointerException
	 *             if {@code expressions} is {@code null}
	 * @throws IllegalArgumentException
	 *             if {@code expressions} is empty
	 */
	CompositeTemporalExpression(List<TemporalExpression> expressions) {
		Objects.requireNonNull(expressions);
		if (expressions.isEmpty()) {
			throw new IllegalArgumentException("CompositeTemporalExpression requires at least one sub-expression.");
		}
		this.expressions = Collections.unmodifiableList(new ArrayList<>(expressions));
		return;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		return expressions.equals(((CompositeTemporalExpression) obj).expressions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass().getName(), expressions);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": ")
				.append(expressions.stream().map(e -> e.toString()).collect(Collectors.joining(", "))).append(']');
		return sb.toString();
	}

	/**
	 * Checks each {@link TemporalExpression} in {@code expressions} for
	 * {@code null}, throwing {@link NullPointerException} if it finds one.
	 *
	 * @param expressions
	 *            array of {@link TemporalExpression}s
	 * @throws NullPointerException
	 *             if any element of {@code expressions} is {@code null}
	 */
	static void checkExpressionsForNull(TemporalExpression... expressions) {
		for (TemporalExpression e : expressions) {
			Objects.requireNonNull(e);
		}
		return;
	}
}
