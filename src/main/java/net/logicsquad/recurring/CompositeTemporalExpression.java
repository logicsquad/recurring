package net.logicsquad.recurring;

import java.util.List;

public abstract class CompositeTemporalExpression implements TemporalExpression {
	protected final List<TemporalExpression> expressions;

	public CompositeTemporalExpression(List<TemporalExpression> expressions) {
		this.expressions = expressions;
		return;
	}
}
