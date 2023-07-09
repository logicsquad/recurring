package net.logicsquad.recurring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Superclass for {@link TemporalExpression}s that work by applying some action
 * over a collection of sub-expressions. The sub-expressions are stored in the
 * {@link #expressions} list in this class.
 *
 * @author paulh
 */
public abstract class CompositeTemporalExpression implements TemporalExpression {
	/**
	 * Collection of sub-expressions
	 */
	protected final List<TemporalExpression> expressions;

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
	protected CompositeTemporalExpression(List<TemporalExpression> expressions) {
		Objects.requireNonNull(expressions);
		if (expressions.isEmpty()) {
			throw new IllegalArgumentException("CompositeTemporalExpression requires at least one sub-expression.");
		}
		this.expressions = Collections.unmodifiableList(new ArrayList<>(expressions));
		return;
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
	protected static void checkExpressionsForNull(TemporalExpression... expressions) {
		for (TemporalExpression e : expressions) {
			Objects.requireNonNull(e);
		}
		return;
	}
}
