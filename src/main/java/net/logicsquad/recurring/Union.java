package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.List;

public class Union extends CompositeTemporalExpression {
	public Union(List<TemporalExpression> expressions) {
		super(expressions);
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
