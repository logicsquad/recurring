package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.List;

public class Union implements TemporalExpression {
	private final List<TemporalExpression> expressions;

	public Union(List<TemporalExpression> expressions) {
		this.expressions = expressions;
		return;
	}

	@Override
	public boolean includes(LocalDate date) {
		for (TemporalExpression e : expressions) {
			if (e.includes(date)) {
				return true;
			}
		}
		return false;
	}
}
