package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Intersection extends CompositeTemporalExpression {
	private Intersection(List<TemporalExpression> expressions) {
		super(expressions);
		return;
	}

	public static Intersection of(List<TemporalExpression> expressions) {
		return new Intersection(expressions);
	}

	public static Intersection of(TemporalExpression... expressions) {
		return new Intersection(Arrays.asList(expressions));
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
