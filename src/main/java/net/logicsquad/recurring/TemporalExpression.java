package net.logicsquad.recurring;

import java.time.LocalDate;

public interface TemporalExpression {
	boolean includes(LocalDate date);
}
