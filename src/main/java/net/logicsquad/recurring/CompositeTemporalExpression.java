package net.logicsquad.recurring;

import java.util.List;
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
	 */
	protected CompositeTemporalExpression(List<TemporalExpression> expressions) {
		this.expressions = expressions;
		return;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(this.getClass().getSimpleName());
		sb.append(": ");
		sb.append(expressions.stream().map(e -> e.toString()).collect(Collectors.joining(", ")));
		sb.append("]");
		return sb.toString();
	}
}
