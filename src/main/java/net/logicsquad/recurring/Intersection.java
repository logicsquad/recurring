package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.List;

public class Intersection extends CompositeTemporalExpression {
	public Intersection(List<TemporalExpression> expressions) {
		super(expressions);
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
