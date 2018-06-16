package net.logicsquad.recurring;

import java.util.List;

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
}
