package net.logicsquad.recurring;

import java.time.LocalDate;

class ScheduleElement {
	private final String event;
	private final TemporalExpression expression;

	public ScheduleElement(String event, TemporalExpression expression) {
		this.event = event;
		this.expression = expression;
		return;
	}

	public boolean isOccurring(LocalDate date) {
		return expression.includes(date);
	}

	public String event() {
		return event;
	}
}
