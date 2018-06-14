package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.List;

public class Intersection implements TemporalExpression {
	private List<TemporalExpression> expressions;

	public Intersection(List<TemporalExpression> expressions) {
		this.expressions = expressions;
		return;
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
