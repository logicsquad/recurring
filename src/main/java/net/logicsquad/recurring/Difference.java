package net.logicsquad.recurring;

import java.time.LocalDate;

public class Difference implements TemporalExpression {
	private final TemporalExpression included;
	private final TemporalExpression excluded;

	private Difference(TemporalExpression included, TemporalExpression excluded) {
		this.included = included;
		this.excluded = excluded;
		return;
	}

	public static Difference of(TemporalExpression included, TemporalExpression excluded) {
		return new Difference(included, excluded);
	}

	@Override
	public boolean includes(LocalDate date) {
		return included.includes(date) && !excluded.includes(date);
	}
}
