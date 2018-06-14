package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Union extends CompositeTemporalExpression {
	private Union(List<TemporalExpression> expressions) {
		super(expressions);
		return;
	}

	public static Union of(List<TemporalExpression> expressions) {
		return new Union(expressions);
	}

	public static Union of(TemporalExpression... expressions) {
		return new Union(Arrays.asList(expressions));
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
